/**
 * Esta activity es la que dibuja y le pasa al backend el usuario logeado.
 */

package com.itbar.frontend.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itbar.R;
import com.itbar.backend.services.RemoteError;
import com.itbar.backend.services.ServiceRepository;
import com.itbar.backend.services.callbacks.UserLogInCallback;
import com.itbar.backend.services.views.User;
import com.itbar.backend.util.FieldKeys;
import com.itbar.backend.util.Form;
import com.itbar.backend.util.FormBuilder;
import com.itbar.frontend.Models.ScreenMessages;
import com.parse.ParseAnalytics;
//import com.parse.starter.R;


public class EnterActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		drawUI();
	}


	public void drawUI() {

		/**
		** Hace que esta activity entre en modo fullscreen, sin la barra de hora pero con la barra de navegacion por default
		** de Android con transparencia
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window w = getWindow();
		w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		/**
		 * Indicamos que para esta actividad, vamos a utilizar el activity_ingreso.xml -ubicado en res.layout-
		 */
		setContentView(R.layout.activity_ingreso);



		final EditText usuario = (EditText) findViewById(R.id.usuariotxt);
		final EditText contra = (EditText) findViewById(R.id.contratxt);
		Button ingresar = (Button) findViewById(R.id.enterbtn);
		Button register = (Button) findViewById(R.id.registerbtn);
		RelativeLayout back = (RelativeLayout) findViewById(R.id.back);
		final ImageView imagen = (ImageView) findViewById(R.id.imagen);

		back.setBackgroundResource(R.drawable.fondo_ppal_itbar);

		usuario.setText("54775");
		contra.setText("kicr1995");

		ingresar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				/**
				 * Creamos un FORM (formulario) que contenga LEGAJO y PASSWORD
				 */
				Form loginForm = FormBuilder.buildLoginForm();

				loginForm.set(FieldKeys.KEY_LEGAJO, usuario.getText().toString());
				loginForm.set(FieldKeys.KEY_PASSWORD, contra.getText().toString());


				if (loginForm.isValid()) {
					ServiceRepository.getInstance().getUserService().loginUser(loginForm, new UserLogInCallback() {
						@Override
						public void success(User user) {
							if (user != null) {
								Intent intent = new Intent(EnterActivity.this, MainActivity.class);
								startActivity(intent);
								imagen.setBackgroundResource(R.drawable.ok);
								Toast.makeText(getApplicationContext(), ScreenMessages.WELCOME, Toast.LENGTH_SHORT).show();
								finish();
							} else {
								imagen.setBackgroundResource(R.drawable.error);
								Toast.makeText(getApplicationContext(), ScreenMessages.NO_USER_VALID, Toast.LENGTH_SHORT).show();
							}
						}

						@Override
						public void error(RemoteError e) {
							Toast.makeText(getApplicationContext(), ScreenMessages.ERROR, Toast.LENGTH_SHORT).show();
						}
					});
				} else {
					Log.v("APP123", loginForm.collectErrors().toString());
				}

			}
		});

		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
			}
		});
	}
}



