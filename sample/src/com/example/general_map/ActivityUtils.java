package com.example.general_map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: bj
 * Date: 13-5-8
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public class ActivityUtils {

    public static void recycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public static void recycleImageView(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                if (bitmapDrawable != null && bitmapDrawable.getBitmap() != null && !bitmapDrawable.getBitmap().isRecycled()) {
                    imageView.setImageDrawable(null);
                    bitmapDrawable.setCallback(null);
                    if (!bitmapDrawable.getBitmap().isRecycled()) {
                        bitmapDrawable.getBitmap().recycle();
                    }
                }
            }
        }
    }

    public static Bitmap getBitmapByResources(Resources res, int resources) {
        InputStream inputStream = null;
        BitmapDrawable bitmapDrawable = null;

        try {
            inputStream = res.openRawResource(resources);
            bitmapDrawable = new BitmapDrawable(res, inputStream);
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }

        }

        return bitmapDrawable != null?bitmapDrawable.getBitmap():null;
    }

}
