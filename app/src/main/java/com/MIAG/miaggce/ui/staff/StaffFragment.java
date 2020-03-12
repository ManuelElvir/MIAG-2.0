package com.MIAG.miaggce.ui.staff;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.ListAdapterForStaff;
import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.STAFFMEMBER;
import com.MIAG.miaggce.models.TeamMember;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.MIAG.miaggce.MainActivity.userKey;

public class StaffFragment extends Fragment implements ListAdapterForStaff.ItemClicklistener {
    private List<TeamMember> staff;
    private GridView gridView;
    private DBManager dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_staff, container, false);


        gridView = root.findViewById(R.id.gridView);

        //replace to dynamics
        staff = new ArrayList<TeamMember>();
        for (int i=0; i<6; i++){
            TeamMember member = new TeamMember();
            member.setName("Manuel Elvir");
            member.setFunction("Polytechnicien Informatique");
            member.setNumber("+237695643360");
            member.setImage("https://i.imgur.com/oW1dGDI.jpg");
            member.setNumber_messenger("2836882129709310");
            staff.add(member);
        }

        dbManager = new DBManager(getContext());
        dbManager.open();

        refreshContent();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (appConfig.isInternetAvailable())
            LoadStaffToServer();

        return root;
    }

    private void refreshContent() {
        List<STAFFMEMBER> staffmembers = dbManager.listStaffMember();

        Log.e("STAFF GET BASE", String.valueOf(staffmembers));

        ListAdapterForStaff adapter = new ListAdapterForStaff(getContext(),staff, this);
        gridView.setAdapter(adapter);
    }

    private void LoadStaffToServer() {
        ApiInterface apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
        Call<List<STAFFMEMBER>> call = apiInterface.listStaffMember();
        call.enqueue(new Callback<List<STAFFMEMBER>>() {
            @Override
            public void onResponse(@NotNull Call<List<STAFFMEMBER>> call, @NotNull Response<List<STAFFMEMBER>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    dbManager.insertListStaffMember(response.body());
                    refreshContent();
                }else {
                    Snackbar.make(gridView,R.string.error_server,Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<STAFFMEMBER>> call, @NotNull Throwable t) {
                Snackbar.make(gridView,R.string.update_fail,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            String text = "Hi! can you help me please?";// Replace with your message.

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+staff.get(position).getNumber() +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick2(View view, int position) {
        Uri uri = Uri.parse("fb-messenger://user/"+staff.get(position).getNumber_messenger());

        Intent toMessenger= new Intent(Intent.ACTION_VIEW, uri);
        startActivity(toMessenger);
        try {
            startActivity(toMessenger);
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            Toast.makeText(getContext(), "Please Install Facebook Messenger",    Toast.LENGTH_LONG).show();
        }
    }
}