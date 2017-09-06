package com.raihan.dxball;

import android.graphics.Color;

public class Settings {
	private int lavelCount;
	
	private int[][][] layout = {
			{
				{ 0, 0, 1, 1, 1, 1, 0, 0},
				{ 0, 1, 0, 0, 0, 0, 1, 0},
				{ 1, 0, 0, 1, 1, 0, 0, 1},
				{ 0, 1, 0, 0, 0, 0, 1, 0},
				{ 0, 0, 1, 1, 1, 1, 0, 0}
			},
			{
				{ 1, 1, 1, 0, 0, 1, 1, 1},
				{ 1, 1, 1, 0, 0, 1, 1, 1},
				{ 1, 1, 1, 0, 0, 1, 1, 1},
				{ 1, 1, 1, 0, 0, 1, 1, 1},
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
	
	public Settings(){
		lavelCount = layout.length;;
	}
	
	public int[][][] getLayout(){
		return layout;
	}
	
	public int getBackColor(int life){
		if( life == 0){
			return Color.argb(255,  46, 204, 113); //green
		}
		if( life == 1){
			return Color.argb(255,  26, 128, 182); //blue
		}
		return Color.argb(255,  231, 76, 60); //red
	}

	

}
