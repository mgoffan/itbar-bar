package com.itbar.backend.services;

import com.itbar.backend.services.views.Bar;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.User;

/**
 * <p>
 * Singleton que reune la informacion universal en el ciclo de vida de la app</p>
 *<p>
 * Discucion:</p>
 *<p>
 * Se eligio el patron Singleton porque puede haber solamente un usuario loggeado en la app, nunca
 * van a haber dos usuarios loggeados a la vez. La sesion adoptara la informacion correspondiente
 * a cada usuario</p>
 *
 * Created by martin on 5/23/15.
 */
public class Session {

	private static Session instance = null;

	private User currentUser = null;

	private Order currentOrder = null;

	private Session() {

	}


	public static Session use() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	protected void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	protected void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}
}
