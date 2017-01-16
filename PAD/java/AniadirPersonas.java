package fdi.ucm.cuentacuentas;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class AniadirPersonas extends AppCompatActivity {
    private EditText buscar;
    public static final String EXTRA_ADDPER="fdi.ucm.cuentacuentas.EXTRA_ADDPER";
    public static final String EXTRA_GRUPOAN="fdi.ucm.cuentacuentas.EXTRA_GRUPOAN";
    public static final String EXTRA_ADMINA="fdi.ucm.cuentacuentas.EXTRA_ADMINA";
    private grupo grup;
    private boolean admin;
    private ListView lista ;
    private  AdaptadorUsuarios adapter;
    private grupousuario usuinsert;
    private ArrayList<usuario> usuarios;
    private TextView seleccionados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_personas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        grup=(grupo)getIntent().getSerializableExtra(EXTRA_GRUPOAN);
        admin=getIntent().getBooleanExtra(EXTRA_ADMINA,false);
        usuarios= new ArrayList<>();
        seleccionados=(TextView)findViewById(R.id.nseleccion);


         buscar=(EditText)findViewById(R.id.buscarusu);


            buscar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    new SelectUsuarios().execute(s.toString());
                }
            });


         lista=(ListView)findViewById(R.id.listusuarios2);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {

                if(admin){
                    usuario usuario=(usuario)lista.getAdapter().getItem(pos);
                    grupousuario usu= new grupousuario();
                    usu.setIdGrupo(grup.getId());
                    usu.setDebe(0);
                    usu.setPagado(0);
                    usu.setAdmin(false);
                    usu.setUsu(usuario);
                    usuinsert=usu;

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(AniadirPersonas.this);

                    builder.setMessage("¿Quiere añadir a este usuario?")
                            .setTitle("añadir usuario")
                            .setNegativeButton("NO", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    usuinsert=null;
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    insergrupusu();
                                    dialog.cancel();
                                }
                            }).show();

                }else{
                    usuario usuar=(usuario)lista.getAdapter().getItem(pos);
                    boolean esta=false;
                    int i=0;
                    for(i=0;i<usuarios.size();i++){

                            if(usuarios.get(i).getId()==usuar.getId()){
                                esta=true;
                                break;
                            }

                    }
                        if(!esta){
                            usuarios.add(usuar);
                        }else{

                            usuarios.remove(i);
                        }
                    seleccionados.setText(usuarios.size()+"");

                }


            }
        });
    }

    private void insergrupusu(){
        boolean esta=false;
        for(grupousuario usu : grup.getMiembros()){

                if(usu.getusu().getId()==usuinsert.getusu().getId()){
                    esta=true;
                }
        }
        if(!esta){
           new insertargrupousuario().execute(usuinsert);
        }else{

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(AniadirPersonas.this);

            builder.setMessage("Usuario existente en el grupo")
                    .setTitle("añadir usuario")

                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    }).show();

        }
    }


    private void aniadirusus(){


        ArrayList<grupousuario> grupousuarios = new ArrayList<>();
        for (int i = 0; i < usuarios.size(); i++) {


                grupousuario usu=new grupousuario();
                usu.setDebe(0);
                usu.setPagado(0);
                usu.setUsu(usuarios.get(i));
                grup.addMiembro(usu);
            }

        Intent intent = new Intent();
        intent.putExtra("grup",grup);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      if(!admin)  getMenuInflater().inflate(R.menu.menu_aniadir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_aniadir) {
            aniadirusus();
            return true;

        }
        if(id==android.R.id.home){
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    class AdaptadorUsuarios extends ArrayAdapter<usuario> {

        Activity context;
        ArrayList<usuario> lista;
        public AdaptadorUsuarios(Activity activity, ArrayList<usuario> datos) {
            super(activity, R.layout.item_usuario,datos);
            this.context=activity;
            lista=datos;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.item_usuario, null);



            TextView nickname = (TextView) item.findViewById(R.id.texitemnick);
            nickname.setText("Nick: "+lista.get(position).getNickname());

            TextView lblnombre = (TextView) item.findViewById(R.id.textitemnombre);
            lblnombre.setText("Nombre: "+lista.get(position).getNombre()+" "+lista.get(position).getApellidos());


            ImageView imagen= (ImageView)item.findViewById(R.id.imageitemusu);
            if(lista.get(position).getImagen()!=null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(lista.get(position).getImagen(),
                        0, lista.get(position).getImagen().length);
                imagen.setImageBitmap(bitmap);
            }


            return (item);
        }
    }





    class SelectUsuarios extends AsyncTask<String,Void,ArrayList<usuario>> {

        @Override
        protected ArrayList<usuario> doInBackground(String... strings) {


            return DAOCuentaCuentas.getInstance().buscarUsuarios(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<usuario> usuarios) {

            if(usuarios!=null){
                adapter=new AdaptadorUsuarios(AniadirPersonas.this,usuarios);
                lista.setAdapter(new AdaptadorUsuarios(AniadirPersonas.this, usuarios));
            }
            else{
                if(adapter!=null){
                    adapter.clear();
                    lista.setAdapter(adapter);
                }

            }


        }
    }




    class insertargrupousuario extends AsyncTask<grupousuario,Void,Boolean> {

        @Override
        protected Boolean doInBackground(grupousuario... u) {

            return DAOCuentaCuentas.getInstance().insertarUsuario(u[0]);
        }

        protected void onPostExecute(Boolean ok) {

            if(ok) {

                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        }
    }
}
