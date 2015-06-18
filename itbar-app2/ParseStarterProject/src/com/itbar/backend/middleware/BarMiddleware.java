package com.itbar.backend.middleware;

import com.itbar.backend.middleware.translators.CategoryTranslator;
import com.itbar.backend.middleware.translators.MenuItemTranslator;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.views.Category;
import com.itbar.backend.services.views.MenuItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Discucion:</p>
 *
 * Created by martin on 5/23/15.
 */
public class BarMiddleware {

	/**
	 * Algoritmo para llevar de una lista de {@link ParseObject} a una lista de {@link Category} que
	 * tiene cada {@link Category} sus correspondientes {@link MenuItem}
	 *
	 * @param list listado de {@link ParseObject}
	 * @return un listado de categorias con sus respectivos productos
	 */
	private static List<Category> getMenusFromTransformAlgorithm(List<ParseObject> list) {

		HashMap<Category, List<MenuItem>> categories = new HashMap<>();

		List<Category> result = new ArrayList<>();

		for (ParseObject parseMenuItem : list) {

			Category key = CategoryTranslator.toCategory(parseMenuItem.getParseObject("category"));

			if (categories.containsKey(key)) {
				categories.get(key).add(MenuItemTranslator.toMenuItem(parseMenuItem));
			} else {
				List<MenuItem> l = new ArrayList<>();
				l.add(MenuItemTranslator.toMenuItem(parseMenuItem));
				categories.put(key, l);
			}
		}

		for (Map.Entry<Category, List<MenuItem>> entry : categories.entrySet()) {
			Category c = new Category(entry.getKey().getName(), entry.getKey().getObjectId());
			c.addItems(entry.getValue());
			result.add(c);
		}

		return  result;

	}

	/**
	 * Trae los Menus o Categorias del proveedor de datos devolviendo un listado de categorias con
	 * sus productos correspondientes en su interior
	 * @param cb La rutina a ejecutarse una vez que el pedido haya finalizado
	 */
	public static void getMenus(final FindMultipleCallback<Category> cb) {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuItem");
		query.include("category");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {

					cb.success(
													getMenusFromTransformAlgorithm(list)
					);
				}
			}
		});

	}

	/**
	 * Agrega un menu o categoria en la BD
	 * @param category La categoria a ser agregada
	 * @param cb La rutina a ejecutarse cuando haya finalizado la comunicacion con la BD
	 */
	public static void addCategory(Category category, final RUDCallback cb) {

		ParseObject object = CategoryTranslator.fromCategory(category);

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}

	/**
	 * Elimina una categoria de la BD
	 *
	 * @param category La categoria a ser removida
	 * @param cb Codigo que se ejecutara al finalizar
	 */
	public static void removeCategory(Category category, final RUDCallback cb) {

		ParseObject object = CategoryTranslator.fromCategory(category);

		if (object.getObjectId() == null) {
			cb.error(new RemoteError(104, "Falta OID"));
			return;
		}

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}

	/**
	 * Actualiza la categoria o menu. Es equivalente a agregar una categoria, por como funciona
	 * {@link com.parse.Parse}
	 *
	 * @param category La cateogoria que se quiera modificar
	 * @param cb La rutina que se ejecutara cuando se haya modificado
	 */
	public static void updateCategory(Category category, final RUDCallback cb) {
		addCategory(category, cb);
	}

	/**
	 * Agrega un nuevo producto a la BD
	 * @param item el producto a ser agregado
	 * @param cb La rutina a ser ejecutada cuando finalice
	 */
	public static void addMenuItem(MenuItem item, final RUDCallback cb) {

		ParseObject object = MenuItemTranslator.fromMenuItem(item);

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});
	}

	/**
	 * Elimina un producto de la BD
	 * @param item el producto a ser eliminado
	 * @param cb La rutina a ejecutarse cuando se ejecute
	 */
	public static void removeMenuItem(MenuItem item, final RUDCallback cb) {

		ParseObject object = MenuItemTranslator.fromMenuItem(item);

		if (object.getObjectId() == null) {
			cb.error(new RemoteError(104, "Falta OID"));
			return;
		}

		object.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success();
				}
			}
		});

	}

	/**
	 * Actualiza un producto en la BD. En este caso, por como funciona {@linnk Parse} es equivalente
	 * a agregar un producto
	 *
	 * @param item El producto a modificar
	 * @param cb La rutina que se ejecutara cuando finalice
	 */
	public static void updateMenuItem(MenuItem item, RUDCallback cb) {
		addMenuItem(item, cb);
	}


}
