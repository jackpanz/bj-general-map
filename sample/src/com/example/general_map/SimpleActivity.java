package com.example.general_map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.bj.android.customMap.MapController;
import com.bj.android.customMap.MarkProperty;


public class SimpleActivity extends Activity {

    private MapController mapController;

    public ImageView img_map;

    private Bitmap mapBitmap;

    private FrameLayout root;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        img_map = (ImageView) findViewById(R.id.img_map);
        root = (FrameLayout) findViewById(R.id.root);

        mapBitmap = ActivityUtils.getBitmapByResources(getResources(), R.drawable.r1_route);
        img_map.setImageBitmap(mapBitmap);

        mapController = new MapController(this, root, img_map);
        mapController.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mapController.setMaximumScale(5.0f);
        mapController.setOnMarkListener(new MapController.OnMarkListener() {
            @Override
            public void onMark(View ImageView, View view, float x, float y) {
                String title = (String) view.getTag();
                Toast.makeText(SimpleActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        });

        mapController.addMark(R.layout.simple_text, new MarkProperty(300, 120, 700, 300), "mark1");
        mapController.addMark(R.layout.simple_image, new MarkProperty(200, 200, 900, 900), "mark2");
        mapController.addMark(R.layout.simple_fixed_size, new MarkProperty(60, 60, 1200, 500, MarkProperty.FIXED_SIZE), "mark3");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapController.cleanup();
        mapController = null;
        ActivityUtils.recycle(mapBitmap);
        System.gc();
        Runtime.getRuntime().gc();
    }
}
