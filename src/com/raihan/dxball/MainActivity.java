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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
    
	 class MainView extends SurfaceView implements Runnable, SensorEventListener {
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
	     int brickTotal, brickDone;
	     
	     Sensor sensor;
	     SensorManager sensorManager;
	     
	     SoundPool soundPool;
	     int beep1ID = -1, beep2ID = -1, beep3ID = -1, loseLifeID = -1, explodeID = -1;
	     
	     Settings settings;
	     int score, lives, lavel, highScore;
	     int[][][] layout;
	     HighScore highScoreObj;
	
	     public MainView(Context context) {
	         super(context);
	
	         ourHolder = getHolder();
	         paint = new Paint();
	         
	         Display display = getWindowManager().getDefaultDisplay();
	         screenX = display.getWidth();
	         screenY = display.getHeight();
	         
	         paddle = new Paddle(screenX, screenY);
	         ball = new Ball();
	         
	         settings = new Settings();
	         
	         score = 0;
	         lavel = 0;
	         
	         highScoreObj = new HighScore(context);
	         highScore = highScoreObj.getHighScore();
	         
	         layout = settings.getLayout();
	         
	         loadMusic(context);
	         
    		 createBricksAndRestart();
	     }
	     
	     public void createBricksAndRestart(){
	         ball.reset(screenX, screenY);
	         paddle.reset(screenX);
	         int brickWidth = screenX / 8;
	         int brickHeight = screenY / 10;
	         brickTotal = 0;
	         brickDone = 0;
	         for(int column = 0; column < 8; column ++ ){
	              for(int row = 0; row < 5; row ++ ){
	                   if (layout[lavel][row][column] == 1) {
	                	   bricks[brickTotal] = new Brick(
		                		   row, 
		                		   column, 
		                		   brickWidth, 
		                		   brickHeight);
	                	   brickTotal ++;
	                   }
	              }
	         }
	         
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
	    	 paddle.update(fps, screenX);
	    	 ball.update(fps);
	    	 
	    	 // Check for ball colliding with a brick
	    	 for(int i = 0; i < brickTotal; i++){
	    		 if (bricks[i].getVisibility() == 1){
	    			 if(RectF.intersects(bricks[i].getRect(), ball.getRect())) {
	    				 bricks[i].setInvisible();
	    				 ball.reverseYVelocity();
	    				 score += 10;
	    				 brickDone++;
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
	    	     score -= 5;
	    	 }
	    	 
	    	 // Check for ball colliding with bottom of screen
	    	 if(ball.getRect().bottom > screenY){
	    		 ball.reverseYVelocity();
	    	     ball.clearObstacleY(screenY - 2);
	    	     lives --;
	    	     soundPool.play(loseLifeID, 1, 1, 0, 0, 1);
	    	     if(lives == 0){
	    	    	 lavel = 0;
	    	    	 paused = true;
	    	    	 if (score > highScore) {
			    		 highScoreObj.setHighScore(score);
			    		 highScore = highScoreObj.getHighScore();
		    		 }
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
	    	 if(brickDone == brickTotal){
	    		 lavel++; 
	    		 if (lavel == 3)
		        	 lavel = 0;
	    		 paused = true;
	    		 createBricksAndRestart();
	    	 }
	     }
	
	     public void draw() {
	         if (ourHolder.getSurface().isValid()) {
	             canvas = ourHolder.lockCanvas();
	             canvas.drawColor(settings.getBackColor(3 - lives));
	             
	             paint.setColor(Color.argb(255,  255, 255, 255)); // for ball and paddle
	             canvas.drawRect(paddle.getRect(), paint);	
	             canvas.drawRect(ball.getRect(), paint);
	             
	             paint.setColor(Color.argb(255,  249, 129, 0)); // for bricks
	             for(int i = 0; i < brickTotal; i++){
	                  if(bricks[i].getVisibility() == 1) {
	                       canvas.drawRect(bricks[i].getRect(), paint);
	                  }
	             }

	             paint.setColor(Color.argb(255, 0, 0, 0));
	             paint.setTextSize(30);
	             canvas.drawText(
	            		 "Score: " + score + 
	            		 "  Lives: " + lives +
	            		 "  Lavel: " + (lavel + 1) +
	            		 "  High Score: " + highScore , 10, 50, paint);
	              
	             if(brickDone == brickTotal && lavel == 2){
	                  paint.setTextSize(90);
	                  canvas.drawText("YOU HAVE WON!", 10, screenY/2, paint);
	                  score = 0;
	                  lavel = 0;
	             }
	              
	             if(lives <= 0){
	                  paint.setTextSize(90);
	                  canvas.drawText("YOU HAVE LOST!", 10, screenY/2, paint);
	                  score = 0;
	                  lavel = 0;
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
	         
	    	 if (sensorManager != null) {
	    		 sensorManager.unregisterListener(this);
	    		 sensorManager = null;
             }
	     }
	     
	     public void resume() {
	         playing = true;
	         gameThread = new Thread(this);
	         gameThread.start();
	         
	         if (sensorManager == null){
		         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		         sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	         }
	         sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
	     }

	     @Override
	     public boolean onTouchEvent(MotionEvent motionEvent) {	
	         switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
	             case MotionEvent.ACTION_DOWN:
	                 paused = !paused;
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

		 public void onSensorChanged(SensorEvent arg0) {
			 float x = arg0.values[SensorManager.DATA_X];

			 if(x < -1)
				 paddle.setMovementState(paddle.RIGHT);
			 else if(x > 1)
				 paddle.setMovementState(paddle.LEFT);
			 else
				 paddle.setMovementState(paddle.STOPPED);
		 }

		public void onAccuracyChanged(Sensor arg0, int arg1) {
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