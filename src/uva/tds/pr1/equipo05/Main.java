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
		String[] correos = {"andrea@gmail.com"};
		System.out.println("Crea una nueva persona, andalon");
		contacts.createNewPerson("Andrea", "andalon", "Alonso", correos, null);
		
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
		
		System.out.println("Ahora eliminamos a toso del grupo y comprobamos que no esta");
		System.out.println(grupo.containsContact(antonio));
		contacts.removeContactFromGroup(antonio, grupo);
		grupo = contacts.getGroupByName("grupo1");
		System.out.println("Imprimimos sus datos:");
		System.out.println("Id(nombregrupo): " + grupo.getID());
		System.out.print("Miembros: [" );
		c = grupo.getContactos();
		for(int i=0; i<c.length-1; i++){
			System.out.print(c[i].getID() + ", ");
		}
		System.out.println(c[c.length-1].getID() + "]");
		
		/*Person c2 = contacts.getPersonByNickname(c[1].getID());
		System.out.println("Son iguales: " + c2.equals(antonio));
		System.out.println("Imprimimos sus datos: ");
		System.out.println("Id(alias): " + c2.getID());
		System.out.println("Nombre: " + c2.getNombre());
		System.out.println("Apellidos: " + c2.getApellidos());
		System.out.println("Numeros de telefono");
		HashMap<String, EnumKindOfPhone> numeros2 = new HashMap<String, EnumKindOfPhone>(antonio.getTelefonos());
		System.out.println(numeros2);
		System.out.println("Ahora solo los del tipo MovilPersonal");
		String numerosTipo2[] = antonio.getTelefonosTipo(EnumKindOfPhone.MovilPersonal);
		System.out.print("Numeros de tipo " + EnumKindOfPhone.MovilPersonal.toString() + ": [");
		for(int i=0; i<numerosTipo2.length-1; i++){
			System.out.print(numerosTipo2[i] + ", ");
		}
		System.out.println(numerosTipo2[numerosTipo2.length-1] + "]");
		System.out.print("Correos: [");
		String correoss2[] = antonio.getCorreos();
		for(int i=0; i<correoss2.length-1; i++){
			System.out.print(correoss2[i] + ", ");
		}
		System.out.println(correoss2[correoss2.length-1] + "]");*/
		
		System.out.println("\nActualiza el contenido en el XML de prueba : " + ruta2.toString());
		contacts.updateTo(ruta2);
	}
}