package uva.tds.pr1.equipo05;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main{
	public static void main(String[] args){
		EContactSystemInterface contacts = EContactSystemImpl.contactSystemFactory();
		System.out.println("Va 2");
		Path ruta = Paths.get("src/uva/tds/pr1/equipo05/libreta_electronica.xml");
		Path ruta2 = Paths.get("libreta_electronica.xml");
		Path ruta3 = Paths.get("libreta_electronica2.xml");
		
		System.out.println("Carga el XML de prueba: " + ruta.toString());
		contacts.loadFrom(ruta);
		Person isma = (Person)contacts.getAnyContactById("ismpere");
		System.out.println(isma.getNombre());
		String[] correos = {"andrea@gmail.com"};
		System.out.println("Crea una nueva persona, andalon");
		contacts.createNewPerson("Andrea", "andalon", "Alonso", correos, null);
		Person x = (Person)contacts.getAnyContactById("andalon");
		System.out.println(x.getNombre());
		contacts.updateTo(ruta2);
		
		System.out.println("Crea una nueva persona, ycg");
		String[] correos2 = {"yania@gmail.com"};
		contacts.createNewPerson("Yania", "ycg", "Crespo", correos2, null);
		
		System.out.println("\nAhora sacamos una persona y comprobamos sus datos,"
				+ " en este caso a toso");
		Person antonio = contacts.getPersonByNickname("toso");
		System.out.println("Imprimimos sus datos: ");
		System.out.println("Id(alias): " + antonio.getID());
		System.out.println("Nombre: " + antonio.getNombre());
		System.out.println("Apellidos: " + antonio.getApellidos());
		System.out.println("Numeros de telefono");
		HashMap<String, EnumKindOfPhone> numeros = new HashMap<String, EnumKindOfPhone>(antonio.getTelefonos());
		System.out.println(numeros);
		System.out.println("Ahora solo los del tipo MovilPersonal");
		String numerosTipo[] = antonio.getTelefonosTipo(EnumKindOfPhone.MovilPersonal);
		System.out.print("Numeros de tipo " + EnumKindOfPhone.MovilPersonal.toString() + ": [");
		for(int i=0; i<numerosTipo.length-1; i++){
			System.out.print(numerosTipo[i] + ", ");
		}
		System.out.println(numerosTipo[numerosTipo.length-1] + "]");
		System.out.print("Correos: [");
		String correoss[] = antonio.getCorreos();
		for(int i=0; i<correoss.length-1; i++){
			System.out.print(correoss[i] + ", ");
		}
		System.out.println(correoss[correoss.length-1] + "]");
		
		System.out.println("\nAhora sacamos el grupo1 y comprobamos sus datos");
		Group grupo = contacts.getGroupByName("grupo1");
		System.out.println("Imprimimos sus datos:");
		System.out.println("Id(nombregrupo): " + grupo.getID());
		System.out.print("Miembros: [" );
		Contact c[] = grupo.getContactos();
		for(int i=0; i<c.length-1; i++){
			System.out.print(c[i].getID() + ", ");
		}
		System.out.println(c[c.length-1].getID() + "]");
		
		grupo = contacts.getGroupByName("grupo1");
		System.out.println("Ahora eliminamos a toso del grupo y comprobamos que no esta");
		System.out.println("Lo tiene: " + grupo.containsContact(antonio));
		contacts.removeContactFromGroup(antonio, grupo);
		grupo = contacts.getGroupByName("grupo1");
		System.out.println("Imprimimos sus datos:");
		System.out.println("Id(nombregrupo): " + grupo.getID());
		System.out.print("Miembros: [" );
		c = grupo.getContactos();
		for(int i=0; i<c.length-1; i++){
			System.out.print(c[i].getID() + ", ");
		}
		System.out.println(c[c.length-1].getID() + "]\n");
		
		System.out.println("Ahora le eliminamos de la libreta y comprobamos que no esta");
		contacts.removeContactFromSystem(antonio);
		if(contacts.getPersonByNickname("toso")==null){
			System.out.println("Se ha borrado\n");
		}
		System.out.println("Añado a ycg a grupo1");
		Person yania = (Person)contacts.getAnyContactById("ycg");
		if(yania==null){
			System.out.println("Hola");
		}
		contacts.addContactToGroup(yania, grupo);
		grupo = contacts.getGroupByName("grupo1");
		System.out.println("Imprimimos sus datos:");
		System.out.println("Id(nombregrupo): " + grupo.getID());
		System.out.print("Miembros: [" );
		c = grupo.getContactos();
		for(int i=0; i<c.length-1; i++){
			System.out.print(c[i].getID() + ", ");
		}
		System.out.println(c[c.length-1].getID() + "]\n");
		
		contacts.removeContactFromSystem(contacts.getPersonByNickname("andalon"));
		contacts.removeContactFromSystem(contacts.getPersonByNickname("ycg"));
		
		
		System.out.println("\nActualiza el contenido en el XML de prueba : " + ruta2.toString());
		contacts.updateTo(ruta3);
	}
}