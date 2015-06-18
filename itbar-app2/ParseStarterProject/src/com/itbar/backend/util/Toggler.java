package com.itbar.backend.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Clase generica que permite cambiar entre n estados en una lista
 * </p>
 *
 * <p>Asume que el valor por defecto es el 0</p>
 * Created by martin on 08/06/15.
 */
public class Toggler<T> {

	private List<T> values = new ArrayList<>();
	private Integer currentIndex = 0;

	public Toggler(List<T> values) throws IllegalArgumentException {
		if (values.size() == 0) {
			throw new IllegalArgumentException("Lista vacia");
		}
		this.values = values;
	}

	public T getNext() {
		return values.get((++currentIndex)%values.size());
	}

	public T peekNext() {
		return values.get((currentIndex+1)%values.size());
	}

}
