package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

/**
 * <p>
 * Un <code>FindOneCallback</code> se utiliza para buscar un objeto de tipo T
 * </p>
 * <p>La forma mas facil de usar un <code>FindOneCallback</code> es a traves de una clase
 * anonima. Overrideando los metodos <code>success</code> y <code>error</code> para especificar
 * que se debe hacer una vez que se complete el login.</p>
 *
 * <p>Por ejemplo: este codigo trae una categorias</p>
 *
 * <pre>
 *
 *   ServiceRepository.getInstance().getBarService().getMenu(new FindOneCallback<Category>() {
 *    public void success(Category object) {
 *      hacerAlgoConLaCategoria(object);
 *    }
 *
 *    public void error(RemoteError e) {
 *      ocurrioUnError();
 *    }
 *  });
 *
 * </pre>
 * Created by martin on 5/23/15.
 */
public interface FindOneCallback<T> {
	/**
	 * Overridear esta funcion con el codigo a ejecutarse despues de que el loggeo fue exitoso
	 * @param object el objeto de tipo T traido
	 */
	void success(T object);

	/**
	 * Overridear esta funcion con el codigo a ejecutarse despues de que el loggeo fue erroneo
	 * @param e RemoteError con el codigo y mensaje de error
	 */
	void error(RemoteError e);
}
