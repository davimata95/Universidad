package fdi.ucm.cuentacuentas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_perfil extends AppCompatActivity {
    usuario usu;
    public static final String EXTRA_VERPERFIL="fdi.ucm.cuentacuentas.EXTRA_VERPERFIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verperfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usu=(usuario)getIntent().getSerializableExtra(EXTRA_VERPERFIL);

        TextView nick=(TextView)findViewById(R.id.lblperfilnick);
        nick.setText(usu.getNickname());

        TextView nombre=(TextView)findViewById(R.id.lblperfilnombre);
        nombre.setText(usu.getNombre());

        TextView apellidos=(TextView)findViewById(R.id.lblperfilapellidos);
        apellidos.setText(usu.getApellidos());

        TextView telefono=(TextView)findViewById((R.id.lblperfiltelefono));
        telefono.setText(usu.getTelefono());

        ImageView imagen= (ImageView)findViewById(R.id.imageperfilusu);
        if(usu.getImagen()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(usu.getImagen(),
                    0, usu.getImagen().length);
            imagen.setImageBitmap(bitmap);
        }


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
}
