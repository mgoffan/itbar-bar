package com.itbar.backend.middleware;

import com.itbar.backend.middleware.translators.OrderProductTranslator;
import com.itbar.backend.middleware.translators.OrderTranslator;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.callbacks.FindMultipleCallback;
import com.itbar.backend.services.callbacks.RUDCallback;
import com.itbar.backend.services.callbacks.SaveOrderCallback;
import com.itbar.backend.services.views.Order;
import com.itbar.backend.services.views.OrderProduct;
import com.itbar.backend.services.views.User;
import com.itbar.backend.util.FieldKeys;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Es el nexo de conexion entre el {@link com.itbar.backend.services.OrderService} y {@link Parse}
 *
 * <p>Discucion</p>
 *
 * <p>
 * Ver {@link UserMiddleware}</p>
 *
 * Created by martin on 5/28/15.
 *
 * @see UserMiddleware
 */
public class OrderMiddleware {

	public static void placeOrder(final Order order, final SaveOrderCallback cb) {

		final ParseObject parseOrder = OrderTranslator.fromOrder(order);

		parseOrder.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					order.setObjectId(parseOrder.getObjectId());

					List<ParseObject> items = new ArrayList<>();

					for (OrderProduct oP : order.getItems()) {
						items.add(OrderProductTranslator.fromOrderProduct(oP));
					}

					ParseObject.saveAllInBackground(items, new SaveCallback() {
						@Override
						public void done(com.parse.ParseException e) {
							if (e != null) {
								cb.error(new RemoteError(e));
							} else {
								cb.success();
							}
						}
					});
				}
			}
		});

	}

	public static void cancelOrder(final String orderId, final RUDCallback cb) {

		ParseObject parseOrder = ParseObject.createWithoutData("Order", orderId);

		parseOrder.put(FieldKeys.KEY_STATUS,"Cancelada");

//		final ParseObject parseOrder = OrderTranslator.fromOrder(order);

		parseOrder.saveInBackground(new SaveCallback() {
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

	public static void fetchWithStatus(String status, String buyer, final FindMultipleCallback<Order> cb) {

		ParseQuery<ParseObject> query = new ParseQuery<>("Order");

		query.include("buyer");

		if (status != null)
			query.whereEqualTo("status", status);
		if (buyer != null)
			query.whereEqualTo("buyer", ParseObject.createWithoutData("_User", buyer));

		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {

					List<Order> orders = new ArrayList<>();

					for (ParseObject obj : list) {

						orders.add(OrderTranslator.toOrder(obj));

					}

					cb.success(orders);


				}
			}
		});

	}
}
