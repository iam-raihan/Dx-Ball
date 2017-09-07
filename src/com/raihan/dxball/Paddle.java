package com.raihan.dxball;

import android.graphics.RectF;

public class Paddle {
    private RectF rectL;
    private RectF rectR;
    
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
        length = 70;
        height = 20;
        x = screenX / 2;
        y = screenY - 20;
        rectL = new RectF(x - length, y, x, y + height);
        rectR = new RectF(x, y, x + length, y + height);
        paddleSpeed = 450;
    }
    
    public RectF getRectL(){
        return rectL;
    }
    
    public RectF getRectR(){
        return rectR;
    }
    
    public void setMovementState(int state){
        paddleMoving = state;
    }
 
    public void update(long fps, int screenX){
        if(paddleMoving == LEFT && rectL.left > 0){
            x = x - paddleSpeed / fps;
        }
        if(paddleMoving == RIGHT && rectR.right < screenX){
            x = x + paddleSpeed / fps;
        }
        setPaddlePosX();
    }
    
    public void reset(int screenX){
    	x = screenX / 2;
    	setPaddlePosX();
    }
    
    private void setPaddlePosX(){
    	rectL.left = x - length;
        rectL.right = x;
        rectR.left = x;
        rectR.right = x + length;
    }
}