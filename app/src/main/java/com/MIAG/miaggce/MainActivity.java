package com.MIAG.miaggce;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.MIAG.miaggce.app.AsyncTaskRunner;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.models.EXAM;
import com.MIAG.miaggce.ui.identification.IdentificationActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import static com.MIAG.miaggce.ui.splash.SplashScreen.EMAIL;
import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NAME;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NUMBER;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT1;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT2;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PASSWORD;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.USERKEY;

public class MainActivity extends AppCompatActivity implements MainView {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView name, period;
    public static String userKey;
    private ProgressBar progressBar;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
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

        Button logout = navigationView.getHeaderView(0).findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.logout(MainActivity.this,view);
            }
        });

        // Tâche asychrone chargé du téléchargement des nouvelles données dès que une connexion internet est établit.
        AsyncTaskRunner.AsyncTaskListener asyncTaskListener = new AsyncTaskRunner.AsyncTaskListener() {
            @Override
            public void startDownload() {
                getDataToServer();
            }
        };
        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(asyncTaskListener);
        asyncTaskRunner.execute("Update Data...");
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
        userKey = pref.getString(USERKEY,null);
    }

    public static void logout(final Activity activity, final View v) {
        Snackbar snackbar = Snackbar
                .make(v, "Do you want to Logout??", Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(v, "disconnection...", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(NAME,null);
                        editor.putString(NUMBER,null);
                        editor.putString(PASSWORD,null);
                        editor.putString(EMAIL,null);
                        editor.putString(PARENT1,null);
                        editor.putString(PARENT2,null);
                        editor.putBoolean(ENABLE,false);
                        editor.apply();
                        Intent intent = new Intent(activity, IdentificationActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });
        snackbar.show();
    }

    private void getDataToServer() {
        Toast.makeText(this, "Update Data...", Toast.LENGTH_SHORT).show();
        MainPresenter presenter = new MainPresenter(this, userKey);
        presenter.getExams();

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideLoadding() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoadind(String cause) {
        HideLoadding();
        Snackbar.make(progressBar,cause,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveExams(List<EXAM> exams) {
        DBManager dbManager = new DBManager(this);
        dbManager.open();
        dbManager.insertListExam(exams);
    }
}
