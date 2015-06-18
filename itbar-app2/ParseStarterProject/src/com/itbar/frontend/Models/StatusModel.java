package com.itbar.frontend.Models;

/**
 * @deprecated se usa {@link com.itbar.backend.util.Toggler}
 * @see com.itbar.backend.util.Toggler
 */
public class StatusModel {

	public boolean state = true;

	public StatusModel(boolean state) {
		this.state = state;
	}

	public boolean change() {
		if (state == true) {
			state = false;
		} else {
			state = true;
		}
		return state;
	}

	public boolean getState() {
		return state;
	}


}
