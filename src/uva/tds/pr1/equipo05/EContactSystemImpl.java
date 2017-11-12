package uva.tds.pr1.equipo05;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import uva.tds.pr1.equipo05.aux.SimpleErrorHandler;
import java.lang.IllegalArgumentException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implentnetación de la interfaz EContactSystemInterface
 * @author martorb
 * @author ismpere
 *
 */
public class EContactSystemImpl implements EContactSystemInterface{
	
	private boolean loaded, modified;
	private InputSource source;
	private Document document;
	private DocumentBuilderFactory domParserFactory;
	private DocumentBuilder parser;
	
	/**
	 * 
	 */
	public EContactSystemImpl(){		
	}
	
	/**
	 * Método factoriía, devuelve la implementación
	 * @return EContactSystemImpl
	 */
	public static EContactSystemInterface contactSystemFactory(){
		return new EContactSystemImpl();		
	}

	/**
	 * Carga el XML y lo parsea unasndo DOM
	 * @param pathToXML 
	 * @assert.pre !pathToXML.toString().isEmpty()
	 */
	public void loadFrom(Path pathToXML) {
		assert(!pathToXML.toString().isEmpty());
		
		FileReader input;
		domParserFactory = DocumentBuilderFactory.newInstance();
		domParserFactory.setValidating(true);
		try{
			input = new FileReader(pathToXML.toString());
		}catch(Exception e){
			throw new IllegalArgumentException("La ruta no es correcta");
		}
		try{
			parser = domParserFactory.newDocumentBuilder();
			source = new InputSource(input);
			parser.setErrorHandler(new SimpleErrorHandler());
			document = parser.parse(source);		
		}catch(Exception e){
			throw new IllegalStateException("Ha ocurrido un error al parsear el XML");
		}
		loaded = true;
		modified = false;
		
		//Los print se quitan antes de la entrega
		System.out.println(parser.getClass());
		System.out.println(parser.isValidating());
		System.out.println(document.getDoctype().getName());
		
	}
	
	/**
	 * Actualiza la libreta electronica en otro XML en la ruta pasada como argumento
	 * @assert.pre isModifiedAfterLoaded()
	 */
	public void updateTo(Path pathToXML) {
		assert(isModifiedAfterLoaded());
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		String systemId = document.getDoctype().getSystemId();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(pathToXML.toString()));
		try{
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemId);
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.transform(source, result);

