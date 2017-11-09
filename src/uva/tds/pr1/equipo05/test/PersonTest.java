package uva.tds.pr1.equipo05.test;

import static org.junit.Assert.*;
import org.junit.Test;
import uva.tds.pr1.equipo05.Person;

import uva.tds.pr1.equipo05.EnumKindOfPhone;

/**
 * 
 * @author martorb
 *
 */
public class PersonTest {

	
	@Test
	public void testNombreValidoCorto(){
		Person x=new Person("2");
		x.setNombre("A");
		assertEquals(x.getNombre(),"2");
	}
	
	@Test
	public void testNombreValidoLargo(){
		Person x=new Person("2");
		x.setNombre("Este-nombre-tiene-nadamas-ynadamenos-que49-letras");
		assertEquals(x.getNombre(),"Este-nombre-tiene-nadamas-ynadamenos-que49-letras");
	}
	
	@Test (expected=AssertionError.class)
	public void testNombreNoValidoVacio(){
		Person x=new Person("2");
		x.setNombre("");
	}
	
	@Test (expected=AssertionError.class)
	public void testNombreNoValidoLargo(){
		Person x=new Person("2");
		x.setNombre("Estenombreesclarayevidentementeunnombredemasiadolargo");
	}
	
	@Test
	public void testApellidosValidosCortos(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testApellidosValidosLargos(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testApellidosNoValidosVacio(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testApellidosNoValidosLargos(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testCorreoValidoCorto(){
		fail("Not yet implemented");
	}
	
	@Test
	public void tesCorreoValidoLargo(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testCorreoNoValidoVacio(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testCorreoNoValidoVariosArrobas(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testCorreoNoValidoSinArroba(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testCorreoNoValidoLargo(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testTelefonoValidoCorto(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testTelefonoValidoLargo(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testTelefonoNoValidoVacio(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testTelefonoNoValidoLargo(){
		fail("Not yet implemented");
	}
	
	
}
