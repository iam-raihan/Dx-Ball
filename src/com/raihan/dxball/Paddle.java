package com.raihan.dxball;

import android.graphics.RectF;

public class Paddle {
    private RectF rect;
    private float length;
    private float height;
    private float x;
    private float y;
    private float paddleSpeed;
 
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
 
    private int paddleMoving = STOPPED;
 
    public Paddle(int screenX, int screenY){
        length = 230;
        height = 20;
        x = screenX / 2;
        y = screenY - 20;
        rect = new RectF(x, y, x + length, y + height);
        paddleSpeed = 450;
    }
    
    public RectF getRect(){
        return rect;
    }
    
    public void setMovementState(int state){
        paddleMoving = state;
    }
 
    public void update(long fps, int screenX){
        if(paddleMoving == LEFT && rect.left > 0){
            x = x - paddleSpeed / fps;
        }
        if(paddleMoving == RIGHT && rect.right < screenX){
            x = x + paddleSpeed / fps;
        }
        rect.left = x;
        rect.right = x + length;
    }
}