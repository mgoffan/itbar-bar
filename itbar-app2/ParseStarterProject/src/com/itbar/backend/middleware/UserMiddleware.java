package com.itbar.backend.middleware;

import android.util.Log;

import com.itbar.backend.middleware.translators.UserTranslator;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.callbacks.UserLogInCallback;
import com.itbar.backend.services.callbacks.UserLogOutCallback;
import com.itbar.backend.services.callbacks.UserSignUpCallback;
import com.itbar.backend.services.views.User;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * <p>
 * Middleware de conexion con el proveedor de base de datos, en este caso Parse.
 * </p>
 * Discucion:
 * <p>
 * Un Middleware es el encargado de realizar operaciones en el proveedor de servicios. Es la capa
 * entre el proveedor de base de datos  y los servivios de la aplicacion. Cuenta con
 * las siguientes premisas:
 * </p>
 *   <ul>
 *  <li>La informacion recibida ya fue validada cuando proviene del lado del front</li>
 *  <li></>Tiene que funcionar como una capa: es decir para un lado tiene que hablar Parse y para el otro
 *    lenguaje app, de manera tal que si el dia de manana el proveedor de base de datos dejara de
 *    ser Parse entonces simplemente lo que se cambia es el codigo de conexion, y la capa de
 *    servicios permanece intacta</li>
 *  <li>
 *    Lo anterior involucra que toda cuestion involucrada a la existencia de @{link Parse} se queda
 *    en esta capa, como todos los callbacks de Parse, modelos de {@link com.parse.Parse} ({@link ParseUser},
 *    {@link com.parse.ParseObject}), los traductores y la {@link ParseException} de {@link ParseException}</li>
 *    </ul>
 *
 * Created by martin on 5/22/15.
 *
 * @see com.itbar.backend.util.Form
 * @see com.itbar.backend.util.FormBuilder
 * @see com.parse.Parse
 * @see ParseUser
 * @see ParseException
 * @see com.parse.ParseObject
 *
 *
 */
public class UserMiddleware {

	/**
	 * Inicia sesion el proveedor de base de datos
	 * @param legajo El legajo del usuario
	 * @param pass Contraseña del usuario
	 * @param cb Rutina que se ejecutara una vez terminado el pedido
	 */
	public static void loginUser(String legajo, String pass, final UserLogInCallback cb) {

		ParseUser.logInInBackground(legajo, pass, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (e != null) {
					cb.error(new RemoteError(e));
				} else {
					cb.success(UserTranslator.toUser(user));
				}
			}
		});
	}

	/**
	 * Crea un usuario en el proveedor de BD
	 *
	 * @param user Usuario a registrar
	 * @param pass Contraseña del usuario
	 * @param cb Rutina a ejecutarse cuando finalice el pedido
	 */
	public static void signupUser(User user, String pass, final UserSignUpCallback cb) {

		ParseUser parseUser = UserTranslator.fromUser(user, pass);

		parseUser.signUpInBackground(new SignUpCallback() {
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
	 * Ejecuta el cierre de sesion en el proveedor de BD
	 * @param cb La rutina a ejecutarse cuando el cierre de sesion termine
	 */
	public static void logoutUser(final UserLogOutCallback cb) {

		ParseUser.logOutInBackground(new LogOutCallback() {
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

}
