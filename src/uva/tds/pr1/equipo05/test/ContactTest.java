package uva.tds.pr1.equipo05.test;

import static org.junit.Assert.*;
import org.junit.Test;
import uva.tds.pr1.equipo05.Contact;

/**
 * Implementacion de la clase ContactTest
 * @author ismpere
 * @author martorb
 *
 */
public class ContactTest {
	
	@Test
	public void testInicializacionIDValidoPequeño(){
		Contact x = new Contact("1");
		
		assertEquals(x.getID(),"1");
	}
	@Test
	public void testInicializacionIDValidoGrande(){
		Contact x = new Contact("Tengo-Veinte-Letras!");
		
		assertEquals(x.getID(),"Tengo-Veinte-Letras!");
	}
	
	@Test
	public void testSetIDValidoPequeño(){
		Contact x = new Contact("1");
		x.setID("2");
		
		assertEquals(x.getID(),"2");
	}
	@Test
	public void testSetIDValidoGrande(){
		Contact x = new Contact("1");
		x.setID("Tengo-Veinte-Letras!");
		
		assertEquals(x.getID(),"Tengo-Veinte-Letras!");
	}
	@Test(expected=NullPointerException.class)
	public void testInicializacionIDNoValidoNulo(){
		String s = null;
		Contact x = new Contact(s);
	}
	@Test(expected=AssertionError.class)
	public void testInicializacionIDNoValidoVacio(){
		Contact x = new Contact("");
	}
	@Test(expected=AssertionError.class)
	public void testInicializacionIDNoValidoGrande(){
		Contact x = new Contact("Tengo-+-Veinte-Letras");
	}
	@Test(expected=NullPointerException.class)
	public void testSetIDNoValidoNulo(){
		String s = null;
		Contact x = new Contact("1");
		x.setID(s);
	}
	@Test(expected=AssertionError.class)
	public void testSetIDNoValidoVacio(){
		Contact x = new Contact("1");
		x.setID("");
	}
	@Test(expected=AssertionError.class)
	public void testSetIDNoValidoGrande(){
		Contact x = new Contact("1");
		x.setID("Tengo-+-Veinte-Letras");
	}
	
	
}