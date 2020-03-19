package com.MIAG.miaggce.ui.staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.MIAG.miaggce.MainActivity.userKey;

public class StaffFragment extends Fragment implements ListAdapterForStaff.ItemClicklistener {
    private GridView gridView;
    private DBManager dbManager;
    private List<STAFFMEMBER> staffmembers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_staff, container, false);


        gridView = root.findViewById(R.id.gridView);

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
        staffmembers = dbManager.listStaffMember();

        ListAdapterForStaff adapter = new ListAdapterForStaff(getContext(),staffmembers, this);
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
                    Toast.makeText(getContext(),R.string.error_server,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<STAFFMEMBER>> call, @NotNull Throwable t) {
                Toast.makeText(getContext(),R.string.update_fail,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            String text = "Hi! can you help me please?";// Replace with your message.

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+getphonenumber(position) +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick2(View view, int position) {
        Uri uri = Uri.parse("fb-messenger://user/"+getphonenumber(position));

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

    private String getphonenumber(int position){
        if (staffmembers.get(position).getSM_NUMBER().substring(0,3).equals("237"))
            return staffmembers.get(position).getSM_NUMBER();
        else
            return "237"+staffmembers.get(position).getSM_NUMBER();
    }
}