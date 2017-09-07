package com.raihan.dxball;

import android.graphics.RectF;

import java.util.Random;
 
public class Ball {
    RectF rect;
    float xVelocity;
    float yVelocity;
    float ballWidth = 10;
    float ballHeight = 10;
 
    public Ball(){
        xVelocity = 200 + new Random().nextInt(50);
        yVelocity = -400 + new Random().nextInt(50);
        randomXVelocity();
        rect = new RectF();
    }
 
    public RectF getRect(){
        return rect;
    }
    
    public int getHeight(){
        return (int) ballHeight;
    }
 
    public void update(long fps){
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
    }
 
    public void reverseYVelocity(){
        yVelocity = -yVelocity + new Random().nextInt(50);
    }
 
    public void reverseXVelocity(){
        xVelocity = -xVelocity + new Random().nextInt(50);
    }
    
    private void randomXVelocity(){
    	if(new Random().nextInt(2) == 1){
    		reverseXVelocity();
    	}
    }
 
    public void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }
 
    public void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;
    }
 
    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 20;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 20 - ballHeight;
        xVelocity = 200 + new Random().nextInt(50);
        yVelocity = -400 + new Random().nextInt(50);
        randomXVelocity();
    }
}