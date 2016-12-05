package fdi.ucm.cuentacuentas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Alvaro on 05/05/2016.
 */
public class FragmentListado extends Fragment {

   // private static final FragmentListado frag= new FragmentListado();
    private ListView lstListado;
    private LstgrupoListener listener;
    private usuario usu;
    private boolean admin;
    private DAOCuentaCuentas dao;
    public static final String EXTRA_ADMING="fdi.ucm.cuentacuentas.ADMING";






    public static FragmentListado getInstance(Bundle bund){
        FragmentListado frag= new FragmentListado();
        frag.setArguments(bund);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao=DAOCuentaCuentas.getInstance();
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_fragmento, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        new SelectGrupos().execute();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setLstGrupoListener(LstgrupoListener listener){
        this.listener=listener;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        Bundle arg= getArguments();
        usu=usuario.getIntsance();
        admin=arg.getBoolean(EXTRA_ADMING);

        lstListado = (ListView)getView().findViewById(R.id.lstgrupos);


        new SelectGrupos().execute();

        FloatingActionButton fab = (FloatingActionButton)getView().findViewById(R.id.btnaddgrupo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                          Intent intent=new Intent(getActivity(),ConfigGrupo.class);
                         // intent.putExtra("usuario",usu);

                          startActivity(intent);
            }
        });

        lstListado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
                if (listener!=null) {
                    listener.OnGrupoSeleccionado(
                            (grupo)lstListado.getAdapter().getItem(pos));
                }
                grupo grup=(grupo)lstListado.getAdapter().getItem(pos);
                if(!admin){
                Intent intent=new Intent(getActivity(),ActivityGrupo.class);
                intent = intent.putExtra(ActivityGrupo.EXTRA_GRUPO, grup);
                startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getActivity(),ActivityAdminGrp.class);
                    intent.putExtra(ActivityAdminGrp.EXTRA_GRUPOADMIN,grup);
                    startActivity(intent);
                }

            }
        });
    }



    public interface LstgrupoListener{
        public void OnGrupoSeleccionado(grupo grup);
    }


    class AdaptadorGrupos extends ArrayAdapter<grupo> {

        Activity context;
        grupo grupos[];
        public AdaptadorGrupos(Fragment context, grupo[] datos) {
            super(context.getActivity(), R.layout.item_grupo,datos);
            this.context=context.getActivity();
            grupos=datos;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.item_grupo, null);

            TextView lblTitulo = (TextView) item.findViewById(R.id.LbGrupo);
            lblTitulo.setText(grupos[position].getNombre());

            TextView lblDebe = (TextView) item.findViewById(R.id.Lblitemdeb);
            lblDebe.setText(grupos[position].getDeben()+"");

            TextView lblPagado=(TextView)item.findViewById(R.id.Lblitempagado);
            lblPagado.setText(grupos[position].getPagado()+"");


            ImageView imagen= (ImageView)item.findViewById(R.id.imagengrupo);
           if(grupos[position].getImagen()!=null) {
               Bitmap bitmap = BitmapFactory.decodeByteArray(grupos[position].getImagen(), 0, grupos[position].getImagen().length);
               imagen.setImageBitmap(bitmap);
           }

            return (item);
        }
    }


    public class SelectGrupos extends AsyncTask<String,Void,ArrayList<grupo>> {

        @Override
        protected ArrayList<grupo> doInBackground(String... strings) {


            return dao.buscarGrupos(usu,admin);



        }

        @Override
        protected void onPostExecute(ArrayList<grupo> grupos) {


            if(grupos!=null){
            grupo gruposarr[]=new grupo[grupos.size()];

            for(int i=0;i<grupos.size();i++){
                gruposarr[i]=grupos.get(i);
            }

            lstListado.setAdapter(new AdaptadorGrupos(FragmentListado.this,gruposarr));
        }else {
                grupo gruposarr[]=new grupo[0];
                lstListado.setAdapter(new AdaptadorGrupos(FragmentListado.this,gruposarr));
            }
            }

    }

}
