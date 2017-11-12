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

import org.w3c.dom.Attr;
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
	private Document document, doc;
	private DocumentBuilderFactory domParserFactory;
	private DocumentBuilder parser;
	private Path ruta;
	private ArrayList<Person> personas;
	private ArrayList<Group> grupos;
	
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
		
		ruta = pathToXML;
		
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
		
		personas = new ArrayList<Person>();
		NodeList per = document.getElementsByTagName("persona");
		per.item(0).getAttributes().item(0).getTextContent();
		for(int i=0; i<per.getLength(); i++){
			String id = per.item(i).getAttributes().item(0).getTextContent();
			System.out.println(id);
			Element persona = document.getElementById(id);
			personas.add(getPerson(persona));
		}
		grupos = new ArrayList<Group>();
		NodeList grp = document.getElementsByTagName("grupo");
		grp.item(0).getAttributes().item(0).getTextContent();
		for(int i=0; i<grp.getLength(); i++){
			String id = grp.item(i).getAttributes().item(0).getTextContent();
			System.out.println(id);
			Element grupo = document.getElementById(id);
			grupos.add(getGroup(grupo));
		}
		System.out.println(grupos.size());
		
	}
	
	/**
	 * Actualiza la libreta electronica en otro XML en la ruta pasada como argumento
	 */
	public void updateTo(Path pathToXML) {
		
		doc = parser.newDocument();
		Element libreta = doc.createElement("libreta_electronica");
		doc.appendChild(libreta);
		for(int i=0; i<personas.size(); i++){
			personToLibreta(personas.get(i));
		}
		for(int i=0; i<grupos.size(); i++){
			groupToLibreta(grupos.get(i));
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		String systemId = document.getDoctype().getSystemId();
		DOMSource source = new DOMSource(doc);
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
		
		Person persona;
		if(phones!=null && phones.size()>0){
			persona = new Person(name, nickname, emails, phones);
		}else{
			persona = new Person(name, nickname, emails);
		}
		if(surName!=null){
			persona.setApellidos(surName);
		}
		personas.add(persona);
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
		
		grupos.add(new Group(name, contacts));	
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
		
		Contact x = null;
		if(getPersonByNickname(id)!=null){
			x = getPersonByNickname(id);
		}else{
			if(getGroupByName(id)!=null){
				x = getGroupByName(id);
			}
		}
		return x;
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
		
		Person x = null;
		for(int i=0; i<personas.size(); i++){
			if(personas.get(i).getID().equals(name)){
				x = personas.get(i);
			}
		}
		return x;		
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
		
		Group x = null;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getID().equals(name)){
				x = grupos.get(i);
			}
		}
		return x;
	}

	/**
	 * Añade un elemento Contact a un grupo (Group)
	 * @param contact Contacto a añadir al grupo
	 * @param group Grupo buscado
	 * @assert.pre isXMLLoaded()
	 * @assert.pre existsContactById(contact.getID())
	 * @assert.pre existGroupById(group.getID())
	 * @assert.pre !group.containsContact(contact)
	 */
	public void addContactToGroup(Contact contact, Group group) {
		assert(isXMLLoaded());
		assert(existContactById(contact.getID()));
		assert(existGroupById(group.getID()));
		assert(!group.containsContact(contact));
		
		getGroupByName(group.getID()).addContact(contact);
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
		assert(existContactById(group.getID()));
		assert(group.containsContact(contact));

		group.removeContact(contact);
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
		
		if(existPersonById(contact.getID())){
			personas.remove(contact);
		}else{
			grupos.remove(contact);
		}
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).containsContact(contact)){
				removeContactFromGroup(contact, grupos.get(i));
			}
		}
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
		return(existPersonById(id) || existGroupById(id));
	}
	public boolean existPersonById(String id){
		boolean is = false;
		for(int i=0; i<personas.size(); i++){
			if(personas.get(i).getID().equals(id)){
				is = true;
			}
		}
		return is;
	}
	public boolean existGroupById(String id){
		boolean is = false;
		for(int i=0; i<grupos.size(); i++){
			if(grupos.get(i).getID().equals(id)){
				is = true;
			}
		}
		return is;
	}
	private Person getPerson(Element persona){
		String nombre, id;
		String apellidos = null;
		Map<String, EnumKindOfPhone> telefonos = null;
		
		id = persona.getAttribute("alias");
		
		nombre = persona.getElementsByTagName("nombre").item(0).getTextContent();
		if(persona.getElementsByTagName("apellidos").getLength()>0){
			apellidos = persona.getElementsByTagName("apellidos").item(0).getTextContent();
		}
		NodeList nodeCorreos = persona.getElementsByTagName("correo");
		String correos[] = new String[nodeCorreos.getLength()];
		for(int i=0; i<nodeCorreos.getLength(); i++){
			correos[i] = nodeCorreos.item(i).getTextContent();
		}
		NodeList nodeTelefonos = persona.getElementsByTagName("telefono");
		if(nodeTelefonos.getLength()>0){
			telefonos = new HashMap<String, EnumKindOfPhone>(nodeTelefonos.getLength());
			for(int i=0; i<nodeTelefonos.getLength(); i++){
				NamedNodeMap attr = nodeTelefonos.item(i).getAttributes();
				String tipotelef = attr.getNamedItem("tipotelef").getTextContent();
				EnumKindOfPhone tt = EnumKindOfPhone.valueOf(tipotelef);
				String numero = nodeTelefonos.item(i).getTextContent();
				telefonos.put(numero, tt);
			}
		}
		Person x;
		if(telefonos!=null && telefonos.size()>0){
			x = new Person(nombre, id, correos, telefonos);
		}else{
			x = new Person(nombre, id, correos);
		}
		if(apellidos!=null){
			x.setApellidos(apellidos);
		}
		return x;
	}
	private Group getGroup(Element grupo){
		String name = grupo.getAttribute("nombregrupo");
		System.out.println(name);
		NodeList nodeContactos = grupo.getElementsByTagName("miembro");
		Contact contactos[] = new Contact[nodeContactos.getLength()];
		for(int i=0; i<nodeContactos.getLength(); i++){
			String id = nodeContactos.item(i).getAttributes().item(0).getTextContent();
			Element x = document.getElementById(id);
			if(x.getNodeName().equals("persona")){
				contactos[i]=getPerson(x);
			}else{
				contactos[i]=getGroup(x);
			}
		}
		return new Group(name, contactos);
	}
	private void personToLibreta(Person p){
		Element persona = doc.createElement("persona");
		persona.setAttribute("alias", p.getID());
		if(p.getApellidos() != null){
			Element apellidos = doc.createElement("apellidos");
			apellidos.appendChild(doc.createTextNode(p.getApellidos()));
			persona.appendChild(apellidos);
		}
		Element nombre = doc.createElement("nombre");
		nombre.appendChild(doc.createTextNode(p.getNombre()));
		persona.appendChild(nombre);
		for(int i=0; i<p.getCorreos().length; i++){
			Element correo = doc.createElement("correo");
			correo.appendChild(doc.createTextNode(p.getCorreos()[i]));
			persona.appendChild(correo);
		}
		if(p.getTelefonos() != null && !p.getTelefonos().isEmpty()){
			for (Map.Entry<String, EnumKindOfPhone> entry : p.getTelefonos().entrySet()){
				Element telefono = doc.createElement("telefono");
				telefono.setAttribute("tipotelef", entry.getValue().toString());
				Element numtelef = doc.createElement("numtelef");
				numtelef.appendChild(doc.createTextNode(entry.getKey()));
				telefono.appendChild(numtelef);
				persona.appendChild(telefono);
			}
		}
		addToLibreta(persona);

	}
	private void groupToLibreta(Group g){
		Element grupo = doc.createElement("grupo");
		grupo.setAttribute("nombre", g.getID());
		for(int i=0;i<g.getContactos().length;i++){
			Element miembro=doc.createElement("miembro");
			miembro.setAttribute("id", g.getContactos()[i].getID());
			grupo.appendChild(miembro);
		}
		addToLibreta(grupo);	

	}
	/**
	 * Añade el elemento x a la libreta de contactos
	 * @param x
	 */
	private void addToLibreta(Element x){
		Element libreta = doc.getDocumentElement();
		Element contacto = doc.createElement("contacto");
		contacto.appendChild(x);
		libreta.appendChild(contacto);	
		
		modified = true;
	}
	
}