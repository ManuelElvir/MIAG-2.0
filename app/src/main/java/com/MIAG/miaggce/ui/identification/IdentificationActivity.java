package com.MIAG.miaggce.ui.identification;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.RESPONSE;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.MIAG.miaggce.ui.splash.SplashScreen.EMAIL;
import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.ID;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NAME;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NUMBER;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT1;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT2;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PASSWORD;
import static com.MIAG.miaggce.ui.splash.SplashScreen.USERKEY;

/**
 * @author Manuel Elvir
 * @version 1.0.0
 * this is activity for login and signin
 */
public class IdentificationActivity extends AppCompatActivity {

    public final String PREFERENCE = "MIAG_GCE";
    EditText number, password, name;
    Button login, singin;
    LinearLayout layoutSingin, layoutLogin;
    Button buttonLogin, buttonSingin;
    ProgressBar animate;
    boolean isLogin = true, success =false;
    Animation animFadeIn, animFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification2);

        login = findViewById(R.id.button_login);
        singin = findViewById(R.id.button_singin);

        buttonLogin = findViewById(R.id.text_login);
        buttonSingin = findViewById(R.id.text_singin);

        layoutLogin = findViewById(R.id.layout_login);
        layoutSingin = findViewById(R.id.layout_singin);

        animate = findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isLogin =true;
                animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_slide_down);
                animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out_slide_up);
                animFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layoutLogin.setVisibility(View.VISIBLE);
                        layoutSingin.setVisibility(View.GONE);
                        layoutLogin.startAnimation(animFadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                layoutSingin.startAnimation(animFadeOut);
            }
        });

        buttonSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogin =false;
                animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_slide_down);
                animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out_slide_up);
                animFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        layoutLogin.setVisibility(View.GONE);
                        layoutSingin.setVisibility(View.VISIBLE);
                        layoutSingin.startAnimation(animFadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                layoutLogin.startAnimation(animFadeOut);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = findViewById(R.id.number_login);
                password = findViewById(R.id.password_login);

                if (LoginToServer(number.getText().toString(), password.getText().toString())){
                    Intent intent = new Intent(IdentificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    IdentificationActivity.this.finish();
                }
            }
        });

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = findViewById(R.id.number_singin);
                password = findViewById(R.id.password_singin);
                name = findViewById(R.id.name_singin);

                if (SinginToServer(name.getText().toString(), number.getText().toString(), password.getText().toString())){
                    Intent intent = new Intent(IdentificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    IdentificationActivity.this.finish();
                }
            }
        });


    }

    private boolean LoginToServer(String number, final String password) {
        startAnimation();
        ApiInterface apiInterface = ApiClient.getApiClient(appConfig.DEFAULT_KEY).create(ApiInterface.class);
        Call<RESPONSE> call = apiInterface.connectStudent(number,password);
        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<RESPONSE> call, @NotNull Response<RESPONSE> response) {
                revertAnimation();
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().getSuccess()){
                        success = true;
                        boolean enable = false;
                        if (response.body().getStudent().getSTD_STATE()==1)
                            enable =true;
                        saveConnexion(response.body().getStudent().getSTD_ID(),response.body().getStudent().getSTD_NAME(), response.body().getStudent().getSTD_EMAIL(), password,response.body().getStudent().getSTD_EMAIL(), response.body().getStudent().getSTD_TEL_PARENT1(),response.body().getStudent().getSTD_TEL_PARENT2(),enable);
                    }
                    else{
                        errorReponse(response.body().getCause());
                    }
                }
                else
                    errorReponse(IdentificationActivity.this.getString(R.string.error_server));
            }

            @Override
            public void onFailure(@NotNull Call<RESPONSE> call, @NotNull Throwable t) {
                revertAnimation();
                errorReponse(t.getLocalizedMessage());
                Log.e("Request error",""+t.getLocalizedMessage());
            }
        });
        return success;
    }

    private boolean SinginToServer(final String name, final String number, final String password) {
        startAnimation();
        ApiInterface apiInterface = ApiClient.getApiClient(appConfig.DEFAULT_KEY).create(ApiInterface.class);
        Call<RESPONSE> call = apiInterface.addStudent(name,number,password,"","","");
        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<RESPONSE> call, @NotNull Response<RESPONSE> response) {
                revertAnimation();
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().getSuccess()){
                        success = true;
                        saveConnexion(response.body().getStudent().getSTD_ID(),name, number, password,"", "","",false);
                    }
                    else
                        errorReponse(response.body().getCause());
                }
                else
                    errorReponse(IdentificationActivity.this.getString(R.string.error_server));
            }

            @Override
            public void onFailure(@NotNull Call<RESPONSE> call, @NotNull Throwable t) {
                revertAnimation();
                errorReponse(t.getLocalizedMessage());
                Log.e("Request error",""+t.getLocalizedMessage());
            }
        });
        return success;
    }

    private void revertAnimation() {
        animate.setVisibility(View.GONE);
    }

    private void startAnimation() {
        animate.setVisibility(View.VISIBLE);
    }

    private void errorReponse(String message) {
        Snackbar snackbar = Snackbar
                .make(animate, message, Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isLogin)
                            login.callOnClick();
                        else
                            singin.callOnClick();
                    }
                });
        snackbar.show();
    }

    private void saveConnexion(int id, String name, String number, String password, String email, String parent1, String parent2, boolean enable){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putInt(ID, id);
        editor.putString(NUMBER, number);
        editor.putString(PASSWORD, password);
        editor.putString(EMAIL, email);
        editor.putString(NAME, name);
        editor.putString(USERKEY, email);
        editor.putString(PARENT1, parent1);
        editor.putString(PARENT2, parent2);
        editor.putBoolean(ENABLE, enable);
        editor.apply();
    }
}
