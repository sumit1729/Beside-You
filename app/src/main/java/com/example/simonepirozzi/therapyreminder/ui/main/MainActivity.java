package com.example.simonepirozzi.therapyreminder.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.simonepirozzi.therapyreminder.ui.account.AccountFragment;
import com.example.simonepirozzi.therapyreminder.ui.home.HomeFragment;
import com.example.simonepirozzi.therapyreminder.MedicoFragment;
import com.example.simonepirozzi.therapyreminder.R;
import com.example.simonepirozzi.therapyreminder.ui.therapy.TherapyFragment;
import com.example.simonepirozzi.therapyreminder.data.db.TinyDB;
import com.example.simonepirozzi.therapyreminder.VisiteFragment;

public class MainActivity extends AppCompatActivity {

    TinyDB db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_home:
                    setTitle(getString(R.string.title_nav_home));
                    fragmentTransaction.replace(R.id.containerFrame, new HomeFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                    return true;

                case R.id.nav_therapy:
                    setTitle(getString(R.string.title_nav_therapy));
                    fragmentTransaction.replace(R.id.containerFrame, new TherapyFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                    return true;

                case R.id.nav_examination:
                    setTitle(getString(R.string.title_nav_exam));
                    fragmentTransaction.replace(R.id.containerFrame, new VisiteFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                    return true;

                case R.id.nav_doctor:
                    setTitle(getString(R.string.title_nav_doctor));
                    fragmentTransaction.replace(R.id.containerFrame, new MedicoFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                    return true;

                case R.id.nav_account:
                    setTitle(getString(R.string.title_nav_account));
                    fragmentTransaction.replace(R.id.containerFrame, new AccountFragment()).commit();
                    fragmentTransaction.addToBackStack(null);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFrame, new HomeFragment()).commit();
        fragmentTransaction.addToBackStack(null);
        db = new TinyDB(getApplicationContext());


    }

}
