package com.raihan.dxball;

import android.graphics.RectF;

public class Brick {
    private RectF rect;
    private int padding;
    private int isVisible;
 
    public Brick(int row, int column, int width, int height){
        isVisible = 1;
        padding = 1;
        rect = new RectF(
        		column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }
 
    public RectF getRect(){
        return this.rect;
    }
 
    public void setInvisible(){
        isVisible = 0;
    }
 
    public int getVisibility(){
        return isVisible;
    }
}