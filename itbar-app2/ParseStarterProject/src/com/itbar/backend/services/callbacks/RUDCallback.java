package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

/**
 * RUD: Read, Update, Delete
 *
 * <p>
 * Un <code>RUDCallback</code> se utiliza para operaciones de tipo RUD
 * </p>
 * <p>La forma mas facil de usar un <code>RUDCallback</code> es a traves de una clase
 * anonima. Overrideando los metodos <code>success</code> y <code>error</code> para especificar
 * que se debe hacer una vez que se complete el login.</p>
 *
 * Created by martin on 5/23/15.
 */
public interface RUDCallback {
	/**
	 * Overridear esta funcion con el codigo a ejecutarse despues de que el loggeo fue exitoso
	 */
	void success();
	/**
	 * Overridear esta funcion con el codigo a ejecutarse despues de que el loggeo fue erroneo
	 * @param e RemoteError con el codigo y mensaje de error
	 */
	void error(RemoteError e);
}
