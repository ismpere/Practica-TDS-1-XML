package uva.tds.pr1.equipo05;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main{
	public static void main(String[] args){
		EContactSystemInterface contacts = EContactSystemImpl.contactSystemFactory();
		Path ruta = Paths.get("src/uva/tds/pr1/equipo05/libreta_electronica.xml");
		contacts.loadFrom(ruta);
		String[] correos = {"andrea@gmail.com"};
		contacts.createNewPerson("Andrea", "andalon", null, correos, null);
	}
}