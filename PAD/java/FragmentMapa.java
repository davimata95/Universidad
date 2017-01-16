package fdi.ucm.cuentacuentas;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Alvaro on 19/05/2016.
 */
public class FragmentMapa extends Fragment implements OnMapReadyCallback {

    GoogleMap maps;

    ArrayList<grupo> grupos;
    private usuario usu;

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        Bundle arg = getArguments();
        usu =usuario.getIntsance(); //(usuario) arg.getSerializable("usuario");

    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(this);
        FragmentTransaction frag1 = this.getChildFragmentManager().beginTransaction();
        frag1.add(R.id.frag_map, mapFragment);
        frag1.commit();


        return view;


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationman = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationman.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(loc!=null) {
            maps.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(loc.getLatitude(),
                            loc.getLongitude()), 15));
        }
            maps.setMyLocationEnabled(true);
        new SelectGrupos().execute();
    }

    private void mostrarmarcadores(ArrayList<grupo> grupos){
        if(grupos!=null) {
            for (grupo grup : grupos) {
                maps.addMarker(new MarkerOptions()
                        .position(new LatLng(grup.getLatitud(), grup.getLongitud()))
                        .title(grup.getNombre())
                );

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (maps!=null) {
            maps.clear();
            new SelectGrupos().execute();
        }
    }

    public class SelectGrupos extends AsyncTask<String,Void,ArrayList<grupo>> {


        @Override
        protected ArrayList<grupo> doInBackground(String... strings) {
            return DAOCuentaCuentas.getInstance().buscarGrupos(usu,false);
        }

        @Override
        protected void onPostExecute(ArrayList<grupo> grupos) {

            mostrarmarcadores(grupos);

        }
    }
}
