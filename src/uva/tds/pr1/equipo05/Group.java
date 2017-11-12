package uva.tds.pr1.equipo05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Implementacion de la clase Group
 * @author ismpere
 * @author martorb
 */
public class Group extends Contact{
	
	ArrayList<Contact> contactos;
	/**
	 * Constructor por defecto de la clase Group
	 * @param id nombreGrupo
	 */
	public Group(String id){
		super(id);
		contactos = new ArrayList<Contact>();
	}
	/**
	 * Constructor de la clase Group
	 * @param id nombreGrupo
	 * @param contactos
	 */
	public Group(String id, Contact[] contactos){
		super(id);
		this.contactos = new ArrayList<Contact>(Arrays.asList(contactos));
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
		boolean cont = false;
		for(int i=0; i<contactos.size(); i++){
			if(contactos.get(i).getID().equals(c.getID())){
				cont = true;
				break;
			}
		}
		return cont;
	}
	/**
	 * Elimina el contacto del grupo
	 * @assert.pre containsContact(c)
	 * @param c Contacto
	 */
	public void removeContact(Contact c){
		assert(containsContact(c));
		for(int i=0; i<contactos.size(); i++){
			if(contactos.get(i).getID().equals(c.getID())){
				contactos.remove(i);
			}
		}
	}
	/**
	 * Devuelve una lista de los contactos del grupo
	 * @return Contact[] contactos
	 */
	public Contact[] getContactos(){
		return ((List<Contact>)contactos).toArray(new Contact[contactos.size()]);
	}
	
	
}