package com.MyMentor.competitive.ui.gallery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.adapter.GridAdapterForImage;
import com.MyMentor.competitive.adapter.SliderAdapterImage;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.app.ExpandableHeightGridView;
import com.MyMentor.competitive.models.FILE;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.List;

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

        return root;
    }

    private void refreshContent() {
        List<FILE> images = dbManager.listFileByType("image");

        SliderAdapterImage adapter = new SliderAdapterImage(getContext(), images);
        sliderView.setSliderAdapter(adapter);

        GridAdapterForImage gridAdapter = new GridAdapterForImage(this.getContext(), images);
        gridView.setAdapter(gridAdapter);
    }


    private void showImage(int i) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra("position",i);
        startActivity(intent);
    }
}