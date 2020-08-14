package com.MyMentor.competitive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.MyMentor.competitive.app.appConfig;
import com.MyMentor.competitive.ui.identification.IdentificationActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import static com.MyMentor.competitive.app.appConfig.EMAIL;
import static com.MyMentor.competitive.app.appConfig.ENABLE;
import static com.MyMentor.competitive.app.appConfig.NAME;
import static com.MyMentor.competitive.app.appConfig.NUMBER;
import static com.MyMentor.competitive.app.appConfig.PARENT1;
import static com.MyMentor.competitive.app.appConfig.PARENT2;
import static com.MyMentor.competitive.app.appConfig.PASSWORD;
import static com.MyMentor.competitive.app.appConfig.PREFERENCE;
import static com.MyMentor.competitive.app.appConfig.USERKEY;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView name, period;
    public static String userKey;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gce_a, R.id.nav_gce_o, R.id.nav_competitive,
                R.id.nav_staff, R.id.nav_galery, R.id.nav_user, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        pref = getApplicationContext().getSharedPreferences(PREFERENCE, 0);
        name = navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        period = navigationView.getHeaderView(0).findViewById(R.id.nav_period);
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_user);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });

        navigationView.setItemIconTintList(null);// retire la recoloration au click sur l'élément
        // ce drawer Listener nous aidera à définir ce qui ce passe lors de toute manipulation du drawer
        updateProfile(); //à chaque ouverture du drawer on mets à jour le profil
        DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                updateProfile(); //à chaque ouverture du drawer on mets à jour le profil
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
        drawer.addDrawerListener(drawerListener);

        final Spinner spinner = navigationView.getHeaderView(0).findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<>();
        categories.add("");
        categories.add(getString(R.string.logout));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void updateProfile(){
        name.setText(pref.getString(NAME, null)); // getting String
        if (pref.getBoolean(ENABLE, false)){ //get Boolean
            period.setText(getText(R.string.enable));
        }
        userKey = pref.getString(USERKEY, appConfig.DEFAULT_KEY);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 1) {
            Snackbar snackbar1 = Snackbar.make(view, "disconnection...", Snackbar.LENGTH_SHORT);
            snackbar1.show();
            SharedPreferences pref = getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(NAME, null);
            editor.putString(NUMBER, null);
            editor.putString(PASSWORD, null);
            editor.putString(EMAIL, null);
            editor.putString(PARENT1, null);
            editor.putString(PARENT2, null);
            editor.putBoolean(ENABLE, false);
            editor.apply();
            Intent intent = new Intent(this, IdentificationActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
