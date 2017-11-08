package uva.tds.pr1.equipo05;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import uva.tds.pr1.equipo05.aux.SimpleErrorHandler;
import java.lang.IllegalArgumentException;

public class EContactSystemImpl implements EContactSystemInterface{
	
	public static EContactSystemInterface contactSystemFactory(){
		return null;		
	}

	public void loadFrom(Path pathToXML) {
		FileReader input;
		InputSource source;
		Document document;
		DocumentBuilderFactory domParserFactory;
		DocumentBuilder parser;
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
		System.out.println(parser.getClass());
		System.out.println(parser.isValidating());
		System.out.println(document.getDoctype().getName());
		
	}

	public void updateTo(Path pathToXML) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isXMLLoaded(){
		return true;
	}
	
	public boolean isModifiedAfterLoaded(){
		return true;
	}

	public void createNewPerson(String name, String nickname, String surName, String[] emails,
			Map<String, EnumKindOfPhone> phones) {
		// TODO Auto-generated method stub
		
	}

	public void createNewGroup(String name, Contact[] contacts) {
		// TODO Auto-generated method stub
		
	}

	public Contact getAnyContactById(String id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	public void removeContactFromGroup(Contact contact, Group group) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeContactFromSystem(Contact contact){
		
	}
	
}