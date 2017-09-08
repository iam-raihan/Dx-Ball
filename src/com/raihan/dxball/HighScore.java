package com.raihan.dxball;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class HighScore {
	private int highScore;
	private ScoreFile scoreFile;
	private Context context;
	
	public HighScore(Context context) {
		this.context = context;
		scoreFile = new ScoreFile();
		highScore = scoreFile.ReadFile();
	}
	
	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int score) {
		if (this.highScore < score) {
			scoreFile.WriteFile(score);
			Util.showMessage("New High Score : " + highScore);
		}
	}
	
	private class ScoreFile{
		private final String FILENAME = "HighScore.txt";
		
		private int ReadFile() {
			FileInputStream fis = null;
			
			try {
				fis = context.openFileInput(FILENAME);
			} catch (FileNotFoundException e) {
				return 0;
			}
			
			InputStreamReader inputStreamReader = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
			String line;
		
			try {
				line = bufferedReader.readLine();
			}catch (IOException e) {
				return 0;
			}			
		
			try {
				bufferedReader.close();
				inputStreamReader.close();
				fis.close();
			} catch (IOException e) {
			}
			
			return new Integer(line);
		}
		
		private void WriteFile(int score) {
			FileOutputStream fos = null;
			
			try {
				fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			} catch (FileNotFoundException e) {
				return;
			}
			
			try {
				fos.write(String.valueOf(score).getBytes());
				fos.flush();
			} catch (IOException e) {
				return;
			}
			
			try {
				fos.close();
			} catch (IOException e) {
			}
			
			highScore = score;
		}
	}
}
