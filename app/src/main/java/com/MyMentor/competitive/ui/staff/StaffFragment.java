package com.MyMentor.competitive.ui.staff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.ListAdapterForStaff;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.models.STAFFMEMBER;
import java.util.List;

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

        return root;
    }

    private void refreshContent() {
        staffmembers = dbManager.listStaffMember();

        ListAdapterForStaff adapter = new ListAdapterForStaff(getContext(),staffmembers, this);
        gridView.setAdapter(adapter);
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