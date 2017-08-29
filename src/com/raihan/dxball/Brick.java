package com.raihan.dxball;

import android.graphics.RectF;

public class Brick {
    private RectF rect;
    private int isVisible;
 
    public Brick(int row, int column, int width, int height, int visible){
        isVisible = visible;
        int padding = 1;
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