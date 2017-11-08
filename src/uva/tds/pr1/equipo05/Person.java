package uva.tds.pr1.equipo05;
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
	private String num_telefono;
	
	/**
	 * Constructor por defecto de la clase Person
	 * @param alias
	 */
	public Person(String alias){
		super(alias);
	}
	
	/**
	 * Cambia el nombre de la persona
	 * @param nombre
	 */
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	/**
	 * Cambia los apellidos de la persona
	 * @param apellidos
	 */
	public void setApellidos(String apellidos){
		this.apellidos=apellidos;
	}
	
	/**
	 * Cambia el correo de la persona
	 * @assert.pre correo.contains("@")
	 * @param correo
	 */
	public void setCorreo(String correo){
		this.correo=correo;
	}
	
	/**
	 * Cambia el telefono de la persona
	 * @param num_telefono
	 */
	public void setNum_telefono(String num_telefono){
		this.num_telefono=num_telefono;
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
	public String getTelefono(){
		return num_telefono;
	}
	
}