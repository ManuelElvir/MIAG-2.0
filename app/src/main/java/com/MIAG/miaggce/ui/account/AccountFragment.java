package com.MIAG.miaggce.ui.account;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.RESPONSE;
import com.google.android.material.snackbar.Snackbar;
import java.util.Objects;
import static com.MIAG.miaggce.ui.splash.SplashScreen.EMAIL;
import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.ID;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NAME;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NUMBER;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT1;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT2;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PASSWORD;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.REGISTER_KEY;

public class AccountFragment extends Fragment implements AccountView {

    private EditText name, number, password, email, parent1, parent2, code;
    private TextView textName;
    private SharedPreferences pref;
    private View root;
    private ProgressBar animate;
    private boolean isFisrt = true;
    private AccountPresenter presenter;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        pref = Objects.requireNonNull(getContext()).getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
        root = inflater.inflate(R.layout.fragment_account, container, false);

        name = root.findViewById(R.id.name);
        number = root.findViewById(R.id.number);
        password = root.findViewById(R.id.password);
        email = root.findViewById(R.id.email);
        parent1 = root.findViewById(R.id.parent1);
        parent2 = root.findViewById(R.id.parent2);
        textName = root.findViewById(R.id.textName);
        code = root.findViewById(R.id.code);
        Button save = root.findViewById(R.id.save);
        Button discard = root.findViewById(R.id.discard);
        Button register = root.findViewById(R.id.validate);
        Button logout = root.findViewById(R.id.logout);
        animate = root.findViewById(R.id.progressBar);

        presenter = new AccountPresenter(this,MainActivity.userKey);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.logout(AccountFragment.this.getActivity(),view);
            }
        });

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        resetData();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                if(appConfig.isInternetAvailable())
                    presenter.register(Integer.valueOf(pref.getString(ID,"0")),code.getText().toString());
                else {
                    if(pref.getString(REGISTER_KEY,"").equals(code.getText().toString()))
                        onRegisterSuccess();
                    else
                        onReceiveError("This register key is not correct");
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = 0;
                if(pref.getBoolean(ENABLE,false))
                    state = 1;

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                if(appConfig.isInternetAvailable())
                    presenter.updateUser(Integer.valueOf(pref.getString(ID,"0")),name.getText().toString(),
                        number.getText().toString(),
                        password.getText().toString(),
                        email.getText().toString(),
                        parent1.getText().toString(),
                        parent2.getText().toString(), state);
                else {
                    onUpdateSuccess(null);
                }
            }
        });

        verifyRegistration();

        return root;
    }

    private void verifyRegistration() {

        if (pref.getBoolean(ENABLE,false)){
            LinearLayout layout = root.findViewById(R.id.bloc_registration);
            layout.setVisibility(View.GONE);
        }
    }

    private void resetData(){

        String val_name = pref.getString(NAME,null);
        String val_number = pref.getString(NUMBER,null);
        String val_password = pref.getString(PASSWORD,null);
        String val_email = pref.getString(EMAIL,null);
        String val_parent1 = pref.getString(PARENT1,null);
        String val_parent2 = pref.getString(PARENT2,null);

        if (val_name!=null)
            name.setText(val_name);
        number.setText(val_number);
        password.setText(val_password);
        if (val_email!=null)
            email.setText(val_email);
        if (val_parent1!=null)
            parent1.setText(val_parent1);
        if (val_parent2!=null)
            parent2.setText(val_parent2);

        textName.setText(name.getText().toString());
        if (isFisrt)
            isFisrt = false;
        else
            Snackbar.make(root,"Data Restore",Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onShowLoading() {
        animate.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoadig() {
        animate.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateSuccess(RESPONSE response) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(NAME,name.getText().toString());
        editor.putString(NUMBER,number.getText().toString());
        editor.putString(PASSWORD,password.getText().toString());
        editor.putString(EMAIL,email.getText().toString());
        editor.putString(PARENT1,parent1.getText().toString());
        editor.putString(PARENT2,parent2.getText().toString());
        editor.apply();
        textName.setText(name.getText().toString());
        Snackbar.make(root,"Update success",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveError(String message) {
        Snackbar.make(animate,message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(ENABLE, true);
        editor.apply();
        verifyRegistration();
        Snackbar.make(root,"registration success!",Snackbar.LENGTH_SHORT).show();
    }
}