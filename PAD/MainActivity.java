package fdi.ucm.cuentacuentas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private usuario usu;
    public static final String EXTRA_USU="fdi.ucm.cuentacuentas.EXTRA_USU";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usu=usuario.getIntsance();//(usuario)getIntent().getSerializableExtra(EXTRA_USU);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter adapterpage = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
       // mViewPager.setAdapter(adapterpage);
            setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.action_cerrar_sesion){

            SharedPreferences pref= getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            editor.putString("usuario","");
            editor.putString("password","");
            editor.putBoolean("logeado",false);
            editor.commit();
            Intent intent= new Intent(MainActivity.this, ActivityLogin.class);
            startActivity(intent);
            finishActivity(0);
        }

        if(id==R.id.action_acercade){
            Intent intent= new Intent(MainActivity.this,ActivityAcercaDe.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        Bundle bund= new Bundle();
        bund.putBoolean(FragmentListado.EXTRA_ADMING,false);
       // bund.putSerializable("usuario", usu);

        adapter.addFragment(FragmentListado.getInstance(bund), "Grupos");
        bund=new Bundle();
        bund.putBoolean(FragmentListado.EXTRA_ADMING,true);
      //  bund.putSerializable("usuario",usu);
        adapter.addFragment(FragmentListado.getInstance(bund), "Administrar");
        bund= new Bundle();
       // bund.putSerializable("usuario",usu);
        FragmentMapa maps= new FragmentMapa();
        maps.setArguments(bund);
        adapter.addFragment(maps, "Localizacion");
        viewPager.setAdapter(adapter);

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }



}
