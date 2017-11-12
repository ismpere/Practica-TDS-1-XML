package uva.tds.pr1.equipo05;

import java.util.ArrayList;

/**
 * 
 * @author ismpere
 * @author martorb
 *
 */
public class Contact {
	String id;
	/**
	 * Constructor por defecto de la clase Contact
	 * @assert.pre !id.equals("")
	 * @assert.pre id.length()<=20
	 * @param id
	 * 
	 */
	public Contact(String id){
		assert(!id.equals(""));
		assert(id.length()<=20);
		this.id = id;
	}
	/**
	 * Devuelve el ID del contacto
	 * @return ID
	 */
	public String getID(){
		return id;
	}
	/**
	 * Cambia el ID del contacto
	 * @assert.pre !id.equals("")
	 * @assert.pre id.length()<=20
	 * @param id
	 */
	public void setID(String id){
		assert(!id.equals(""));
		assert(id.length()<=20);
		this.id = id;
	}
	/**
	 * Pasa un arrayList de Contact a un array del mismo tipo
	 * @param x
	 * @return Contact[] contactos
	 */
	protected Contact[] toArray(ArrayList<Contact> x){
		Contact y[] = new Contact[x.size()];
		for(int i=0; i<x.size(); i++){
			y[i] = x.get(i);
		}
		return y;
	}
	
}