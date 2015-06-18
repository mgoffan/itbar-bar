package com.itbar.backend.util;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by martin on 10/06/15.
 */
public class TogglerTest {

	Toggler<String> stringToggler;
	Toggler<Integer> integerToggler;
	Toggler<Boolean> booleanToggler;

	List<String> stringList = new ArrayList<>();
	List<Integer> integerList = new ArrayList<>();
	List<Boolean> booleanList = new ArrayList<>();


	@Before
	public void setUp() throws Exception {
		
		stringList.add("Hola");
		stringList.add("Chau");

		stringToggler = new Toggler<>(stringList);

//		integerList.add(1);



		booleanList.add(true);
		booleanList.add(false);

		booleanToggler = new Toggler<>(booleanList);

	}

	@Test(expected = IllegalArgumentException.class)
	public void TogglerTest() throws IllegalArgumentException {
		integerToggler = new Toggler<>(integerList);


	}

	@Test
	public void TogglerTest2() throws Exception {
		try {
			integerToggler = new Toggler<>(integerList);
		} catch (IllegalArgumentException ex) {
			assertNotNull("not null", ex);
		}
	}

	@Test
	public void testGetNext() throws Exception {

		assertTrue("Chau == Chau", stringToggler.getNext().equals("Chau"));
		assertFalse("Hola != Chau", stringToggler.getNext().equals("Chau"));
	}

	@Test
	public void testPeekNext() throws Exception {

		assertEquals(false, booleanToggler.peekNext());
		assertEquals(false, booleanToggler.peekNext());
	}
}