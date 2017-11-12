package uva.tds.pr1.equipo05;
import java.util.*;

import org.w3c.dom.Element;
/**
 * 
 * @author martorb
 *
 */
public class Person extends Contact{
	private String alias;
	private String nombre;
	private String apellidos;
	private ArrayList<String> correos;
	Map<String, EnumKindOfPhone> lista_telefonos;
	
	/**
	 * Constructor por defecto de la clase Person
	 * @param alias
	 */
	public Person(String nombre, String alias, String[] correos){
		super(alias);
		this.nombre = nombre;
		this.correos = new ArrayList<String>(Arrays.asList(correos));
		lista_telefonos = new HashMap<String, EnumKindOfPhone>();
	}
	/**
	 * 
	 * @param nombre
	 * @param alias
	 * @param correos
	 * @param lista_telefonos
	 */
	public Person(String nombre, String alias, String[] correos, 
			Map<String, EnumKindOfPhone> lista_telefonos){
		super(alias);
		this.nombre = nombre;
		this.correos = new ArrayList<String>(Arrays.asList(correos));
		this.lista_telefonos = new HashMap<String, EnumKindOfPhone>(lista_telefonos);
	}
	/**
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param alias
	 * @param correos
	 * @param lista_telefonos
	 */
	public Person(String nombre, String apellidos, String alias, String[] correos, 
			Map<String, EnumKindOfPhone> lista_telefonos){
		super(alias);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.correos = new ArrayList<String>(Arrays.asList(correos));
		this.lista_telefonos = new HashMap<String, EnumKindOfPhone>(lista_telefonos);
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
	public void addCorreo(String correo){
		assert(correo.contains("@"));
		assert(!correo.equals(""));
		assert(correo.length()<100);
		this.correos.add(correo);
	}
	
	/**
	 * Cambia el telefono de la persona
	 * @assert !num_telefono.equals("")
	 * @assert num_telefono.length()<50
	 * @param num_telefono
	 */
	public void addNum_telefono(String num_telefono, EnumKindOfPhone tipo){
		assert(!num_telefono.equals(""));
		assert(num_telefono.length()<50);
		lista_telefonos.put(num_telefono,tipo);
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
	public String[] getCorreos(){
		return (String[])correos.toArray();
	}
	/**
	 * Devuelve una lista de numeros del tipo tipo
	 * @param tipo
	 * @return Lista de telefonos
	 */
	public String[] getTelefonosTipo(EnumKindOfPhone tipo){
		ArrayList<String> numeros = new ArrayList<String>();
		for (Map.Entry<String, EnumKindOfPhone> entry : lista_telefonos.entrySet()){
			if(entry.getValue().equals(tipo)){
				numeros.add(entry.getKey());
			}
		}
		String numerosTipo[] = new String[numeros.size()];
		for(int i=0; i<numeros.size(); i++){
			numerosTipo[i] = numeros.get(i);
		}
		return numerosTipo;
	}
	/**
	 * Devuelve un mapa de los telefonos con su tipo
	 * @return telefonos
	 */
	public Map<String, EnumKindOfPhone> getTelefonos(){
		Map<String, EnumKindOfPhone> aux = new HashMap<String, EnumKindOfPhone>(lista_telefonos);
		return aux;
	}
	
	
}