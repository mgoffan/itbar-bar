package com.itbar.backend.services.callbacks;

import com.itbar.backend.services.RemoteError;

import java.util.List;

/**
 * <p>
 * Un <code>FindMultipleCallback</code> se utiliza para buscar multiples objetos de tipo T
 * </p>
 * <p>La forma mas facil de usar un <code>FindMultipleCallback</code> es a traves de una clase
 * anonima. Overrideando los metodos <code>success</code> y <code>error</code> para especificar
 * que se debe hacer una vez que se complete el login.</p>
 *
 * <p>Por ejemplo: este codigo trae todas las categorias</p>
 *
 * <pre>
 *
 *   ServiceRepository.getInstance().getBarService().getMenus(new FindMultipleCallback<Category>() {
 *    public void success(List<Category> objects) {
 *      hacerAlgoConLasCategorias(objects);
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
public interface FindMultipleCallback<T> {
	/**
	 * Overridear esta funcion con el codigo a ejecutarse despues de que el loggeo fue exitoso
	 * @param objects el listado de objetos de tipo T traidos
	 */
	void success(List<T> objects);

	/**
	 * Overridear esta funcion con el codigo a ejecutarse despues de que el loggeo fue erroneo
	 * @param e RemoteError con el codigo y mensaje de error
	 */
	void error(RemoteError e);
}
