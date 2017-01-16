package fdi.ucm.cuentacuentas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ActivityGrupo extends AppCompatActivity {
    private grupo grup;
    public static final String EXTRA_GRUPO="fdi.ucm.cuentacuentas.EXTRA_GRUPO";
    private ListView Lstmiembros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vergrupo);
        setTitle("Ver Grupo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        grup=(grupo)getIntent().getSerializableExtra(EXTRA_GRUPO);
        TextView nombre=(TextView)findViewById(R.id.Lblmiembronombre);
        nombre.setText(grup.getNombre());

      ImageView imagen =(ImageView)findViewById((R.id.imggrupo));

        if(grup.getImagen()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(grup.getImagen(), 0, grup.getImagen().length);
            imagen.setImageBitmap(bitmap);

        }

        TextView debertotal =(TextView)findViewById(R.id.totaldeber2);
        debertotal.setText(grup.getDeben()+"");

        TextView pagadototal=(TextView)findViewById(R.id.totalpagado2);
        pagadototal.setText(grup.getPagado()+"");

        Lstmiembros=(ListView)findViewById(R.id.listamiembrogrupo);

        new SelectGrupoUsuario().execute();







    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }




    class SelectGrupoUsuario extends AsyncTask<String,Void,ArrayList<grupousuario>> {

        @Override
        protected ArrayList<grupousuario> doInBackground(String... strings) {

            return DAOCuentaCuentas.getInstance().SelectGrupoUsuario(grup);
        }

        @Override
        protected void onPostExecute(ArrayList<grupousuario> mm) {

            if (mm != null) {

                grup.setMiembros(mm);

                Lstmiembros.setAdapter(new AdaptadorMiembros(ActivityGrupo.this, grup.getMiembros()));
            } else {
                grup.setMiembros(null);
            }

        }
    }

}
