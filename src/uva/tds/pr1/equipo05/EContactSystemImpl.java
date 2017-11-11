package uva.tds.pr1.equipo05;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
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

public class EContactSystemImpl implements EContactSystemInterface{
	
	boolean loaded, modified;
	InputSource source;
	Document document;
	DocumentBuilderFactory domParserFactory;
	DocumentBuilder parser;
	
	public EContactSystemImpl(){		
	}
	
	public static EContactSystemInterface contactSystemFactory(){
		return new EContactSystemImpl();		
	}

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
	
	public boolean isXMLLoaded(){
		return loaded;
	}
	
	public boolean isModifiedAfterLoaded(){
		return modified;
	}
	/**
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
		if(!(surName == null)){
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
		if(!(phones == null) && !phones.isEmpty()){
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

	public Contact getAnyContactById(String id) {
		assert(id!="");
		
		return null;
	}

	public Person getPersonByNickname(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Group getGroupByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addContactToGroup(Contact contact, Group group) {
		assert(document.getElementById(group.getID())!=null);
		assert(document.getElementById(contact.getID())!=null);
		
		Element grupo = document.getElementById(group.getID());
		Element miembro =document.getElementById(contact.getID());
		grupo.appendChild(miembro);
	}

	public void removeContactFromGroup(Contact contact, Group group) {
		
		
	}
	
	public void removeContactFromSystem(Contact contact){
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