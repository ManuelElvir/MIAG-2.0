package com.MyMentor.competitive.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.MyMentor.competitive.R;
import com.MyMentor.competitive.app.DBManager;
import com.MyMentor.competitive.app.SimpleGestureFilter;
import com.MyMentor.competitive.models.FILE;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Objects;

public class ImageActivity extends AppCompatActivity implements
        SimpleGestureFilter.SimpleGestureListener{
    private SimpleGestureFilter detector;
    private List<FILE> images;
    ImageView image;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getText(R.string.galerie));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        position = getIntent().getIntExtra("position", 0);
        DBManager dbManager = new DBManager(this);
        dbManager.open();
        images = dbManager.listFileByType("image");
        image = findViewById(R.id.image);

        // Detect touched area
        detector = new SimpleGestureFilter(this, this);
        Picasso.get().load(images.get(position).getFILE_NAME()).into(image);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
            case SimpleGestureFilter.SWIPE_DOWN:
                if (position>0)
                    position --;
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
            case SimpleGestureFilter.SWIPE_UP:
                if (position<images.size()-1)
                    position ++;
                break;

        }
        Picasso.get().load(images.get(position).getFILE_NAME()).into(image);
    }


    //Toast shown when double tapped on screen
    @Override
    public void onDoubleTap() {
        if (image.getScaleType()== ImageView.ScaleType.CENTER_INSIDE)
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        else
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
