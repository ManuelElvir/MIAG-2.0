package com.MIAG.miaggce.ui.splash;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.ui.identification.IdentificationActivity;

/**
 * @author Manuel ELvir
 * @version 1.0.0
 * this activity is show when the application start. she verify if the somebody user is connect an start correspond activity
 */
public class SplashScreen extends AppCompatActivity {

    public static  final String PREFERENCE = "MIAG_GCE", ID = "id", NUMBER = "number", NAME = "name", EMAIL = "email", PASSWORD = "password", PARENT1 = "parent1", PARENT2 = "parent2", ENABLE = "enable", USERKEY = "userkey", REGISTER_KEY = "all_func_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title and set to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        /* get to preference if somebody user is connected */
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
        final boolean isConnect = null != pref.getString(NUMBER, null);

        /* Wait 2 seconds */
        int SPLASH_DISPLAY_LENGTH = 2000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                if (isConnect) // if a user is connected start MainActivity
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                else    //if no body user connected start IdentificationActivity
                    intent = new Intent(SplashScreen.this, IdentificationActivity.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
