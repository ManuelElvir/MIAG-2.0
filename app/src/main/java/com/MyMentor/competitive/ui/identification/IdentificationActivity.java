package com.MyMentor.competitive.ui.identification;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.MyMentor.competitive.MainActivity;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.api.ApiClient;
import com.MyMentor.competitive.api.ApiInterface;
import com.MyMentor.competitive.app.appConfig;
import com.MyMentor.competitive.models.RESPONSE;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.MyMentor.competitive.app.appConfig.EMAIL;
import static com.MyMentor.competitive.app.appConfig.ENABLE;
import static com.MyMentor.competitive.app.appConfig.ID;
import static com.MyMentor.competitive.app.appConfig.NAME;
import static com.MyMentor.competitive.app.appConfig.NUMBER;
import static com.MyMentor.competitive.app.appConfig.PARENT1;
import static com.MyMentor.competitive.app.appConfig.PARENT2;
import static com.MyMentor.competitive.app.appConfig.PASSWORD;
import static com.MyMentor.competitive.app.appConfig.REGISTER_KEY;
import static com.MyMentor.competitive.app.appConfig.USERKEY;

/**
 * @author Manuel Elvir
 * @version 1.0.0
 * this is activity for login and signin
 */
public class IdentificationActivity extends AppCompatActivity {

    public final String PREFERENCE = "My_Mentor_Competitive";
    EditText number, password, name;
    Button login, singIn;
    LinearLayout layoutSingIn, layoutLogin;
    Button buttonLogin, buttonSingIn;
    ProgressBar animate;
    boolean isLogin = true;
    Animation animFadeIn, animFadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        login = findViewById(R.id.button_login);
        singIn = findViewById(R.id.button_singin);

        buttonLogin = findViewById(R.id.text_login);
        buttonSingIn = findViewById(R.id.text_singin);

        layoutLogin = findViewById(R.id.layout_login);
        layoutSingIn = findViewById(R.id.layout_singin);

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
                        layoutSingIn.setVisibility(View.GONE);
                        layoutLogin.startAnimation(animFadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                layoutSingIn.startAnimation(animFadeOut);
            }
        });

        buttonSingIn.setOnClickListener(new View.OnClickListener() {
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
                        layoutSingIn.setVisibility(View.VISIBLE);
                        layoutSingIn.startAnimation(animFadeIn);
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
                LoginToServer(number.getText().toString(), password.getText().toString());
            }
        });

        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = findViewById(R.id.number_singin);
                password = findViewById(R.id.password_singin);
                name = findViewById(R.id.name_singin);
                SinginToServer(name.getText().toString(), number.getText().toString(), password.getText().toString());
            }
        });


    }

    private void LoginToServer(String number, final String password) {
        startAnimation();
        ApiInterface apiInterface = ApiClient.getApiClient(appConfig.DEFAULT_KEY).create(ApiInterface.class);
        Call<RESPONSE> call = apiInterface.connectStudent(number,password);
        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<RESPONSE> call, @NotNull Response<RESPONSE> response) {
                revertAnimation();
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().getSuccess()){
                        boolean enable = false;
                        if (response.body().getStudent().getSTD_STATE().equals("1"))
                            enable =true;
                        saveConnexion(response.body().getStudent().getSTD_ID(),response.body().getStudent().getSTD_NAME(), response.body().getStudent().getSTD_NUMBER(), password,response.body().getStudent().getSTD_EMAIL(), response.body().getStudent().getSTD_TEL_PARENT1(),response.body().getStudent().getSTD_TEL_PARENT2(),enable, response.body().getUser_Key(), response.body().getStudent().getALL_FUNC_KEY());
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
            }
        });
    }

    private void SinginToServer(final String name, final String number, final String password) {
        startAnimation();
        ApiInterface apiInterface = ApiClient.getApiClient(appConfig.DEFAULT_KEY).create(ApiInterface.class);
        Call<RESPONSE> call = apiInterface.addStudent(name,number,password,"contact@miag.com",number,number);
        call.enqueue(new Callback<RESPONSE>() {
            @Override
            public void onResponse(@NotNull Call<RESPONSE> call, @NotNull Response<RESPONSE> response) {
                revertAnimation();
                if (response.isSuccessful() && response.body()!=null){
                    if (response.body().getSuccess()){
                        saveConnexion(response.body().getStudent().getSTD_ID(),name, number, password,"", "","",false, response.body().getUser_Key(), response.body().getStudent().getALL_FUNC_KEY());
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
            }
        });
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
                            singIn.callOnClick();
                    }
                });
        snackbar.show();
    }

    private void saveConnexion(String id, String name, String number, String password, String email, String parent1, String parent2, boolean enable, String key, String registerKey){
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putString(ID, id);
        editor.putString(NUMBER, number);
        editor.putString(PASSWORD, password);
        editor.putString(EMAIL, email);
        editor.putString(NAME, name);
        editor.putString(USERKEY, key);
        editor.putString(PARENT1, parent1);
        editor.putString(PARENT2, parent2);
        editor.putBoolean(ENABLE, enable);
        editor.putString(REGISTER_KEY, registerKey);
        editor.apply();


        Intent intent = new Intent(IdentificationActivity.this, MainActivity.class);
        startActivity(intent);
        IdentificationActivity.this.finish();
    }
}
