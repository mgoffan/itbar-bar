package com.itbar.backend.middleware;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

/**
 * Habla con {@link Parse} para definir cuestiones globales como la configuracion de inicio de la
 * app
 *
 * Created by martin on 5/23/15.
 */
public class GlobalMiddleware {

	/**
	 * Inicia la configuracion asociada a {@link Parse}, el proveedor de datos.
	 * @param ctx El contexto de la app
	 */
	public static void init(Context ctx) {

		// Initialize Crash Reporting.
		ParseCrashReporting.enable(ctx);

		// Enable Local Datastore.
		Parse.enableLocalDatastore(ctx);

		// Add your initialization code here
		Parse.initialize(ctx);


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		defaultACL.setPublicWriteAccess(true);
		defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);

	}
}
