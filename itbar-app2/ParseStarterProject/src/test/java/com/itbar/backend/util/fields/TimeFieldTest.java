package com.itbar.backend.util.fields;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martin on 18/06/15.
 */
public class TimeFieldTest {

	TimeField field;

	@Before
	public void setUp() throws Exception {
		field = new TimeField(true);

		field.setValue("hola");
	}

	@Test
	public void testIsFormat24hs() throws Exception {

		assertTrue(field.isFormat24hs());

		field.setValue("02:25");

		assertTrue(field.isFormat24hs());

		field.setFormat24hs(false);

		assertFalse(field.isFormat24hs());

	}

	@Test
	public void testIsValid() throws Exception {

		assertFalse(field.isValid());

		field.setValue("14:25");

		assertTrue(field.isValid());

		field.setValue("1:25");

		assertFalse(field.isValid());

		field.setValue("25:34");

		assertFalse(field.isValid());

	}

	@Test
	public void testGetValue() throws Exception {

		field.setValue("14:25");

		assertTrue(field.getValue().equals("14:25"));
		assertFalse(field.getValue().equals("02:25 PM"));

	}
}