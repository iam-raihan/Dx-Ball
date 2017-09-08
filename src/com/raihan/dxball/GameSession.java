package com.raihan.dxball;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class GameSession {
	private int curScore;
	
	private int maxLife;
	private int curLife;
	
	private int maxLevel;
	private int curLevel;
		
	private HighScore highScoreObj;
	private Settings settings;
	
	public GameSession(Context context){
		highScoreObj = new HighScore(context);
		settings = new Settings();
		
		curScore = 0;
		
		maxLife = settings.getMaxLife();
		curLife = maxLife;
		
		maxLevel = settings.getMaxLevel();
		curLevel = 1;
	}
	
	public int getCurScore(){
		return curScore;
	}
	
	public int getCurLife(){
		return curLife;
	}

	public int getCurLevel(){
		return curLevel;
	}
	
	public int getHighScore(){
		return highScoreObj.getHighScore();
	}

	public void scoreUp(){
		curScore += 10;
	}
	
	public void scoreDown(){
		curScore -= 5;
	}
	
	public void levelUp(){
		curLevel++;
		int bonus = 0;
		while(curLife > 1){
			bonus += 50;
			curLife--;
		}
		Util.showMessage("Bonus for extra life: " + bonus);
		curScore += bonus;
		curLife = maxLife;
	}
	
	public void lifedown(){
		curLife--;
	}
	
	public boolean checkLife(){
		return curLife <= 0;
	}
	
	public boolean checkLevel(){
		return curLevel > maxLevel;
	}
	
	public void reset(boolean isComplete){
		String msg;
		if(isComplete)
			msg = "You Won!! Score:";
		else
			msg = "You Lost!! Score:";
		Util.showMessage(msg + curScore);
		highScoreObj.setHighScore(curScore);
		curScore = 0;
		curLife = maxLife;
		curLevel = 1;
	}

	public int getCurBackColor() {
		return settings.getBackColor(3 - curLife);
	}

	public Box[] getBricks(int screenX){
		int[][] curLayout = settings.getLayout(curLevel);
		int boxSize = screenX / 8;
        List<Box> boxList = new ArrayList<Box>();
        
        for(int row = 0; row < 8; row ++ ){
             for(int column = 0; column < 5; column ++ ){
                  if (curLayout[column][row] == 1) {
                	  boxList.add(new Box(
                			  column, 
	                		  row, 
	                		  boxSize, 
	                		  boxSize)
                	  );
                  }
             }
        }
		return boxList.toArray(new Box[boxList.size()]);
	}
}
