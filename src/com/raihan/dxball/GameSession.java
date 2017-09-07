package com.raihan.dxball;

import android.content.Context;

public class GameSession {
	private int curScore;
	
	private int maxLife;
	private int curLife;
	
	private int maxLavel;
	private int curLavel;
		
	private HighScore highScoreObj;
	private Settings settings;
	
	public GameSession(Context context){
		highScoreObj = new HighScore(context);
		settings = new Settings();
		
		curScore = 0;
		
		maxLife = settings.getMaxLife();
		curLife = maxLife;
		
		maxLavel = settings.getMaxLavel();
		curLavel = 1;
	}
	
	public int getCurScore(){
		return curScore;
	}
	
	public int getCurLife(){
		return curLife;
	}

	public int getCurLavel(){
		return curLavel;
	}
	
	public int getHighScore(){
		return highScoreObj.getHighScore();
	}

	public int[][] getCurLayout(){
		return settings.getLayout(curLavel);
	}
	
	public void scoreUp(){
		curScore += 10;
	}
	
	public void scoreDown(){
		curScore -= 5;
	}
	
	public void lavelUp(){
		curLavel++;
		curLife = maxLife;
	}
	
	public void lifedown(){
		curLife--;
	}
	
	public boolean checkLife(){
		return curLife <= 0;
	}
	
	public boolean checkLavel(){
		return curLavel > maxLavel;
	}
	
	public void reset(){
		highScoreObj.setHighScore(curScore);
		curScore = 0;
		curLife = maxLife;
		curLavel = 1;
	}

	public int getCurBackColor() {
		return settings.getBackColor(3 - curLife);
	}
}
