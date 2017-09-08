package com.raihan.dxball;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class Util {
	private static Context context;
	private static Handler handler = new Handler();
	
	public static void setContext(Context context) {
		Util.context = context;
	}
	
	public static void showMessage(final String msg) {
		handler.post(new Runnable(){
		    public void run(){
		        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		    }
		});
	}
}
