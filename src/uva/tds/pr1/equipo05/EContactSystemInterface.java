package uva.tds.pr1.equipo05;

import java.nio.file.Path;
import java.util.Map;

/**
 * 
 * @author martorb
 * @author ismpere
 *
 */
public interface EContactSystemInterface {
	
	/**
	 * 
	 * @return
	 */
	public static EcontactSystemInterface contactSystemFactory();
	
	/**
	 * 
	 * @param pathToXML
	 */
	void loadFrom(Path pathToXML);
	
	/**
	 * 
	 * @param pathToXML
	 */
	void updateTo(Path pathToXML);
	
	/**	boolean isModifiedAfterLoaded();
	 * 
	 * @param name
	 * @param nickname
	 * @param surName
	 * @param emails
	 * @param phones
	 */
	void createNewPerson(String name, String nickname, String surName, String[] emails, Map<String,EnumKindOfPhone> phones);
	
	/**
	 * 
	 * @param name
	 * @param contacts
	 */
	void createNewGroup(String name, Contact[] contacts);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Contact getAnyContactById(String id);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	Person getPersonByNickname(String name);
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	Group getGroupByName(String name);
	
	/**
	 * 
	 * @param contact
	 * @param group
	 */
	void addContactToGroup(Contact contact, Group group);
	
	/**
	 * 
	 * @param contact
	 * @param group
	 */
	void removeContactFromGroup(Contact contact, Group group);
	
	/**
	 * 
	 * @param contact
	 */
	void removeContactFromSystem(Contact contact);
	
}