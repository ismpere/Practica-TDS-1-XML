package uva.tds.pr1.equipo05;
import java.util.*;
/**
 * 
 * @author martorb
 *
 */
public class Person extends Contact{
	private String alias;
	private String nombre;
	private String apellidos;
	private String correo;
	Map<EnumKindOfPhone,String> lista_telefonos;
	
	/**
	 * Constructor por defecto de la clase Person
	 * @param alias
	 */
	public Person(String alias){
		super(alias);
	}
	
	/**
	 * Cambia el nombre de la persona
	 * @assert !nombre.equals("")
	 * @assert nombre.length<50)
	 * @param nombre
	 */
	public void setNombre(String nombre){
		assert(!nombre.equals(""));
		assert(nombre.length()<50);
		this.nombre=nombre;
	}
	
	/**
	 * Cambia los apellidos de la persona
	 * @assert !apelidos.equals("")
	 * @assert apellidos.length()<50
	 * @param apellidos
	 */
	public void setApellidos(String apellidos){
		assert(!apellidos.equals(""));
		assert(apellidos.length()<50);
		this.apellidos=apellidos;
	}
	
	/**
	 * Cambia el correo de la persona
	 * @assert.pre !correo.equals("")
	 * @assert.pre correo.contains("@")
	 * @assert.pre correo.length()<100
	 * @param correo
	 */
	public void setCorreo(String correo){
		assert(correo.contains("@"));
		assert(!correo.equals(""));
		assert(correo.length()<100);
		this.correo=correo;
	}
	
	/**
	 * Cambia el telefono de la persona
	 * @assert !num_telefono.equals("")
	 * @assert num_telefono.length()<50
	 * @param num_telefono
	 */
	public void addNum_telefono(String num_telefono,EnumKindOfPhone tipo){
		assert(!num_telefono.equals(""));
		assert(num_telefono.length()<50);
		lista_telefonos.put(tipo,num_telefono);
	}
	/**
	 * Devuelve el alias de la persona
	 * @return Alias
	 */
	public String getAlias(){
		return super.getID();
	}
	/**
	 * Devuelve el nombre de la persona
	 * @return Nombre.
	 */
	public String getNombre(){
		return nombre;
	}
	/**
	 * Devuelve los apellidos de la persona
	 * @return Apellidos.
	 */
	public String getApellidos(){
		return apellidos;
	}
	/**
	 * Devuelve la dirección de correo electrónico de la persona
	 * @return Correo electrónico.
	 */
	public String getCorreo(){
		return correo;
	}
	/**
	 * 
	 * @return Numero de telefono.
	 */
	public String getTelefono(EnumKindOfPhone tipo){
		return lista_telefonos.get(tipo);
	}
	
}