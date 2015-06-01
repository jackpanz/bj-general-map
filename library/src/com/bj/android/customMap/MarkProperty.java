package com.bj.android.customMap;

/**
 * Created by bj on 2015/6/1.
 */
public class MarkProperty {

    public MarkProperty(){

    }

    public MarkProperty(int realWidth,int realHeight,int centerX,int centerY){
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public MarkProperty(int realWidth,int realHeight,int centerX,int centerY,int changeMode){
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.centerX = centerX;
        this.centerY = centerY;
        this.changeMode = changeMode;
    }

    public int realWidth;
    public int realHeight;
    public int centerX;
    public int centerY;

    public int changeMode = GENERAL;

    public static int GENERAL = 0;

    public static int FIXED_SIZE = 1;


}
