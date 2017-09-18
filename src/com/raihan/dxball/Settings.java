package com.raihan.dxball;

import android.graphics.Color;

public class Settings {
	
	/*
	 *  set max life user can get for each level
	 */
	protected final int maxLife = 3;
	
	/*
	 *  '1' for Box and '0' for empty space in layout. 
	 *  Add new 2D array element of [5,8] dimension and 
	 *  a free brand new level will be added in game. 
	 *  No need to adjust code anywhere else for new level.
	 */
	protected final int[][][] layout = {
			{
				{ 0, 0, 1, 1, 1, 1, 0, 0},
				{ 0, 1, 0, 0, 0, 0, 1, 0},
				{ 1, 0, 0, 1, 1, 0, 0, 1},
				{ 0, 1, 0, 0, 0, 0, 1, 0},
				{ 0, 0, 1, 1, 1, 1, 0, 0}
			},
			{
				{ 1, 1, 1, 0, 0, 1, 1, 1},
				{ 1, 0, 1, 1, 1, 1, 0, 1},
				{ 1, 1, 1, 0, 0, 1, 1, 1},
				{ 1, 0, 1, 1, 1, 1, 0, 1},
				{ 1, 1, 1, 0, 0, 1, 1, 1}
			},
			{ 
				{ 1, 0, 0, 0, 0, 0, 0, 1},
				{ 0, 0, 0, 0, 0, 0, 0, 0},
				{ 0, 0, 0, 1, 1, 0, 0, 0},
				{ 0, 0, 0, 0, 0, 0, 0, 0},
				{ 1, 0, 0, 0, 0, 0, 0, 1} 
			}
		};
	
	/*
	 *  set bonus score for extra life for each levelUp
	 */
	protected final int bonusForExtraLife = 50;
	
	protected int getMaxLife(){
		return maxLife;
	}
	
	protected int getMaxLevel(){
		return layout.length;
	}
	
	protected int[][] getLayout(int lavel){
		return layout[lavel - 1];
	}
	
	protected int getBackColor(int life){
		if( life == 0){
			return Color.argb(255,  46, 204, 113); //green
		}
		if( life == 1){
			return Color.argb(255, 107, 185, 240); //blue
		}
		return Color.argb(255,  231, 76, 60); //red
	}
}
