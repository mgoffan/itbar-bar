package com.itbar.frontend.Models;

/**
 * @see com.itbar.backend.util.Toggler
 * @deprecated se usa {@link com.itbar.backend.util.Toggler}
 */
public class CounterModel {

	int count = 0;

	public void add() {
		count++;
	}

	public int get() {
		return count;
	}

	public void set(int a) {
		count = a;
	}

	public int moduleFour() {
		return count % 4;
	}

	public boolean equals(int n) {
		return count == n;
	}


}
