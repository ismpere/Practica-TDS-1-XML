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
	
	boolean loaded, modified;
	InputSource source;
	Document document;
	DocumentBuilderFactory domParserFactory;
	DocumentBuilder parser;
	
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
	 */
	public void loadFrom(Path pathToXML) {
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
		System.out.println(parser.getClass());
		System.out.println(parser.isValidating());
		System.out.println(document.getDoctype().getName());
		
	}
	
	/**
	 * 
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

			System.out.println("File saved!");
		}catch(Exception e){
			throw new IllegalStateException("Ha ocurrido un problema al actualizar el archivo");
		}
		
	}
	
	/**
	 * Indica si el XML ya ha sido cargado
	 * @return loaded 
	 */
	public boolean isXMLLoaded(){
		return loaded;
	}
	
	/**
	 * Indica si el XML ha sido modificado después de haber sido cargado
	 * @return modified
	 */
	public boolean isModifiedAfterLoaded(){
		return modified;
	}
	/**
	 * Crea un nuevo elemento Person en el documento
	 * @param name Nombre de la persona
	 * @param nickname Nick de la persona
	 * @param surName Apellidos de la persona
	 * @param emais Array que contiene los e-mails de la persona
	 * @assert.pre isXMLLoaded()
	 * @assert.pre emails.length>0
	 * @assert.pre !isContactById(nickname)
	 */
	public void createNewPerson(String name, String nickname, String surName, String[] emails,
			Map<String, EnumKindOfPhone> phones) {
		assert(isXMLLoaded());
		assert(emails.length>0);
		//assert(!isContactById(nickname));
		Element libreta = document.getDocumentElement();
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
		Element contacto = document.createElement("contacto");
		contacto.appendChild(persona);
		libreta.appendChild(contacto);	
		
		modified = true;
	}
	
	/**
	 * Crea un nuevo elemento Group en el documento
	 * @param name Nombre para el nuevo grupo
	 * @param contacts Array que contiene los contactos a añadir al nuevo grupo
	 * @assert.pre isXMLLoaded()
	 * @assert.pre contacts.length!=0;
	 */
	public void createNewGroup(String name, Contact[] contacts) {
		assert(isXMLLoaded());
		assert(contacts.length!=0);
		
		Element libreta = document.getDocumentElement();
		Element grupo = document.createElement("grupo");
		grupo.setAttribute("nombre", name);
		for(int i=0;i<contacts.length;i++){
			Element miembro=document.createElement("miembro"+(i+1));
			miembro.appendChild(document.createTextNode(contacts[i].getID()));
			grupo.appendChild(miembro);
		}
	}
	
	/**
	 * Devuelve un elemento Contact a partir de su id
	 * @param id ID del contacto buscado
	 * @return contacto buscado
	 * @assert.pre isXMLLoaded()
	 */
	public Contact getAnyContactById(String id) {
		assert(isXMLLoaded());
		
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
	 * @return persona buscada (copia)
	 * @assert.pre isXMLLoaded()
	 */
	public Person getPersonByNickname(String name) {
		assert(isXMLLoaded());
		
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
	 * @return grupo buscado (copia)
	 * @assert.pre isXMLLoaded()
	 */
	public Group getGroupByName(String name) {
		assert(isXMLLoaded());
		
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
	 */
	public void addContactToGroup(Contact contact, Group group) {
		assert(isXMLLoaded());
		//assert(document.getElementById(group.getID())!=null);
		//assert(document.getElementById(contact.getID())!=null);
		
		Element grupo = document.getElementById(group.getID());
		Element miembro =document.getElementById(contact.getID());
		grupo.appendChild(miembro);
	}

	/**
	 * Elimina un elemento Contact de un grupo (Group)
	 * @param contact Contacto a eliminar del grupo
	 * @param group Grupo buscado
	 * @assert.pre isXMLLoaded() 
	 */
	public void removeContactFromGroup(Contact contact, Group group) {
		assert(isXMLLoaded());
		
		Element grupo = document.getElementById(group.getID());
		NodeList miembros = grupo.getElementsByTagName("miembro");
		for(int i=0;i<miembros.getLength();i++){
			if(miembros.item(i).getTextContent()==contact.getID()){
				grupo.removeChild(miembros.item(i));
			}
			
		}
	}
	/**
	 * Elimina un elemento Contact del sistema
	 * @param contact Contacto a eliminar
	 * @assert.pre isXMLLoaded()
	 */
	public void removeContactFromSystem(Contact contact){
		assert(isXMLLoaded());
		
		Element contacto = document.getElementById(contact.getID());
		contacto.getParentNode().removeChild(contacto);
	}
	public boolean isContactById(String id){
		boolean is = false;
		if(!(getAnyContactById(id) == null)){
			is = true;
		}
		return is;
	}
	
}