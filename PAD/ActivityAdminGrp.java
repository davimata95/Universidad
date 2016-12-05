package fdi.ucm.cuentacuentas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.BufferUnderflowException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ActivityAdminGrp extends AppCompatActivity {
    private grupo grup;
    private float deber;
    private float pagar;
    private float cant;
    private ListView Lstmiembros;
    private ArrayList<grupousuario> miembros;
    public static final String EXTRA_GRUPOADMIN="fdi.ucm.cuentacuentas.EXTRA_GRUPOANDMIN";
    private grupousuario usu;
    private TextView debertotal;
    private TextView pagadototal;
    private DAOCuentaCuentas dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admingrupo);
        dao=DAOCuentaCuentas.getInstance();
        deber=0;
        pagar=0;
        setTitle("Administrar Grupo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        grup=(grupo)getIntent().getSerializableExtra(EXTRA_GRUPOADMIN);
        TextView nombre=(TextView)findViewById(R.id.Lblmiembronombreadm);
        nombre.setText(grup.getNombre());

       ImageView imagen =(ImageView)findViewById((R.id.imggrupoadm));
        if(grup.getImagen()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(grup.getImagen(), 0, grup.getImagen().length);
            imagen.setImageBitmap(bitmap);

        }

        debertotal =(TextView)findViewById(R.id.totaldeber2adm);
        debertotal.setText(grup.getDeben()+"");

        pagadototal=(TextView)findViewById(R.id.totalpagado2adm);
        pagadototal.setText(grup.getPagado()+"");

        Button btnpago =(Button)findViewById(R.id.butonpago);


        Button btndeber=(Button)findViewById(R.id.butondeudageneral);
        btndeber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bun=new Bundle();
                bun.putBoolean("total",true);
                FragmentManager fm=getSupportFragmentManager();
                CantDebeDialog dialog= new CantDebeDialog();
                dialog.setArguments(bun);
                dialog.show(fm,"pago2");
            }
        });

         Lstmiembros=(ListView)findViewById(R.id.listamiembrogrupoadm);
            new SelectGrupoUsuario().execute();

        Lstmiembros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                usu= (grupousuario) Lstmiembros.getAdapter().getItem(pos);
                    miembroAccion();

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grupo, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reiniciar) {
         new  reiniciarCuentas().execute(grup.getId()+"");

        }
        if(id==R.id.action_add){

            Intent intent=new Intent(ActivityAdminGrp.this,AniadirPersonas.class);

            intent.putExtra("grupo",grup);
            intent.putExtra("admin",true);
            startActivityForResult(intent,5);
            return true;
        }

        if(id==R.id.action_borrargrup){
            new borrarGrupo().execute(grup.getId()+"");
            return true;
        }
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            // No hacer nada
        } else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case (5) :
                    new SelectGrupoUsuario().execute();

            }
        }
    }

    public void verPerfil(){

        Intent intent= new Intent(this,activity_perfil.class);
        intent.putExtra(activity_perfil.EXTRA_VERPERFIL, usu.getusu());
        startActivity(intent);
    }

    public void borrarmiembro(){

        deber=grup.getDeben()-usu.getDebe();
        new borrarmiembro().execute(usu.getIdGrupo()+"",usu.getusu().getId()+"","deben",usu.getDebe()+"");

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    private void miembroAccion(){

        FragmentManager fm=getSupportFragmentManager();
        AccionMiemDialog miemdialog= new AccionMiemDialog();
        miemdialog.show(fm,"acciones");
    }

    public void acciondeuda(){

        Bundle bu= new Bundle();
        bu.putBoolean("total",false);
        FragmentManager fm=getSupportFragmentManager();
        CantDebeDialog dialog= new CantDebeDialog();
        dialog.setArguments(bu);
        dialog.show(fm,"deuda");
    }

    public void accionpago(){

        Bundle bu = new Bundle();
        bu.putBoolean("total",false);
        FragmentManager fm=getSupportFragmentManager();
        CantPagoDialog dialog= new CantPagoDialog();
        dialog.setArguments(bu);
        dialog.show(fm,"pago");
    }
    public void anadirdebototal(float cant){
        float cantotal=cant*miembros.size();
        deber=cantotal;

        new  actualizarGrupoTotal().execute("debe",cant+"", grup.getId()+"",cantotal+"","deben");
    }


    class SelectGrupoUsuario extends AsyncTask<String,Void,ArrayList<grupousuario>> {

        @Override
        protected ArrayList<grupousuario> doInBackground(String... strings) {

            return dao.SelectGrupoUsuario(grup);
        }

        @Override
        protected void onPostExecute(ArrayList<grupousuario> mm) {

            if(mm!= null){
                miembros=mm;
                grup.setMiembros(miembros);

                Lstmiembros.setAdapter(new AdaptadorMiembros(ActivityAdminGrp.this, grup.getMiembros()));
            }
            else{
                grup.setMiembros(null);
            }

        }
    }
    public void anadirdeuda(float cantidad){

        deber=cantidad;
        new actualizarDebeUsu().execute("debe",""+deber, usu.getIdGrupo()+"",usu.getusu().getId()+"","deben");
     }

    public void anadirpago(float cantidad){
        pagar=cantidad;
        new actualizaPago().execute(""+pagar, usu.getIdGrupo()+"",usu.getusu().getId()+"");
    }
    class actualizarDebeUsu extends AsyncTask<String,Void,Boolean> {

            @Override
            protected Boolean doInBackground(String... strings) {


                return dao.actualizarDebeUsu(strings[0],strings[1],strings[2],strings[3],strings[4]);
            }

            protected void onPostExecute(Boolean ok) {
                if (ok) {
                    if (deber != 0) {
                        usu.setDebe(deber+usu.getDebe());
                        grup.setDeben(deber+grup.getDeben());
                        debertotal.setText(grup.getDeben()+"");

                    } else if (pagar != 0) {
                        usu.setPagado(pagar);
                        grup.setPagado(pagar);

                    }
                    Lstmiembros.setAdapter(new AdaptadorMiembros(ActivityAdminGrp.this, miembros));
                } else {
                             if (deber != 0) {
                                usu.setDebe(usu.getDebe() - deber);
                             } else if (pagar != 0) {

                               usu.setPagado(usu.getPagado() - pagar);
                             }
                          }
                deber = 0;
                pagar = 0;
                 }

        }
    class actualizarGrupoTotal extends AsyncTask<String, Void, Boolean> {

                @Override
                protected Boolean doInBackground(String... strings) {

                return dao.actualizarGrupoTotal(strings[0],strings[1],strings[2],strings[3],strings[4]);
                }

                protected void onPostExecute(Boolean ok) {
                        if (ok) {
                            new SelectGrupoUsuario().execute();
                            if (deber != 0) {

                                grup.setDeben(deber+grup.getDeben());
                                    debertotal.setText(grup.getDeben()+"");

                            } else if (pagar != 0) {

                                grup.setPagado(pagar+grup.getPagado());
                                pagadototal.setText(grup.getPagado()+"");
                            }
                            Lstmiembros.setAdapter(new AdaptadorMiembros(ActivityAdminGrp.this, miembros));
                        } else {

                        }
                        deber = 0;
                        pagar = 0;
                    }
            }
    class actualizaPago extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {


          return dao.actualizarPago(strings[0],strings[1],strings[2]);
        }

        protected void onPostExecute(Boolean ok) {
            if (ok) {

                usu.setDebe(usu.getDebe()-pagar);
                grup.setDeben(grup.getDeben()-pagar);
                debertotal.setText(grup.getDeben()+"");


                usu.setPagado(pagar+usu.getPagado());
                grup.setPagado(pagar+grup.getPagado());
                pagadototal.setText(grup.getPagado()+"");


                Lstmiembros.setAdapter(new AdaptadorMiembros(ActivityAdminGrp.this, miembros));
            } else {


            }
            deber = 0;
            pagar = 0;
        }

    }
    class reiniciarCuentas extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return dao.reiniciarCuentas(strings[0]);
        }

        protected void onPostExecute(Boolean ok) {
            if (ok) {
                new SelectGrupoUsuario().execute();
                grup.setDeben(0);
                grup.setPagado(0);
                debertotal.setText("0");
                pagadototal.setText("0");
            }

            deber=0;
            pagar=0;

        }


    }
    class borrarGrupo extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return dao.borraGrupo(strings[0]);
        }

        protected void onPostExecute(Boolean ok) {
            if (ok) {
             finish();
            }
        }


    }

    class borrarmiembro extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return dao.borrarMiembro(strings[0], strings[1],strings[2],strings[3]);
        }

        protected void onPostExecute(Boolean ok) {
            if (ok) {

                debertotal.setText(deber+"");
                deber=0;
                new SelectGrupoUsuario().execute();
            }

        }


    }





}
