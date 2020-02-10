package com.MIAG.miaggce.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.MainActivity;
import com.MIAG.miaggce.R;
import com.MIAG.miaggce.ui.identification.IdentificationActivity;
import com.google.android.material.snackbar.Snackbar;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import static com.MIAG.miaggce.ui.splash.SplashScreen.EMAIL;
import static com.MIAG.miaggce.ui.splash.SplashScreen.ENABLE;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NAME;
import static com.MIAG.miaggce.ui.splash.SplashScreen.NUMBER;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT1;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PARENT2;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PASSWORD;
import static com.MIAG.miaggce.ui.splash.SplashScreen.PREFERENCE;

public class AccountFragment extends Fragment {

    EditText name, number, password, email, parent1, parent2, code;
    TextView textName;
    Button save, register, logout, discard;
    SharedPreferences pref;
    View root;
    ProgressBar animate;
    boolean isFisrt = true;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        pref = getContext().getSharedPreferences(PREFERENCE, 0); // 0 - for private mode
        root = inflater.inflate(R.layout.fragment_account, container, false);

        name = root.findViewById(R.id.name);
        number = root.findViewById(R.id.number);
        password = root.findViewById(R.id.password);
        email = root.findViewById(R.id.email);
        parent1 = root.findViewById(R.id.parent1);
        parent2 = root.findViewById(R.id.parent2);
        textName = root.findViewById(R.id.textName);
        code = root.findViewById(R.id.code);
        save = root.findViewById(R.id.save);
        discard = root.findViewById(R.id.discard);
        register = root.findViewById(R.id.validate);
        logout = root.findViewById(R.id.logout);
        animate = root.findViewById(R.id.progressBar);

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
                enableAccountToServer();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
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

    private void saveData(){
        if (saveToServer()){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(NAME,name.getText().toString());
            editor.putString(NUMBER,number.getText().toString());
            editor.putString(PASSWORD,password.getText().toString());
            editor.putString(EMAIL,email.getText().toString());
            editor.putString(PARENT1,parent1.getText().toString());
            editor.putString(PARENT2,parent2.getText().toString());
            editor.apply();
            textName.setText(name.getText().toString());
        }else {
            Snackbar.make(root,getText(R.string.error),Snackbar.LENGTH_SHORT).show();
        }
    }

    private void saveCode(boolean success){
        if (success){
            NestedScrollView scrollView = root.findViewById(R.id.scrollView);
            scrollView.invalidate();
            scrollView.requestLayout();
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(ENABLE, true);
            editor.apply();
            verifyRegistration();
            Snackbar.make(root,"registration success!",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(root,"Invalide code!!",Snackbar.LENGTH_SHORT).show();
        }

    }

    private boolean saveToServer(){
        startAnimation();

        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                revertAnimation();
                Snackbar.make(root,"Data Saved!",Snackbar.LENGTH_SHORT).show();
            }
        }, SPLASH_DISPLAY_LENGTH);
        return true;
    }

    private void revertAnimation() {
        animate.setVisibility(View.GONE);
    }

    private void startAnimation() {
        animate.setVisibility(View.VISIBLE);
    }

    private boolean enableAccountToServer(){
        startAnimation();

        int SPLASH_DISPLAY_LENGTH = 5000;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                revertAnimation();
                saveCode(true);
            }
        }, SPLASH_DISPLAY_LENGTH);
        return true;
    }
}