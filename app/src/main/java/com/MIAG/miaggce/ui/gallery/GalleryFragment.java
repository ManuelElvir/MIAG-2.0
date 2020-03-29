package com.MIAG.miaggce.ui.gallery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForImage;
import com.MIAG.miaggce.adapter.SliderAdapterImage;
import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.app.AsyncTaskRunner;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.app.ExpandableHeightGridView;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.FILE;
import com.MIAG.miaggce.ui.image.ImageActivity;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.MIAG.miaggce.MainActivity.userKey;

public class GalleryFragment extends Fragment {

    private ExpandableHeightGridView gridView;
    private SliderView sliderView;
    private DBManager dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        //for slide

        sliderView = root.findViewById(R.id.imageSlider);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        //for grid
        gridView =  root.findViewById(R.id.gridView);
        gridView.setExpanded(true);


        dbManager = new DBManager(getContext());
        dbManager.open();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showImage(i);
            }
        });
        refreshContent();

        AsyncTaskRunner.AsyncTaskListener asyncTaskListener = new AsyncTaskRunner.AsyncTaskListener() {
            @Override
            public void startDownload() {
                LoadImageToServer();
            }
        };
        AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(asyncTaskListener);
        asyncTaskRunner.execute("Update Data...");
        return root;
    }

    private void refreshContent() {
        List<FILE> images = dbManager.listFileByType();

        SliderAdapterImage adapter = new SliderAdapterImage(getContext(), images);
        sliderView.setSliderAdapter(adapter);

        GridAdapterForImage gridAdapter = new GridAdapterForImage(this.getContext(), images);
        gridView.setAdapter(gridAdapter);
    }

    private void LoadImageToServer() {
        ApiInterface apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
        Call<List<FILE>> call = apiInterface.listImage();
        call.enqueue(new Callback<List<FILE>>() {
            @Override
            public void onResponse(@NotNull Call<List<FILE>> call, @NotNull Response<List<FILE>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    dbManager.insertListFile(response.body());
                    refreshContent();
                }else {
                    Snackbar.make(gridView,R.string.error_server,Snackbar.LENGTH_SHORT).show();
                    refreshContent();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<FILE>> call, @NotNull Throwable t) {
                Snackbar.make(gridView,R.string.update_fail,Snackbar.LENGTH_SHORT).show();
                refreshContent();
            }
        });
    }

    private void showImage(int i) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra("position",i);
        startActivity(intent);
    }
}