package com.itbar.backend.util;

import com.itbar.backend.util.fields.TextField;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martin on 10/06/15.
 */
public class FieldTest {

	Field field;

	@Before
	public void setUp() throws Exception {
		field = new TextField();

		field.setValue("Hola");
	}

	@Test
	public void testGetValue() throws Exception {

		assertTrue("son iguales", field.getValue().equals("Hola"));
		assertFalse("no son iguales", field.getValue().equals("hola"));

	}

	@Test
	public void testSetValue() throws Exception {

		field.setValue("Chau");

		assertTrue("son iguales", field.getValue().equals("Chau"));
		assertFalse("no son iguales", field.getValue().equals("chau"));

	}

	@Test
	public void testIsRequired() throws Exception {

		assertEquals(false, field.isRequired());

	}


}