package com.itbar.backend.util;

import com.itbar.backend.util.fields.CuitField;
import com.itbar.backend.util.fields.EmailField;
import com.itbar.backend.util.fields.LegajoField;
import com.itbar.backend.util.fields.NameField;
import com.itbar.backend.util.fields.NumberField;
import com.itbar.backend.util.fields.PasswordField;
import com.itbar.backend.util.fields.PhoneField;
import com.itbar.backend.util.fields.TextField;
import com.itbar.backend.util.fields.TimeField;
import com.itbar.backend.util.fields.Validator;

/**
 * El FormBuilder se utiliza para crear formularios predeterminados para las operaciones a lo largo
 * y ancho de la app. Garantiza que los formularios sean exactamente como se necesitan.
 * Se construyen en los activities y se leen en los Services
 * @see com.itbar.frontend.activities.EnterActivity
 * @see com.itbar.backend.services.UserService
 * Created by martin on 21/05/15.
 */
public class FormBuilder {

	/**
	 * Construye el formulario para hacer el login de un usuario
	 *
	 * @return el formulario correspondiente al login de un usario
	 */
	public static Form buildLoginForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_LEGAJO, new LegajoField(true));
		form.addField(FieldKeys.KEY_PASSWORD, new PasswordField(true));
		return form;
	}

	/**
	 * Construye un formulario para crear un usuario
	 *
	 * @return Form el formulario que corresponde a la creacion de un usuario
	 */
	public static Form buildUserForm() {
		final Form form = new Form();
		form.addField(FieldKeys.KEY_LEGAJO, new LegajoField(true));
		form.addField(FieldKeys.KEY_EMAIL, new EmailField(true));
		form.addField(FieldKeys.KEY_PHONE, new PhoneField());
		form.addField(FieldKeys.KEY_NAME, new NameField(true));
		form.addField(FieldKeys.KEY_SURNAME, new NameField(true));
		form.addField(FieldKeys.KEY_ID, new TextField());
		return form;
	}

	/**
	 * Construye el formulario requerido para registrar a un usuario
	 *
	 * @return Form el formulario que se necesita para registrar a un usuario
	 */
	public static Form buildSignupForm() {
		final Form form = new Form();
		form.addField(FieldKeys.KEY_LEGAJO, new LegajoField(true));
		form.addField(FieldKeys.KEY_EMAIL, new EmailField(true));
		form.addField(FieldKeys.KEY_PHONE, new PhoneField());
		form.addField(FieldKeys.KEY_NAME, new NameField());
		form.addField(FieldKeys.KEY_SURNAME, new NameField());
		form.addField(FieldKeys.KEY_PASSWORD, new PasswordField(true));
		form.addField(FieldKeys.KEY_REPEAT_PASSWORD, new PasswordField(true));
		form.setInterFieldValidation(new InterFieldValidation() {
			@Override
			public Boolean validateForm() {
				return form.get(FieldKeys.KEY_PASSWORD).equalsIgnoreCase(form.get(FieldKeys.KEY_REPEAT_PASSWORD));
			}
		});
		return form;
	}

	/**
	 *
	 * Construye un formulario para concretar un pedido en el servidor.
	 *
	 * Exige un horario, medio de pago, estado, y un total
	 *
	 * @return Form el formulario para colocar un pedido en el servidor
	 * @see Formats
	 * @see com.itbar.backend.services.OrderService
	 */
	public static Form buildOrderForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_HORARIO, new TimeField(true));
		form.addField(FieldKeys.KEY_PAYMENT_TYPE, new TextField(true, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.PAYMENT_TYPE.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Tipo de pago incorrecto";
			}
		}));
		form.addField(FieldKeys.KEY_COMMENT, new TextField());
		form.addField(FieldKeys.KEY_STATUS, new TextField(true, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.STATUS.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Estado incorrecto";
			}
		}));
		form.addField(FieldKeys.KEY_TOTAL, (new NumberField(true)).setHasFloatingPoint(true));
		return form;
	}

	/**
	 * Construye un formulario para cancelar un pedido por identificador
	 *
	 * @return Form el formulario para cancelar un pedido
	 * @see com.itbar.backend.services.OrderService
	 */

	public static Form buildCancelOrderForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_ID, new TextField());
		return form;
	}

	/**
	 * Construye un formulario para realizar busquedas de pedidos por estado
	 * No exige que el estado sea un campo requerido, en tal caso traera todos los pedidos.
	 *
	 * @return Form el formulario para buscar pedidos por estado en el servidor
	 * @see Formats
	 */
	public static Form buildGetOrdersForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_BUYER, new TextField());
		form.addField(FieldKeys.KEY_STATUS, new TextField(false, new Validator() {
			@Override
			public Boolean isValid(String val) {
				return Formats.STATUS.matcher(val).matches();
			}

			@Override
			public String errorDescription() {
				return "Estado incorrecto";
			}
		}));
		return form;
	}

	public static Form buildAddMenuItemForm() {
		Form form = new Form();
		form.addField(FieldKeys.KEY_ID, new TextField(true));
		form.addField(FieldKeys.KEY_COMMENT, new TextField());
		form.addField(FieldKeys.KEY_QUANTITY, (new NumberField(true)).setHasFloatingPoint(false));
		return form;
	}

}
