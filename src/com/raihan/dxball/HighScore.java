package com.raihan.dxball;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.widget.Toast;

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
			this.highScore = score;
			scoreFile.WriteFile(score);
		}
	}
	
	public class ScoreFile{
		private String FILENAME = "HighScore.txt";
		private String FILE_READ_FAILED = "Failed to read High Score";
		private String FILE_WRITE_SUCCESS = "New High Score :";
		private String FILE_WRITE_FAILED = "Failed to write High Score";
		
		private int ReadFile() {
			FileInputStream fis = null;
			
			try {
				fis = context.openFileInput(FILENAME);
			} catch (FileNotFoundException e) {
				return 0;
			}
			
			InputStreamReader inputStreamReader = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		
//			StringBuilder sb = new StringBuilder();
			String line;
		
			try {
//				while ((line = bufferedReader.readLine()) != null) {
//					sb.append(line);
//				}
				line = bufferedReader.readLine();
			}catch (IOException e) {
				Toast.makeText(context, FILE_READ_FAILED, Toast.LENGTH_SHORT).show();
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
				Toast.makeText(context, FILE_WRITE_FAILED, Toast.LENGTH_SHORT).show();
				return;
			}
			
			try {
				fos.write(String.valueOf(score).getBytes());
				fos.flush();
			} catch (IOException e) {
				Toast.makeText(context, FILE_WRITE_FAILED, Toast.LENGTH_SHORT).show();
				return;
			}
			
			try {
				fos.close();
			} catch (IOException e) {
			}
			
			Toast.makeText(context, FILE_WRITE_SUCCESS + score, Toast.LENGTH_SHORT).show();
		}
	}
}
