package com.itbar.backend.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martin on 18/06/15.
 */
public class FormTest {

	Form userForm = FormBuilder.buildUserForm();

	@Before
	public void setUp() throws Exception {

		userForm.set(FieldKeys.KEY_LEGAJO, "01234");
		userForm.set(FieldKeys.KEY_EMAIL, "martin.goffan@me.com");
		userForm.set(FieldKeys.KEY_PHONE, "44444444");
		userForm.set(FieldKeys.KEY_NAME, "1martin");

	}

	@Test
	public void testIsValid() throws Exception {

		assertFalse(userForm.isValid());

		userForm.set(FieldKeys.KEY_LEGAJO, "12345");

		assertFalse(userForm.isValid());

		userForm.set(FieldKeys.KEY_NAME, "martin");
		userForm.set(FieldKeys.KEY_SURNAME, "goffan");

		assertTrue(userForm.isValid());

	}

	@Test
	public void testHasBeenValidated() throws Exception {

		assertFalse(userForm.hasBeenValidated());

		userForm.set(FieldKeys.KEY_LEGAJO, "12345");
		userForm.set(FieldKeys.KEY_NAME, "martin");
		userForm.set(FieldKeys.KEY_SURNAME, "goffan");

		assertFalse(userForm.hasBeenValidated());

		userForm.isValid();

		assertTrue(userForm.hasBeenValidated());


	}

	@Test
	public void testCollectErrors() throws Exception {

		assertEquals(0, userForm.collectErrors().size());

		userForm.isValid();

		assertTrue(userForm.collectErrors().size() > 0);
		assertEquals(2, userForm.collectErrors().size());

		userForm.set(FieldKeys.KEY_LEGAJO, "12345");

		assertEquals(0, userForm.collectErrors().size());

		userForm.set(FieldKeys.KEY_NAME, "martin");
		userForm.set(FieldKeys.KEY_SURNAME, "goffan");

		userForm.isValid();

		assertEquals(0, userForm.collectErrors().size());

	}
}