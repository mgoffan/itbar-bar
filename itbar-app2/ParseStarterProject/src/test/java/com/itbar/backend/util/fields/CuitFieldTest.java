package com.itbar.backend.util.fields;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martin on 18/06/15.
 */
public class CuitFieldTest {

	CuitField field;

	@Before
	public void setUp() throws Exception {
		field = new CuitField(true);

		field.setValue("hola");
	}

	@Test
	public void testIsValid() throws Exception {

		assertEquals(false, field.isValid());

		field.setValue("111111");

		assertEquals(true, field.isValid());

	}

	@Test
	public void testIsRequired() throws Exception {

		assertEquals(true, field.isRequired());
	}
}