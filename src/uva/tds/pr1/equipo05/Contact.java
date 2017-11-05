package uva.tds.pr1.equipo05;
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
	
}