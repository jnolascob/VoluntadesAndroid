package me.doapps.voluntades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import me.doapps.util.JSONParser;
import me.doapps.util.SessionManager;

/**
 * Created by jnolascob on 23/08/2014.
 */
public class Splash extends Activity {
    private static final long SPLASH_SCREEN_DELAY = 2000;
    private Button btn_facebook;
    private String[] APP_PERMISIONS = {"email"};

    private SessionManager manager;

    Facebook mFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getActionBar().hide();

        manager = new SessionManager(Splash.this);
        if(manager.getEstadoSession()){
            startActivity(new Intent(Splash.this, Main.class));
            finish();
        }

        /**generate key hash facebook**/
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("key hash", something+"");
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        /****/
        btn_facebook = (Button) findViewById(R.id.btn_facebook);
        mFacebook = new Facebook(getResources().getString(R.string.facebook_app_id));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Button) findViewById(R.id.btn_facebook)).setVisibility(View.VISIBLE);
                ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.GONE);
                ((TextView) findViewById(R.id.textView)).setVisibility(View.GONE);
                startActivity(new Intent(Splash.this, Main.class));
            }
        }, SPLASH_SCREEN_DELAY);

        /*event login*/
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(
                        mFacebook);
                mFacebook.authorize(Splash.this, APP_PERMISIONS,
                        new LoginDialogListener());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebook.authorizeCallback(requestCode, resultCode, data);
    }

    /**
     * Inner Class Facebook *
     */
    private class LoginDialogListener implements Facebook.DialogListener {

        public void onComplete(Bundle values) {
            Log.e("FACEBOOK LOGIN ", "LoginONComplete");
            new TaskLoginFacebook().execute();
        }

        public void onFacebookError(FacebookError e) {
            Log.e("FACEBOOK LOGIN", "Facebook ERROR ");
            Log.e("FACEBOOK LOGIN", e.toString());
            Toast.makeText(getApplicationContext(),
                    "LOGIN ERROR:\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        public void onError(DialogError e) {
            Log.e("FACEBOOK LOGIN", "ON ERROR ");
            Log.e("FACEBOOK LOGIN", e.toString());
            Toast.makeText(getApplicationContext(),
                    "LOGIN ERROR:\n" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        public void onCancel() {
            Log.e("FACEBOOK LOGIN", "CANCELADO ");
            Toast.makeText(getApplicationContext(), "LOGIN ERROR CANCELADO",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Async Task Facebook*
     */
    private class TaskLoginFacebook extends AsyncTask<Void, Void, Message> {

        private String USUARIO_FACEBOOK;
        private String USUARIO_NOMBRES;
        private String USUARIO_APELLIDOS;
        private String USUARIO_SEXO;
        private String USUARIO_EMAIL;
        private String USUARIO_IMAGEN;

        private String response = "";
        private ProgressDialog pDialog;
        JSONObject objectFacebook;

        @Override
        protected Message doInBackground(Void... args) {
            Message message = new Message();
            try {
                response = mFacebook.request("me");
                objectFacebook = new JSONObject(response);
                Log.e("info", objectFacebook.toString());

                USUARIO_FACEBOOK = "" + objectFacebook.getLong("id");
                USUARIO_NOMBRES = objectFacebook.getString("first_name");
                USUARIO_APELLIDOS = objectFacebook.getString("last_name");
                USUARIO_SEXO = objectFacebook.getString("gender");
                USUARIO_EMAIL = objectFacebook.getString("email");
                USUARIO_IMAGEN = "https://graph.facebook.com/"+ USUARIO_FACEBOOK + "/picture";

                manager.crearSessionOn(USUARIO_FACEBOOK, USUARIO_NOMBRES, USUARIO_APELLIDOS);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Splash.this);
            pDialog.setMessage("Ingresando ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Message result) {
            pDialog.dismiss();
            Intent intent = new Intent(Splash.this, Main.class);
            startActivity(intent);
            finish();
        }

    }
}
