package fdi.ucm.cuentacuentas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {

    private EditText txtusuario;
    private EditText txtcontra;
    private boolean primero=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref= getSharedPreferences("Login",Context.MODE_PRIVATE);
        boolean logeado=pref.getBoolean("logeado",false);
        if(logeado) {
            String uspref = pref.getString("usuario", "");
            String contpref = pref.getString("password", "");
            new SelectInicio().execute( uspref, contpref);
        }else {

        inicializarVentana();
        }
    }

    private void inicializarVentana(){
        setContentView(R.layout.activity_login);

        primero=false;

        txtusuario = (EditText) findViewById(R.id.textusuini);

        txtcontra = (EditText) findViewById(R.id.textcontini);

        Button inicio = (Button) findViewById(R.id.btninicioses);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usu = txtusuario.getText().toString();
                String cont = txtcontra.getText().toString();

                new SelectInicio().execute(usu, cont);
            }
        });

        Button registrarse = (Button) findViewById(R.id.button);
        registrarse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ConfigUsuario.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref= getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("usuario","");
        editor.putString("password","");
        editor.putBoolean("logeado",false);
        editor.commit();
    }



    private void IniciarAplicacion(usuario usu){

        Intent intent= new Intent(ActivityLogin.this,MainActivity.class);
        SharedPreferences pref= getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString("usuario",usu.getNickname());
        editor.putString("password",usu.getPassword());
        editor.putBoolean("logeado",true);
        editor.commit();
        intent.putExtra(MainActivity.EXTRA_USU,usu);
        startActivity(intent);
        finish();



    }



    public class SelectInicio extends AsyncTask<String,Void,usuario> {

        @Override
        protected usuario doInBackground(String... strings) {


            return DAOCuentaCuentas.getInstance().login(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(usuario usu1) {

                    if (usu1==null) {
                        if(primero){
                            inicializarVentana();
                        }else {
                            Toast toast =
                                    Toast.makeText(getApplicationContext(),
                                            "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    } else {
                        usuario usu;
                        usu= usuario.getIntsance();
                        usu.setId(usu1.getId());
                        usu.setNickname(usu1.getNickname());
                        usu.setNombre(usu1.getNombre());
                        usu.setApellidos(usu1.getApellidos());
                        usu.setTelefono(usu1.getTelefono());
                        usu.setPassword(usu1.getPassword());

                            usu.setImagen(usu1.getImagen());

                        IniciarAplicacion(usu);
                    }

        }
    }
}