			//Los print se eliminan antes de entregar
			System.out.println("File saved!");
		}catch(Exception e){
			throw new IllegalStateException("Ha ocurrido un problema al actualizar el archivo");
		}
		
	}
	
	/**
	 * Indica si el XML ha sido cargado
	 * @return loaded 
	 */
	public boolean isXMLLoaded(){
		return loaded;
	}
	
	/**
	 * Indica si el XML ha sido modificado después de haber sido cargado
	 * @assert.pre isXmlLoaded()
	 * @return modified
	 */
	public boolean isModifiedAfterLoaded(){
		assert(isXMLLoaded());
		
		return modified;
	}
	/**
	 * Crea un nuevo elemento Person en el documento
	 * @param name Nombre de la persona
	 * @param nickname Nick de la persona
	 * @param surName Apellidos de la persona
	 * @param emais Array que contiene los e-mails de la persona
	 * @assert.pre isXMLLoaded()
	 * @assert.pre !existsContactById(nickname)ç
	 * @assert.pre emails.length>0
	 */
	public void createNewPerson(String name, String nickname, String surName, String[] emails,
			Map<String, EnumKindOfPhone> phones) {
		assert(isXMLLoaded());
		assert(!existContactById(nickname));
		assert(emails.length>0);
		
		Element persona = document.createElement("persona");
		persona.setAttribute("alias", nickname);
		if(surName != null){
			Element apellidos = document.createElement("apellidos");
			apellidos.appendChild(document.createTextNode(surName));
			persona.appendChild(apellidos);
		}
		Element nombre = document.createElement("nombre");
		nombre.appendChild(document.createTextNode(name));
		persona.appendChild(nombre);
		for(int i=0; i<emails.length; i++){
			Element correo = document.createElement("correo");
			correo.appendChild(document.createTextNode(emails[i]));
			persona.appendChild(correo);
		}
		if(phones != null && !phones.isEmpty()){
			for (Map.Entry<String, EnumKindOfPhone> entry : phones.entrySet()){
				Element telefono = document.createElement("telefono");
				telefono.setAttribute("tipotelef", entry.getValue().toString());
				Element numtelef = document.createElement("numtelef");
				numtelef.appendChild(document.createTextNode(entry.getKey()));
				telefono.appendChild(numtelef);
				persona.appendChild(telefono);
			}
		}
		addToLibreta(persona);
	}
	
	/**
	 * Crea un nuevo elemento Group en el documento
	 * @param name Nombre para el nuevo grupo
	 * @param contacts Array que contiene los contactos a añadir al nuevo grupo
	 * @assert.pre isXMLLoaded()
	 * @assert.pre !existsContactById(name)
	 * @assert.pre contacts.length!=0;
	 */
	public void createNewGroup(String name, Contact[] contacts) {
		assert(isXMLLoaded());
		assert(!existContactById(name));
		assert(contacts.length!=0);
		
		Element grupo = document.createElement("grupo");
		grupo.setAttribute("nombre", name);
		for(int i=0;i<contacts.length;i++){
			Element miembro=document.createElement("miembro"+(i+1));
			miembro.appendChild(document.createTextNode(contacts[i].getID()));
			grupo.appendChild(miembro);
		}
		addToLibreta(grupo);	
	}
	
	/**
	 * Devuelve un elemento Contact a partir de su id
	 * @param id ID del contacto buscado
	 * @assert.pre isXMLLoaded()
	 * @assert.pre !id.isEmpty()
	 * @return contacto buscado
	 */
	public Contact getAnyContactById(String id) {
		assert(isXMLLoaded());
		assert(!id.isEmpty());
		
		Element contacto = document.getElementById(id);
		if(contacto!=null){
			Contact x = null;
			if(getPersonByNickname(id)!=null){
				x = getPersonByNickname(id);
			}else{
				if(getGroupByName(id)!=null){
					x = getGroupByName(id);
				}
			}
			return x;
		}else{
			return null;
		}
	}
	/**
	 * Devuelve un elemento Person a partir de su nick
	 * @param name Nick de la persona
	 * @assert.pre isXMLLoaded()
	 * @assert.pre !name.isEmpty()
	 * @return persona buscada (copia)
	 */
	public Person getPersonByNickname(String name) {
		assert(isXMLLoaded());
		assert(!name.isEmpty());
		
		Element persona = document.getElementById(name);
		if(persona!=null && (persona.getNodeName().equals("persona"))){
			String nombre;
			String apellidos = null;
			Map<String, EnumKindOfPhone> telefonos = null;
			
			
			nombre = persona.getElementsByTagName("nombre").item(0).getTextContent();
			if(persona.getElementsByTagName("apellidos")!=null){
				apellidos = persona.getElementsByTagName("apellidos").item(0).getTextContent();
			}
			NodeList nodeCorreos = persona.getElementsByTagName("correos");
			String correos[] = new String[nodeCorreos.getLength()];
			for(int i=0; i<nodeCorreos.getLength(); i++){
				correos[i] = nodeCorreos.item(i).getTextContent();
			}
			NodeList nodeTelefonos = persona.getElementsByTagName("telefono");
			if(nodeTelefonos!=null){
				telefonos = new HashMap<String, EnumKindOfPhone>(nodeTelefonos.getLength());
				for(int i=0; i<nodeTelefonos.getLength(); i++){
					NamedNodeMap attr = nodeTelefonos.item(i).getAttributes();
					String tipotelef = attr.getNamedItem("tipotelef").getTextContent();
					EnumKindOfPhone tt = EnumKindOfPhone.valueOf(tipotelef);
					String numero = nodeTelefonos.item(i).getTextContent();
					telefonos.put(numero, tt);
				}
			}
			return new Person(nombre, apellidos, name, correos, telefonos);
		}else{
			return null;
		}
	}

	/**
	 * Devuelve un elemento Group a partir de su nombre
	 * @param name Nombre del grupo
	 * @assert.pre isXMLLoaded()
	 * @assert.pre !name.isEmpty()
	 * @return grupo buscado (copia)
	 */
	public Group getGroupByName(String name) {
		assert(isXMLLoaded());
		assert(!name.isEmpty());
		
		Element grupo = document.getElementById(name);
		if(grupo!=null && (grupo.getNodeName().equals("grupo"))){
			NodeList nodeContactos = grupo.getElementsByTagName("contacto");
			Contact contactos[] = new Contact[nodeContactos.getLength()];
			for(int i=0; i<nodeContactos.getLength(); i++){
				String id = nodeContactos.item(i).getAttributes().item(0).getTextContent();
				contactos[i] = getAnyContactById(id);
			}
			return new Group(name, contactos);
		}else{
			return null;
		}
	}

	/**
	 * Añade un elemento Contact a un grupo (Group)
	 * @param contact Contacto a añadir al grupo
	 * @param group Grupo buscado
	 * @assert.pre isXMLLoaded()
	 * @assert.pre existsContactById(contact.getID())
	 * @assert.pre getGroupByName(group.getID())!=null
	 * @assert.pre !group.containsContact(contact)
	 */
	public void addContactToGroup(Contact contact, Group group) {
		assert(isXMLLoaded());
		assert(existContactById(contact.getID()));
		assert(getGroupByName(group.getID())!=null);
		assert(!group.containsContact(contact));
		
		Element grupo = document.getElementById(group.getID());
		Element miembro =document.getElementById(contact.getID());
		grupo.appendChild(miembro);
		
		modified = true;
	}

	/**
	 * Elimina un elemento Contact de un grupo (Group)
	 * @param contact Contacto a eliminar del grupo
	 * @param group Grupo buscado
	 * @assert.pre isXMLLoaded() 
	 * @assert.pre existsContactById(contact.getID())
	 * @assert.pre existsContactById(group.getID())
	 * @assert.pre group.containsContact(contact)
	 */
	public void removeContactFromGroup(Contact contact, Group group) {
		assert(isXMLLoaded());
		assert(existContactById(contact.getID()));
		assert(existContactById(group.getID()));
		assert(group.containsContact(contact));
		
		Element grupo = document.getElementById(group.getID());
		NodeList miembros = grupo.getElementsByTagName("miembro");
		for(int i=0;i<miembros.getLength();i++){
			if(miembros.item(i).getTextContent()==contact.getID()){
				grupo.removeChild(miembros.item(i));
			}
			
		}
		modified = true;
	}
	/**
	 * Elimina un elemento Contact del sistema
	 * @param contact Contacto a eliminar
	 * @assert.pre isXMLLoaded()
	 * @assert.pre existsContactById(contact.getID())
	 */
	public void removeContactFromSystem(Contact contact){
		assert(isXMLLoaded());
		assert(existContactById(contact.getID()));
		
		Element contacto = document.getElementById(contact.getID());
		contacto.getParentNode().removeChild(contacto);
		modified = true;
	}
	/**
	 * Devuelve si el contacto existe o no en la libreta de contactos
	 * @param id
	 * @assert.pre isXmlLoaded()
	 * @assert.pre !id.isEmpty()
	 * @return existe
	 */
	public boolean existContactById(String id){
		assert(isXMLLoaded());
		assert(!id.isEmpty());
		boolean is = false;
		if(getAnyContactById(id)!=null){
			is = true;
		}
		return is;
	}
	/**
	 * Añade el elemento x a la libreta de contactos
	 * @param x
	 */
	private void addToLibreta(Element x){
		Element libreta = document.getDocumentElement();
		Element contacto = document.createElement("contacto");
		contacto.appendChild(x);
		libreta.appendChild(contacto);	
		
		modified = true;
	}
	
}