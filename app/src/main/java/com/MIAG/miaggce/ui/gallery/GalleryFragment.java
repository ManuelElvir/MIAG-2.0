package com.MIAG.miaggce.ui.gallery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.MIAG.miaggce.R;
import com.MIAG.miaggce.adapter.GridAdapterForImage;
import com.MIAG.miaggce.adapter.SliderAdapterImage;
import com.MIAG.miaggce.api.ApiClient;
import com.MIAG.miaggce.api.ApiInterface;
import com.MIAG.miaggce.app.DBManager;
import com.MIAG.miaggce.app.ExpandableHeightGridView;
import com.MIAG.miaggce.app.appConfig;
import com.MIAG.miaggce.models.FILE;
import com.MIAG.miaggce.ui.image.ImageActivity;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.MIAG.miaggce.MainActivity.userKey;

public class GalleryFragment extends Fragment {

    private ArrayList<String> images;
    private ExpandableHeightGridView gridView;
    private DBManager dbManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        //for slide

        SliderView sliderView = root.findViewById(R.id.imageSlider);

        SliderAdapterImage adapter = new SliderAdapterImage(getContext());

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        //for grid
        gridView = (ExpandableHeightGridView) root.findViewById(R.id.gridView);
        gridView.setExpanded(true);

        //replace to dynamics
        images = new ArrayList<>();
        images.add("https://cdn.pixabay.com/photo/2017/12/26/07/36/nature-3039901_960_720.jpg");
        images.add("https://www.photo-paysage.com/albums/userpics/10001/thumb_Cascades_Krka_Croatie.jpg");
        images.add("https://visiter-voyager.info/wp-content/uploads/2019/05/paysage-nature-900x600.jpg");
        images.add("https://i.skyrock.net/8258/65028258/pics/2611834504_1_12_w0l9jVcT.png");
        images.add("https://www.citationbonheur.fr/wp-content/uploads/2018/09/L_influence_du_paysage_sur_le_bonheur.jpg");
        images.add("https://www.competencephoto.com/photo/art/grande/31056991-29406133.jpg?v=1553260189");
        images.add("https://cdn.radiofrance.fr/s3/cruiser-production/2019/05/1de40013-99c6-4280-b82a-dbf5a9c0b55d/838_gettyimages-941329600.jpg");
        images.add("https://www.superprof.fr/ressources/file/2012/05/paysage-baudelaire-fleurs.jpg");
        images.add("https://phototrend.fr/wp-content/uploads/2015/03/AnneJutras_PhotoTrend_0_photo-intro-759x500.jpg");
        images.add("https://www.naturephotographie.com/wp-content/uploads/2019/08/Trouble-in-the-Sky.jpg");
        images.add("https://i.skyrock.net/8258/65028258/pics/2611834504_1_12_w0l9jVcT.png");
        images.add("https://i.pinimg.com/originals/d2/53/d1/d253d195962bbc80b1bd45d0fa407ed8.jpg");

        dbManager = new DBManager(getContext());
        dbManager.open();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showImage(i);
            }
        });
        refreshContent();

        if (appConfig.isInternetAvailable())
            LoadImageToServer();

        return root;
    }

    private void refreshContent() {
        List<FILE> fileImage = dbManager.listFileByType("image");

        GridAdapterForImage gridAdapter = new GridAdapterForImage(this.getContext(), images);
        gridView.setAdapter(gridAdapter);
    }

    private void LoadImageToServer() {
        ApiInterface apiInterface = ApiClient.getApiClient(userKey).create(ApiInterface.class);
        Call<List<FILE>> call = apiInterface.listImage("image");
        call.enqueue(new Callback<List<FILE>>() {
            @Override
            public void onResponse(Call<List<FILE>> call, Response<List<FILE>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    dbManager.insertListFile(response.body());
                    refreshContent();
                }else {
                    Snackbar.make(gridView,R.string.error_server,Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FILE>> call, Throwable t) {
                Snackbar.make(gridView,R.string.update_fail,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void showImage(int i) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("position",i);
        startActivity(intent);
    }
}