package com.bj.android.customMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.nineoldandroids.view.ViewHelper;
import uk.co.senab.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bj on 2015/6/1.
 */
public class MapController extends PhotoViewAttacher {

    ViewGroup mRoot;

    Context mContext;

    public MapController(Context context, ViewGroup root, ImageView imageView) {

        super(imageView);
        this.mContext = context;
        this.mRoot = root;

        this.setOnMatrixChangeListener(new PhotoViewAttacher.OnMatrixChangedListener() {

            @Override
            public void onMatrixChanged(RectF rect) {

                Drawable drawable = getImageView().getDrawable();
                if (drawable == null || !(drawable instanceof BitmapDrawable)) {
                    return;
                }

                BitmapDrawable bitmapDrawable = (BitmapDrawable) getImageView().getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                if (bitmap == null || bitmap.isRecycled()) {
                    return;
                }

                int imageWidth = bitmapDrawable.getBitmap().getWidth();

                float p = imageWidth / rect.width();
                float p1 = rect.width() / imageWidth;

                for (int i = 0; i < mRoot.getChildCount(); i++) {
                    View view = mRoot.getChildAt(i);
                    if (view instanceof MapLinearlayout) {

                        MapLinearlayout mapLinearlayout = (MapLinearlayout) view;
                        if (mapLinearlayout.markProperty.changeMode == MarkProperty.GENERAL) {

                            float mapx = mapLinearlayout.markProperty.centerX;
                            float mapy = mapLinearlayout.markProperty.centerY;
                            mapLinearlayout.setX(mapx / p + rect.left - mapLinearlayout.markProperty.realWidth / 2);
                            mapLinearlayout.setY(mapy / p + rect.top - mapLinearlayout.markProperty.realHeight / 2);
                            ViewHelper.setScaleX(mapLinearlayout, p1);
                            ViewHelper.setScaleY(mapLinearlayout, p1);
                            mapLinearlayout.requestLayout();

                        } else if (mapLinearlayout.markProperty.changeMode == MarkProperty.FIXED_SIZE) {

                            float mapx = mapLinearlayout.markProperty.centerX;
                            float mapy = mapLinearlayout.markProperty.centerY;
                            mapLinearlayout.setX(mapx / p + rect.left - mapLinearlayout.markProperty.realWidth / 2);
                            mapLinearlayout.setY(mapy / p + rect.top - mapLinearlayout.markProperty.realHeight / 2);
                            mapLinearlayout.requestLayout();

                        }

                    }
                }
            }
        });


        this.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View imageView, float x, float y) {



                for (int i = mRoot.getChildCount() - 1 ; i >= 0; i--) {
                    View view = mRoot.getChildAt(i);
                    if (view instanceof MapLinearlayout) {

                        MapLinearlayout mapLinearlayout = (MapLinearlayout) view;

                        float viewX, viewY, viewWidth, viewHeight;
                        viewWidth = mapLinearlayout.markProperty.realWidth * ViewHelper.getScaleX(view);
                        viewHeight = mapLinearlayout.markProperty.realHeight * ViewHelper.getScaleY(view);
                        viewX = mapLinearlayout.getX() + (mapLinearlayout.markProperty.realWidth - viewWidth) / 2;
                        viewY = mapLinearlayout.getY() + (mapLinearlayout.markProperty.realHeight - viewHeight) / 2;

                        Path path = new Path();
                        path.moveTo(viewX, viewY);
                        path.lineTo(viewX + viewWidth, viewY);
                        path.lineTo((viewX + viewWidth), (viewY + viewHeight));
                        path.lineTo(viewX, (viewY + viewHeight));

                        RectF rectF = new RectF();
                        path.computeBounds(rectF, true);

                        if (rectF.contains(x, y)) {
                            if (onMarkListener != null) {
                                onMarkListener.onMark(imageView, mapLinearlayout, x, y);
                            }
                            return;
                        }

                    }
                }

            }
        });

    }

    public OnMarkListener onMarkListener;

    public static interface OnMarkListener {
        void onMark(View ImageView, View view, float x, float y);
    }

    public OnMarkListener getOnMarkListener() {
        return onMarkListener;
    }

    public void setOnMarkListener(OnMarkListener onMarkListener) {
        this.onMarkListener = onMarkListener;
    }

    public View addMark(int layouId, MarkProperty markProperty) {
        return addMark(layouId, markProperty, null);
    }

    public View addMark(int layouId, MarkProperty markProperty, Object tag) {
        MapLinearlayout mapLinearlayout = (MapLinearlayout) LayoutInflater.from(mContext).inflate(layouId, null);
        mapLinearlayout.layouId = layouId;
        mapLinearlayout.setTag(tag);
        mapLinearlayout.setMarkProperty(markProperty);
        mRoot.addView(mapLinearlayout, markProperty.realWidth, markProperty.realHeight);
        return mapLinearlayout;
    }

}
