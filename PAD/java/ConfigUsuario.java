package fdi.ucm.cuentacuentas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static fdi.ucm.cuentacuentas.ActivityLogin.*;

/**
 * Created by Pikup on 16/05/2016.
 */
public class ConfigUsuario extends AppCompatActivity {

    // Atributos
    private ImageButton avatar = null;
    private TextView nickname = null;
    private TextView password = null;
    private TextView nombre = null;
    private TextView apellidos = null;
    private TextView telefono = null;

    // Acciones
    static final int ACTION_AVATAR_PHOTO2 = 1;
    static final int ACTION_AVATAR_GALLERY2 = 2;

    private SharedPreferences preferences = null;

    // Claves
    static final String AVATAR = "avatar";
    static final String NICKNAME = "nickname";
    static final String PASSWORD = "password";
    static final String NAME = "name";
    static final String SURNAMES = "surnames";
    static final String PHONE = "phone";

     private byte[] imagen=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_usuario);
        setTitle("Nuevo usuario");
        // Barra de arriba
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Código para el ImageButton
        avatar = (ImageButton) findViewById(R.id.butUsuarioImage);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });

        nickname = (TextView) findViewById(R.id.textNickname);
        password = (TextView) findViewById(R.id.textPassword);
        nombre = (TextView) findViewById(R.id.textNombre);
        apellidos = (TextView) findViewById(R.id.textApellidos);
        telefono = (TextView) findViewById(R.id.textTelefono);

        // Obtener las preferencias
        preferences = getPreferences(0);

        // Establecer el avatar seleccionado anteriorente
        //setAvatar();
        // Restablecer el resto de valores
        EditText ed = (EditText) findViewById(R.id.textNickname);
        ed.setText(preferences.getString(NICKNAME, ""));
        ed = (EditText) findViewById(R.id.textPassword);
        ed.setText(preferences.getString(PASSWORD, ""));
        ed = (EditText) findViewById(R.id.textNombre);
        ed.setText(preferences.getString(NAME, ""));
        ed = (EditText) findViewById(R.id.textApellidos);
        ed.setText(preferences.getString(SURNAMES, ""));
        ed = (EditText) findViewById(R.id.textTelefono);
        ed.setText(preferences.getString(PHONE, ""));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Si pulsamos "Finalizar"
        if (id == R.id.action_finalizar) {
            guardarPreferencias();

            BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            imagen=bos.toByteArray();
            // Insertamos el usuario en la base de datos
            new insertarUsuario().execute();
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
        PhotoSelectorDialog avatarDialog = new PhotoSelectorDialog(2);
        avatarDialog.show(fm, "photo");
    }

    public void avatarPhoto() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(pictureIntent, ACTION_AVATAR_PHOTO2);
    }

    public void avatarGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, ACTION_AVATAR_GALLERY2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            // No hacer nada
        } else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ACTION_AVATAR_PHOTO2:
                    Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
                    saveAvatar(cameraPic);
                    break;
                case ACTION_AVATAR_GALLERY2:
                    Uri photoUri = data.getData();
                    try {
                        Bitmap galleryPic = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                        saveAvatar(galleryPic);
                    } catch (Exception e) {
                        new AlertDialog.Builder(this).setMessage("ERROR: " + e.getLocalizedMessage()).setPositiveButton(android.R.string.ok, null).show();
                    }
                    break;
            }
        }
    }

    private void saveAvatar(Bitmap bitmap) {
        // Mostraremos el avatar en el botón
        ImageButton avatar = (ImageButton) findViewById(R.id.butUsuarioImage);
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

    public void avatarDefault() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(AVATAR);
        editor.commit();
        setAvatar();
    }

    protected void guardarPreferencias() {
        super.onPause();
        // Guardar preferencias
        SharedPreferences.Editor edit = preferences.edit();
        EditText ed = (EditText) findViewById(R.id.textNickname);
        edit.putString(NICKNAME, ed.getText().toString());
        ed = (EditText) findViewById(R.id.textPassword);
        edit.putString(PASSWORD, ed.getText().toString());
        ed = (EditText) findViewById(R.id.textNombre);
        edit.putString(NAME, ed.getText().toString());
        ed = (EditText) findViewById(R.id.textApellidos);
        edit.putString(SURNAMES, ed.getText().toString());
        ed = (EditText) findViewById(R.id.textTelefono);
        edit.putString(PHONE, ed.getText().toString());
        edit.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Guardar preferencias
        guardarPreferencias();
    }

    class insertarUsuario extends AsyncTask<String, Void, Boolean> {

        Boolean ok = false;
        usuario usu = new usuario();

        @Override
        protected Boolean doInBackground(String... strings) {

            usu=DAOCuentaCuentas.getInstance().nuevoUsuario(preferences.getString(NICKNAME, ""),preferences.getString(PASSWORD, "")
            ,preferences.getString(PHONE, ""),preferences.getString(NAME, ""),preferences.getString(SURNAMES, ""),imagen);
            if(usu!=null){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean ok) {
            if (ok) {
                // Vamos a la ventana principal
                Intent intent = new Intent(ConfigUsuario.this, MainActivity.class);
                usuario usu2= usuario.getIntsance();
                usu2.setNickname(usu.getNickname());
                usu2.setPassword(usu.getPassword());
                usu2.setTelefono(usu.getTelefono());
                usu2.setApellidos(usu.getApellidos());
                usu2.setNombre(usu.getNombre());
                usu2.setId(usu.getId());
                usu2.setImagen(usu.getImagen());
                startActivity(intent);
                finish();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "¡Ya hay un usuario registrado con este nickname, prueba con otro!", Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(ConfigUsuario.this, ConfigUsuario.class);
                startActivity(intent);
            }
        }
    }

}
