package uva.tds.pr1.equipo05.test;

import static org.junit.Assert.*;
import java.nio.file.*;
import java.io.*;

import org.junit.*;
import org.xmlunit.builder.*;
import org.xmlunit.diff.*;

import uva.tds.pr1.equipo05.*;


public class EcontactSystemImplTest {
	
	@AfterClass
	public static void tearDownGeneral() throws IOException{
		Path dir = Paths.get(System.getProperty("user.id"),"test/xmlTestFiles/");
		DirectoryStream<Path> archivos_a_borrar = Files.newDirectoryStream(dir,"*.out.xml");
		for(Path path : archivos_a_borrar){
			Files.delete(path);
		}
		archivos_a_borrar.close();
	}
	
	@Test
	public void testLoadFrom() {
		//carga un XML de prueba desde una carpeta
		EContactSystemInterface libreta = EContactSystemImpl.contactSystemFactory();		
		Path entrada_xml = Paths.get(System.getProperty("user.id"),"test/xmlTestFiles/testLoadFrom.xml");
		libreta.loadFrom(entrada_xml);
		Path salida_xml = Paths.get(System.getProperty("user.id"),"test/xmlTestFiles/testLoadFrom.out.xml");
		libreta.updateTo(salida_xml);
		Diff miDiff = DiffBuilder.compare(entrada_xml.toFile()).withTest(salida_xml.toFile()).checkForIdentical()
					  .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.Default)).build();
		
		assertTrue(libreta.isXMLLoaded());
		assertFalse(libreta.isModifiedAfterLoaded());
		assertFalse("¿Son los XML idénticos? "+miDiff.toString(),miDiff.hasDifferences());
		
	}
	
	@Test
	public void testIsXMLLoaded() {
		EContactSystemInterface libreta = EContactSystemImpl.contactSystemFactory();
		Path entrada_xml = Paths.get(System.getProperty("user.id"),"test/xmlTestFiles/testLoadFrom.xml");
		libreta.loadFrom(entrada_xml);
		assertTrue(libreta.isXMLLoaded());
	}
	
	
	@Test
	public void testIsModifiedAfterLoaded() {
		EContactSystemInterface libreta = EContactSystemImpl.contactSystemFactory();
		Path entrada_xml = Paths.get(System.getProperty("user.id"),"test/xmlTestFiles/testLoadFrom.xml");
		libreta.loadFrom(entrada_xml);
	}
	

	
	
	
	
}
