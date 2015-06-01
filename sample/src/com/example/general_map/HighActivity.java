package com.example.general_map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.bj.android.customMap.MapController;
import com.bj.android.customMap.MapLinearlayout;
import com.bj.android.customMap.MarkProperty;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;


public class HighActivity extends Activity {

    private MapController mapController;

    public ImageView img_map;

    private Bitmap mapBitmap;

    private FrameLayout root;

    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();

    int[] imageid = {
            R.drawable.building_1,
            R.drawable.building_2,
            R.drawable.building_3,
            R.drawable.building_4,

            R.drawable.icon_bus,
            R.drawable.icon_bus,
            R.drawable.icon_bus,
            R.drawable.icon_bus,
            R.drawable.icon_bus,

            R.drawable.icon_restaurant,
            R.drawable.icon_restaurant,
            R.drawable.icon_restaurant,
            R.drawable.icon_restaurant,
            R.drawable.icon_restaurant

    };

    //width height centerX centerY
    // map size 1 : 1



    public class Mark {

        public int category = 0;

        public Mark(String name) {
            this.name = name;
        }

        public String name;

    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_map);

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
                Object obj = view.getTag();
                if (obj instanceof Mark) {
                    Toast.makeText(HighActivity.this, ((Mark) obj).name, Toast.LENGTH_SHORT).show();
                }

            }
        });

        List<MarkProperty> MarkPropertys = new ArrayList<MarkProperty>() {
            {
                //values-hdpi values-xhdpi values-xxhdpi ..
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.building_width), getResources().getDimensionPixelSize(R.dimen.building_width), 700, 700));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.building_width), getResources().getDimensionPixelSize(R.dimen.building_width), 1100, 300));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.building_width), getResources().getDimensionPixelSize(R.dimen.building_width), 900, 900));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.building_width), getResources().getDimensionPixelSize(R.dimen.building_width), 1400, 1000));

                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 620, 350, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 650, 400, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 710, 500, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 650, 630, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 500, 700, MarkProperty.FIXED_SIZE));

                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 900, 1300, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 950, 1200, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 1100, 1150, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 1250, 1150, MarkProperty.FIXED_SIZE));
                add(new MarkProperty(getResources().getDimensionPixelSize(R.dimen.mark_width), getResources().getDimensionPixelSize(R.dimen.mark_width), 1350, 1000, MarkProperty.FIXED_SIZE));


            }
        };

        for (int i = 0; i < imageid.length; i++) {

            Mark mark = new Mark("mark" + i);
            if (imageid[i] == R.drawable.icon_bus) {
                mark.category = 1;
            } else if (imageid[i] == R.drawable.icon_restaurant) {
                mark.category = 2;
            }

            MapLinearlayout mapLinearlayout = (MapLinearlayout) mapController.addMark(R.layout.simple_image_null,
                    MarkPropertys.get(i)
                    , mark);

            ImageView imageView = (ImageView) mapLinearlayout.findViewById(R.id.image);
            Bitmap bitmap = ActivityUtils.getBitmapByResources(getResources(), imageid[i]);
            bitmaps.add(bitmap);
            imageView.setImageBitmap(bitmap);

        }

        //building bus restaurant
        findViewById(R.id.building).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(0);
            }
        });

        findViewById(R.id.bus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(1);

            }
        });

        findViewById(R.id.restaurant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide(2);
            }
        });


    }

    public void hide(int category) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof MapLinearlayout) {
                MapLinearlayout mapLinearlayout = (MapLinearlayout) view;
                Mark mark = (Mark) mapLinearlayout.getTag();

                if (mark.category == category) {
                    mapLinearlayout.setVisibility(
                            mapLinearlayout.getVisibility() == View.GONE ?
                                    View.VISIBLE : View.GONE
                    );

                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapController.cleanup();
        mapController = null;
        ActivityUtils.recycle(mapBitmap);
        for (int i = 0; i < bitmaps.size(); i++) {
            ActivityUtils.recycle(bitmaps.get(i));
        }
        bitmaps.clear();
        System.gc();
        Runtime.getRuntime().gc();

    }
}
