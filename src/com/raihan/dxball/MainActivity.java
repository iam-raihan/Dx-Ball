package com.raihan.dxball;

import java.io.IOException;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {
	MainView mainView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mainView = new MainView(this);
        setContentView(mainView);
    }
    
	 class MainView extends SurfaceView implements Runnable {
	     Thread gameThread = null;
	     SurfaceHolder ourHolder;
	
	     volatile boolean playing; //will never be cached thread-locally
	     boolean paused = true;
	
	     Canvas canvas;
	     Paint paint;
	
	     long fps;
	     private long timeThisFrame;
	
	     int screenX, screenY;
	     
	     Paddle paddle;
	     Ball ball;
	     Brick[] bricks = new Brick[200];
	     int numBricks = 0;
	     
	     SoundPool soundPool;
	     int beep1ID = -1, beep2ID = -1, beep3ID = -1, loseLifeID = -1, explodeID = -1;
	      
	     int score = 0, lives = 3;
	
	     public MainView(Context context) {
	         super(context);
	
	         ourHolder = getHolder();
	         paint = new Paint();
	         
	         Display display = getWindowManager().getDefaultDisplay();
	         screenX = display.getWidth();
	         screenY = display.getHeight();
	         
	         paddle = new Paddle(screenX, screenY);
	         ball = new Ball();
	         
	         loadMusic(context);
	         
	         createBricksAndRestart();
	     }
	     
	     public void createBricksAndRestart(){
	         ball.reset(screenX, screenY);
	         
	         int brickWidth = screenY / 10;
	         int brickHeight = screenX / 8;
	         numBricks = 0;
	         for(int column = 0; column < 8; column ++ ){
	              for(int row = 0; row < 3; row ++ ){
	                   bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
	                   numBricks ++;
	              }
	         }
	         score = 0;
	         lives = 3;
	     }
	
	     public void run() {
	         while (playing) {
	             long startFrameTime = System.currentTimeMillis();
	
	             if(!paused){
	                  update();
	             }
	             draw();
	             timeThisFrame = System.currentTimeMillis() - startFrameTime;
	             if (timeThisFrame >= 1) {
	                 fps = 1000 / timeThisFrame;
	             }
	         }
	     }

	     public void update() {
	    	 paddle.update(fps);
	    	 ball.update(fps);
	    	 
	    	 // Check for ball colliding with a brick
	    	 for(int i = 0; i < numBricks; i++){
	    		 if (bricks[i].getVisibility()){
	    			 if(RectF.intersects(bricks[i].getRect(), ball.getRect())) {
	    				 bricks[i].setInvisible();
	    				 ball.reverseYVelocity();
	    				 score += 10;
	    				 soundPool.play(explodeID, 1, 1, 0, 0, 1);
	    			 }
	    		 }
	    	 }
	    	 
	    	 // Check for ball colliding with paddle
	    	 if(RectF.intersects(paddle.getRect(), ball.getRect())) {
	    		 ball.setRandomXVelocity();
	    	     ball.reverseYVelocity();
	    	     ball.clearObstacleY(paddle.getRect().top - 2);
	    	     soundPool.play(beep1ID, 1, 1, 0, 0, 1);
	    	 }
	    	 
	    	 // Check for ball colliding with bottom of screen
	    	 if(ball.getRect().bottom > screenY){
	    		 ball.reverseYVelocity();
	    	     ball.clearObstacleY(screenY - 2);
	    	     lives --;
	    	     soundPool.play(loseLifeID, 1, 1, 0, 0, 1);
	    	     if(lives == 0){
	    	          paused = true;
	    	          createBricksAndRestart();
	    	     }
		 	 }
	    	 
	    	 // Check for ball colliding with top of screen
	    	 if(ball.getRect().top < 0){
	    	      ball.reverseYVelocity();
	    	      ball.clearObstacleY(ball.getHeight() + 2);
	    	      soundPool.play(beep2ID, 1, 1, 0, 0, 1);
	    	 }
	    	 
	    	 // Check for ball colliding with left of screen
	    	 if(ball.getRect().left < 0){
	    	      ball.reverseXVelocity();
	    	      ball.clearObstacleX(2);
	    	      soundPool.play(beep3ID, 1, 1, 0, 0, 1);
	    	 }
	    	 
	    	 // Check for ball colliding with right of screen
	    	 if(ball.getRect().right > screenX - 10){
	    	      ball.reverseXVelocity();
	    	      ball.clearObstacleX(screenX - 22);
	    	      soundPool.play(beep3ID, 1, 1, 0, 0, 1);
	    	 }
	    	 
	    	 // Pause if cleared screen
	    	 if(score == numBricks * 10){
	    	      paused = true;
	    	      createBricksAndRestart();
	    	 }
	     }
	
	     public void draw() {
	         if (ourHolder.getSurface().isValid()) {
	             canvas = ourHolder.lockCanvas();
	             canvas.drawColor(Color.argb(255,  26, 128, 182));
	             
	             paint.setColor(Color.argb(255,  255, 255, 255)); // for ball and paddle
	             canvas.drawRect(paddle.getRect(), paint);	
	             canvas.drawRect(ball.getRect(), paint);
	             
	             paint.setColor(Color.argb(255,  249, 129, 0)); // for bricks
	             for(int i = 0; i < numBricks; i++){
	                  if(bricks[i].getVisibility()) {
	                       canvas.drawRect(bricks[i].getRect(), paint);
	                  }
	             }

	             paint.setColor(Color.argb(255, 0, 0, 0));
	             paint.setTextSize(20);
	             canvas.drawText(
	            		 "Score: " + score + 
	            		 "  Lives: " + lives +
	            		 "  High Score: " + score +
	            		 "  Lavel: " + lives , 10, 50, paint);
	              
	             if(score == numBricks * 10){
	                  paint.setTextSize(90);
	                  canvas.drawText("YOU HAVE WON!", 10, screenY/2, paint);
	             }
	              
	             if(lives <= 0){
	                  paint.setTextSize(90);
	                  canvas.drawText("YOU HAVE LOST!", 10, screenY/2, paint);
	             }
	             
	             ourHolder.unlockCanvasAndPost(canvas);
	         }	
	     }
	
	     public void pause() {
	         playing = false;
	         try {
	             gameThread.join();
	         } catch (InterruptedException e) {
	             Log.e("Error:", "joining thread");
	         }
	     }
	     
	     public void resume() {
	         playing = true;
	         gameThread = new Thread(this);
	         gameThread.start();
	     }

	     @Override
	     public boolean onTouchEvent(MotionEvent motionEvent) {	
	         switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
	             case MotionEvent.ACTION_DOWN:
	                 paused = false;
	                 if(motionEvent.getX() > screenX / 2){
	                     paddle.setMovementState(paddle.RIGHT);
	                 }
	                 else{
	                     paddle.setMovementState(paddle.LEFT);
	                 }
	                 break;
	             case MotionEvent.ACTION_UP:
	                 paddle.setMovementState(paddle.STOPPED);
	                 break;
	         }
	         return true;
	     }
	     
	     private void loadMusic(Context context) {
			 soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
			 try{
				 AssetManager assetManager = context.getAssets();
			     AssetFileDescriptor descriptor;  
		         descriptor = assetManager.openFd("beep1.ogg");
		         beep1ID = soundPool.load(descriptor, 0);
		         descriptor = assetManager.openFd("beep2.ogg");
		         beep2ID = soundPool.load(descriptor, 0);
		         descriptor = assetManager.openFd("beep3.ogg");
		         beep3ID = soundPool.load(descriptor, 0);
		         descriptor = assetManager.openFd("loseLife.ogg");
		         loseLifeID = soundPool.load(descriptor, 0);
		         descriptor = assetManager.openFd("explode.ogg");
		         explodeID = soundPool.load(descriptor, 0);
	         }catch(IOException e){
	        	 Log.e("error", "failed to load sound files");
        	 }			
		}
	 }

	 @Override
	 protected void onResume() {
	     super.onResume();
	     mainView.resume();
	 }
	
	 @Override
	 protected void onPause() {
	     super.onPause();
	     mainView.pause();
	 }
 }