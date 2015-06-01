package com.bj.android.customMap;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by bj on 2015/6/1.
 */
public class MapLinearlayout extends LinearLayout {

    public MarkProperty markProperty = new MarkProperty();

    public int layouId;

    public MapLinearlayout(Context context) {
        super(context);
    }

    public MapLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapLinearlayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarkProperty getMarkProperty() {
        return markProperty;
    }

    public void setMarkProperty(MarkProperty markProperty) {
        this.markProperty = markProperty;
    }

}
