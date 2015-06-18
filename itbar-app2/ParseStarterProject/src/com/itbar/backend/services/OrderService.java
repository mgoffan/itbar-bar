package com.itbar.backend.services;

import com.itbar.backend.middleware.OrderMiddleware;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.callbacks.SaveOrderCallback;
import com.itbar.backend.services.views.MenuItem;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.OrderProduct;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;

/**
 *<p>
 * Discucion:</p>
 *
 * <p>Ver {@link UserService}</p>
 *
 * Created by martin on 5/23/15.
 *
 * @see UserService
 *
 *
 */
public class OrderService {

	/**
	 * Se fija si se puede delegar la operacion de buscar las ordenes. Esto puede suceder si el form
	 * es valido y si el usuario tiene permiso para hacerlo, es decir es un bar.
	 *
	 * @param form Formulario que porta el estado de los pedidos a ser buscados
	 * @param cb
	 * @see Form
	 */
	public void getOrders(Form form, FindMultipleCallback cb) {

		if (form.hasBeenValidated() || form.isValid()) {

			if (Session.use().getCurrentUser() != null) {
				OrderMiddleware.fetchWithStatus(form.get(FieldKeys.KEY_STATUS), Session.use().getCurrentUser().getObjectId(), cb);
			} else {
				cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
			}
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "Formulario invalido"));
		}

	}

	/**
	 * <p>Discucion:</p>
	 * <p>Por cuestiones que tienen que ver con como almacena Parse sus datos mediante un motor de
	 * base de datos NoSQL, los queries a esta resultan engorrosos, complicados y ensucian el codigo
	 * cuando se trata de almacenar referencias en un array, en esta DB. Ademas excede a la materia y
	 * no la compete. Por esta razon la estrategia que tuvimos que adoptar es la de en otra tabla
	 * guardar los productos que pertenecian a una Pedido. Esto hace que tengamos que acumular todos
	 * los productos localmente y luego cuando el pedido es confirmado se empaqueta la trama que
	 * consiste de todos los productos con la informacion relevante en el pedido global, como su
	 * horario y luego se emite el pedido. Esto hace de <b>Order</b> una <i>particularidad</i> porque
	 * a lo largo y ancho de la app las vistas eran dummy, es decir no importa que informacion tengan
	 * o como la trate el usuario, siempre se van a comunicar con los servicios mediante Form</p>
	 *
	 *
	 * @param form Formulario que contiene la informacion particular del pedido
	 * @param cb Callback que avisa en caso de error o exito
	 *
	 * @see Order
	 * @see OrderMiddleware
	 * @see Form
	 */
	public void placeOrder(Form form, final SaveOrderCallback cb) {
		/**(Parse) Con el usuario agarra a Order y primero crea en Parse un objeto de tipo order, con el id de Order mete todos
		 * los OrderProducts y los linkea al MenuItem y al Order. Esto se hace usando el metodo saveAllInBackground de Parse*/


		// Chequear validez
		if (form.hasBeenValidated() || form.isValid()) {


			// Rellenar el pedido
			Order order = Session.use().getCurrentOrder();

			order.setBuyer(Session.use().getCurrentUser());

			order.setComment(form.get(FieldKeys.KEY_COMMENT));
			order.setHorario(form.get(FieldKeys.KEY_HORARIO));
			order.setPaymentType(form.get(FieldKeys.KEY_PAYMENT_TYPE));
			order.setTotal((Double) form.getField(FieldKeys.KEY_TOTAL).retrieveResult());

			// Mandarlo
			OrderMiddleware.placeOrder(order, new SaveOrderCallback() {
				@Override
				public void success() {
					// Reinicia el pedido
					Session.use().setCurrentOrder(null);
					cb.success();
				}

				@Override
				public void error(RemoteError e) {
					cb.error(e);
				}
			});
		} else {
			cb.error(new RemoteError(RemoteError.INVALID_FORM, "La informacion del pedido no es valida"));
		}
	}

	/**
	 * Cancela una orden
	 * @param form Form que contiene que orden se va a cancelar
	 * @param cb El callback al que avisarle en case de exito o error
	 */
	public void cancelOrder(Form form, RUDCallback cb) { //(Parse)
		/** TODO: usar el form para avisar de que pedido se trata*/

		if (Session.use().getCurrentOrder() == null) {
			cb.error(new RemoteError(RemoteError.NO_SUCH_ORDER, "No hay nada en el carrito"));
			return;
		}

		if (form.hasBeenValidated() || form.isValid()) {

			OrderMiddleware.cancelOrder(form.get(FieldKeys.KEY_ID), cb);
		}

	}

	/**
	 * Agrega un item al carrito
	 * @param menuItem
	 * @param comment
	 * @param qty
	 */
	public static void addItem(MenuItem menuItem, String comment, int qty) { //(LOCAL) Aca mete OrderProduct en el mapa de Order
		/** TODO: usar Form aca */
		OrderProduct orderProduct = new OrderProduct(menuItem, comment, qty);
		if (Session.use().getCurrentOrder() == null)
			Session.use().setCurrentOrder(new Order());
		Session.use().getCurrentOrder().addItem(orderProduct);
	}


	/**
	 * Saca un item del carrito
	 * @param menuItem el item a sacar
	 * @param cb codigo a ejecutar cuando se haya agregado
	 */
	public static void removeItem(MenuItem menuItem, RUDCallback cb) { //(LOCAL)
		/** TODO: usar Form aca */
		if (Session.use().getCurrentUser() != null) {
			if (Session.use().getCurrentOrder() != null) {
				Session.use().getCurrentOrder().removeItem(new OrderProduct(menuItem));
				cb.success();
			} else {
				cb.error(new RemoteError(RemoteError.NO_SUCH_ORDER, "El carrito esta vacio"));
			}
		} else {
			cb.error(new RemoteError(RemoteError.NOT_LOGGED_IN, "No ha iniciado sesion"));
		}
	}

	/**
	 * Actualiza un item en el carrito
	 * @param form Formulario correspondiente a actualizar un item del carrito
	 */
	public static void updateItem(Form form) {
		/** TODO: puede ser que tenga sentido hacer un removeItem y un addItem */



	}
}
