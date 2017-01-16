package fdi.ucm.cuentacuentas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alvaro on 10/05/2016.
 */
class AdaptadorMiembros extends ArrayAdapter<grupousuario> {

    Activity context;
    ArrayList<grupousuario> lista;
    public AdaptadorMiembros(Activity activity, ArrayList<grupousuario> datos) {
        super(activity, R.layout.item_miembrogrupo,datos);
        this.context=activity;
        lista=datos;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.item_miembrogrupo, null);



        TextView lblNombre = (TextView) item.findViewById(R.id.Lblmiembronombre);
        lblNombre.setText(lista.get(position).getusu().getNombre());

        TextView lbldebeusu = (TextView) item.findViewById(R.id.Lblmiembrodebe2);
        lbldebeusu.setText(lista.get(position).getDebe()+"");

        TextView lblpagadousu = (TextView) item.findViewById(R.id.Lblmiembropagado2);
        lblpagadousu.setText(lista.get(position).getPagado()+"");

       ImageView imagen= (ImageView)item.findViewById(R.id.imagenmiembro);
        if(lista.get(position).getusu().getImagen()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(lista.get(position).getusu().getImagen(),
                    0, lista.get(position).getusu().getImagen().length);
            imagen.setImageBitmap(bitmap);
        }


        return (item);
    }
}
