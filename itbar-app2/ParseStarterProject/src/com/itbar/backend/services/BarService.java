package com.itbar.backend.services;

import com.itbar.backend.middleware.BarMiddleware;
import com.itbar.backend.services.callbacks.BarLogInCallback;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.views.Category;


/**
 * Servicio que recibe y delega las posibles operaciones que puede realizar un bar
 *
 * <p>Discucion:</p>
 * <p>Ver {@link UserService}</p>
 * Created by martin on 5/23/15.
 *
 * @see UserService
 */
public class BarService {

	public void getMenus(FindMultipleCallback<Category> cb) {
		if (Session.use().getCurrentUser() != null) {

			BarMiddleware.getMenus(cb);
		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

}
