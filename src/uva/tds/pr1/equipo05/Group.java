package uva.tds.pr1.equipo05;

import java.util.ArrayList;
/**
 * Implementacion de la clase Group
 * @author ismpere
 * @author martorb
 */
public class Group extends Contact{
	
	ArrayList<Contact> contactos;
	/**
	 * Constructor por defecto de la clase Group
	 * @param id
	 */
	public Group(String id){
		super(id);
		contactos = new ArrayList<Contact>();
	}
	/**
	 * Añade el contacto al grupo
	 * @assert.pre !containsContact(c)
	 * @param c Contacto
	 */
	public void addContact(Contact c){
		assert(!containsContact(c));
		contactos.add(c);
	}
	/**
	 * Devuelve si el grupo contiene o no un contacto
	 * @param c Contacto
	 * @return boolean está en el grupo
	 */
	public boolean containsContact(Contact c){
		return contactos.contains(c);
	}
	/**
	 * Elimina el contacto del grupo
	 * @assert.pre containsContact(c)
	 * @param c Contacto
	 */
	public void removeContact(Person c){
		assert(containsContact(c));
		contactos.remove(c);
	}
	/**
	 * Devuelve una lista de los contactos del grupo
	 * @return Contact[] contactos
	 */
	public Contact[] getContactos(){
		return (Contact[])contactos.toArray();
	}
	
	
}