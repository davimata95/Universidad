package fdi.ucm.cuentacuentas;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Alvaro on 19/05/2016.
 */
public class ActivityAniadirLoc extends AppCompatActivity implements OnMapReadyCallback{
    GoogleMap maps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirloc);
        Toolbar tool=(Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment =new  SupportMapFragment();
        mapFragment.getMapAsync(this);
        FragmentTransaction frag1 = getSupportFragmentManager().beginTransaction();
        frag1.add(R.id.act_map,mapFragment);
        frag1.commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maps=googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        //fijar posicion
        LocationManager locationman = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationman.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(loc!=null) {
            maps.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(loc.getLatitude(),
                            loc.getLongitude()), 15));
        }
        maps.setMyLocationEnabled(true);

        //recoger punto

        maps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Projection proj = maps.getProjection();
                Point coord = proj.toScreenLocation(point);

                maps.addMarker(new MarkerOptions()
                        .position(new LatLng(point.latitude,point.longitude))
                        .title("Posicion Seleccionada")
                );

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ActivityAniadirLoc.this);
                final LatLng punto=point;
                builder.setMessage("¿Quiere añadir esta ubicacion?")
                        .setTitle("Añadir localizacion")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                aniadirloc(punto);
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    private void aniadirloc(LatLng point){
        double[] latlon= new double[2];
        latlon[0]=point.latitude;
        latlon[1]=point.longitude;

        Intent intent = new Intent();
        intent.putExtra("latlong",latlon);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
