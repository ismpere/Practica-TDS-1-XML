package uva.tds.pr1.equipo05;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main{
	public static void main(String[] args){
		EContactSystemInterface contacts = EContactSystemImpl.contactSystemFactory();
		Path ruta = Paths.get("src/uva/tds/pr1/equipo05/libreta_electronica.xml");
		Path ruta2 = Paths.get("libreta_electronica.xml");
		Path ruta3 = Paths.get("libreta_electronica2.xml");
		contacts.loadFrom(ruta);
		String[] correos = {"andrea@gmail.com"};
		contacts.createNewPerson("Andrea", "andalon", "Alonso", correos, null);
		contacts.updateTo(ruta2);
		
		String[] correos2 = {"yania@gmail.com"};
		contacts.loadFrom(ruta2);
		contacts.createNewPerson("Yania", "ycg", "Crespo", correos2, null);
		contacts.updateTo(ruta3);
		
		Person antonio = contacts.getPersonByNickname("toso");
		System.out.println(antonio.getID());
		System.out.println(antonio.getApellidos());
		System.out.println(antonio.getNombre());
		System.out.println(antonio.getTelefonos());
		System.out.println(antonio.getCorreos());
	}
}