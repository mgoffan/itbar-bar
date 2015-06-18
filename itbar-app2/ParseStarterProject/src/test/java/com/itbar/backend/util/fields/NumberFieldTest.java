package com.itbar.backend.util.fields;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martin on 18/06/15.
 */
public class NumberFieldTest {

	NumberField<Integer> integerField;
	NumberField<Double> doubleNumberField;

	@Before
	public void setUp() throws Exception {

		integerField = new NumberField<>();
		doubleNumberField = new NumberField<>();
	}

	@Test
	public void testHasFloatingPoint() throws Exception {

		assertFalse(integerField.hasFloatingPoint());
		assertFalse(doubleNumberField.hasFloatingPoint());

		doubleNumberField.setHasFloatingPoint(true);

		assertTrue(doubleNumberField.hasFloatingPoint());

	}

	@Test
	public void testIsValid() throws Exception {

		assertFalse(integerField.isValid());

		integerField.setValue("2");

		assertTrue(integerField.isValid());

		integerField.setValue("2.2");

		assertTrue(integerField.isValid());

		assertFalse(doubleNumberField.isValid());

		doubleNumberField.setValue("2");

		assertTrue(doubleNumberField.isValid());

		doubleNumberField.setValue("2.26496286421863821638210638209");

		assertTrue(doubleNumberField.isValid());

	}

	@Test
	public void testRetrieveResult() throws Exception {

		doubleNumberField.setValue("13.22222222222222");

		assertTrue(doubleNumberField.retrieveResult().equals(13.22222222222222));



	}
}