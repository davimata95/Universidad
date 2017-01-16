package fdi.ucm.cuentacuentas;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConfigGrupo extends AppCompatActivity {

    private ImageButton avatar = null;
    private TextView textnombre = null;
    private ListView lispersonas;

    // Acciones
    static final int ACTION_AVATAR_PHOTO = 1;
    static final int ACTION_AVATAR_GALLERY = 2;

    private SharedPreferences preferences = null;
    // Claves
    static final String AVATAR = "avatar";
    static final String NAME = "name";


    private ArrayList<grupousuario> miembros;


    private grupo grup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_grupo);
        setTitle("Nuevo Grupo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            miembros = new ArrayList<>();
            usuario usu = usuario.getIntsance(); //(usuario) getIntent().getSerializableExtra("usuario");
            grupousuario usuadmin = new grupousuario();
            usuadmin.setAdmin(true);
            usuadmin.setDebe(0);
            usuadmin.setPagado(0);
            usuadmin.setUsu(usu);
            miembros.add(usuadmin);


        grup = new grupo();
        // Código para el imageButton
        avatar = (ImageButton) findViewById(R.id.butAvatarImage);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });

        textnombre = (TextView) findViewById(R.id.texnombregrupo);

        lispersonas = (ListView) findViewById(R.id.lstgrupogr);
        ArrayList<usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < miembros.size(); i++) {
            usuarios.add(miembros.get(i).getusu());
        }

        lispersonas.setAdapter(new AdaptadorUsuarios(this, usuarios));

        // Obtener las preferencias
        preferences = getPreferences(0);

        // Establecer el avatar seleccionado anteriormente
      //  setAvatar();
        // Restablecer el resto de valores
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sig, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_crear) {

            if(!textnombre.getText().toString().trim().equals("")) {
                grup.setNombre(textnombre.getText().toString());

                BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                grup.setImagen(bos.toByteArray());
                grup.setMiembros(miembros);
                if (grup.getLongitud() == null || grup.getLongitud() == null) {
                    localizacion();
                }
                new insertargrupo().execute();
                return true;
            }else{

                new AlertDialog.Builder(this).setMessage("Introduzca nombre de grupo").setPositiveButton(android.R.string.ok, null).show();

            }

        }

        if (id == R.id.action_añadirusu) {
            Intent intent = new Intent(ConfigGrupo.this, AniadirPersonas.class);

            intent.putExtra(AniadirPersonas.EXTRA_GRUPOAN, grup);
            intent.putExtra(AniadirPersonas.EXTRA_ADMINA, false);
            startActivityForResult(intent, 5);
            return true;
        }

        if(id==R.id.action_aniadirloc){

            Intent intent= new Intent(ConfigGrupo.this,ActivityAniadirLoc.class);
            startActivityForResult(intent,6);
            return true;
        }

        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAvatar() {
        // Intentar recuperar el avatar
        String uriString = preferences.getString(AVATAR, "android.resource://com.acme.flip/drawable/user");
        avatar.setImageURI(Uri.parse(uriString));
    }

    private void showPhotoDialog() {
        FragmentManager fm = getSupportFragmentManager();
        PhotoSelectorDialog avatarDialog = new PhotoSelectorDialog(1);
        avatarDialog.show(fm, "photo");
    }

    public void avatarPhoto() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(pictureIntent, ACTION_AVATAR_PHOTO);
    }

    public void avatarGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, ACTION_AVATAR_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            // No hacer nada
        } else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ACTION_AVATAR_PHOTO:
                    Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
                    saveAvatar(cameraPic);
                    break;
                case ACTION_AVATAR_GALLERY:
                    Uri photoUri = data.getData();
                    try {
                        Bitmap galleryPic = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                        saveAvatar(galleryPic);
                    } catch (Exception e) {
                        new AlertDialog.Builder(this).setMessage("ERROR: " + e.getLocalizedMessage()).setPositiveButton(android.R.string.ok, null).show();
                    }
                    break;
                case (5):

                    grupo grupaux = (grupo) data.getExtras().getSerializable("grup");
                    aniadirmiembros(grupaux);
                    break;
                case(6):
                    double latlong[]=data.getExtras().getDoubleArray("latlong");
                    grup.setLatitud(latlong[0]);
                    grup.setLongitud(latlong[1]);
                    break;
            }
        }
    }

    private void aniadirmiembros(grupo grupaux) {
        for (grupousuario usu : grupaux.getMiembros()) {
            boolean esta = false;
            for (int i = 0; i < miembros.size(); i++) {
                if (usu.getusu().getId() == miembros.get(i).getusu().getId()) {
                    esta = true;
                }
            }
            if (!esta) {
                miembros.add(usu);
            }

        }
        ArrayList<usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < miembros.size(); i++) {
            usuarios.add(miembros.get(i).getusu());
        }

        lispersonas.setAdapter(new AdaptadorUsuarios(this, usuarios));

    }

    private void saveAvatar(Bitmap bitmap) {
        // Mostraremos el avatar en el botón
        ImageButton avatar = (ImageButton) findViewById(R.id.butAvatarImage);
        avatar.setImageBitmap(bitmap);
        // Se guarda el avatar en fichero y en las preferencias el nombre del mismo
        String strAvatarFilename = "avatar.jpg";
        SharedPreferences.Editor editor = preferences.edit();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, openFileOutput(strAvatarFilename, MODE_PRIVATE));
            Uri imageUri = Uri.fromFile(new File(getFilesDir(), strAvatarFilename));
            editor.putString(AVATAR, imageUri.getPath());
            editor.commit();
        } catch (FileNotFoundException e) {
            new AlertDialog.Builder(this).setMessage("ERROR: " + e.getMessage()).setPositiveButton(android.R.string.ok, null).show();
        }
    }

   /* public void avatarDefault() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(AVATAR);
        editor.commit();
        setAvatar();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        // Guardar preferencias
        SharedPreferences.Editor edit = preferences.edit();
        EditText ed = (EditText) findViewById(R.id.texnombregrupo);
        edit.putString(NAME, ed.getText().toString());
        edit.commit();
    }


    class AdaptadorUsuarios extends ArrayAdapter<usuario> {

        Activity context;
        ArrayList<usuario> lista;

        public AdaptadorUsuarios(Activity activity, ArrayList<usuario> datos) {
            super(activity, R.layout.item_usuario, datos);
            this.context = activity;
            lista = datos;

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

    class insertargrupo extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {

            return DAOCuentaCuentas.getInstance().insertarGrupo(grup);
        }

        protected void onPostExecute(Boolean ok) {

            if (ok)
                finish();

        }
    }

    public void localizacion() {

        LocationManager locationman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationman == null) {
            Toast.makeText(this, "Error en localizacion", Toast.LENGTH_LONG).show();
        } else {

            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location loc = locationman.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(loc!=null) {
                    grup.setLatitud(loc.getLatitude());
                    grup.setLongitud(loc.getLongitude());
                }

            }catch(Exception e){
                Toast.makeText(this, "Error en localizacion", Toast.LENGTH_LONG).show();

            }
        }
    }





}

