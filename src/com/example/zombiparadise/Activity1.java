package com.example.zombiparadise;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.app.NavUtils;

public class Activity1 extends Activity {
	SharedPreferences sharedPreferences;
	GameView gameView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("Score", MODE_PRIVATE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        gameView = new GameView(this); 
        setContentView(gameView);        
        DisplayMetrics dm = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay(); 
        display.getMetrics(dm);
        gameView.setMetrics(dm);
        startService(new Intent(this, MyService.class)); 

    }
    public boolean onKeyDown( int keyCode, KeyEvent event) 
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        {
        	stopService(new Intent(this, MyService.class));	
        	finish();
            //canvas.drawColor(0xff000000);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) 
        {
        	stopService(new Intent(this, MyService.class));	
        	finish();
            //canvas.drawColor(0xff000000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public boolean onTouchEvent(MotionEvent e) 
    {
    	
        if(gameView.GetHome() == true)
        {
        stopService(new Intent(this, MyService.class));	
        finish();
        }    	
        Log.i("fd", "dh");
        if((e.getAction() == MotionEvent.ACTION_DOWN) | (e.getAction() == MotionEvent.ACTION_MOVE))
    	{
    		if(( gameView.GetX()!= (int) e.getX()) & (gameView.GetY() != (int) e.getY()))
    		{
    		gameView.SetX((int) e.getX());
    		gameView.SetY((int) e.getY());
    		gameView.SetMove(true);
    		}
    	}
    	if(gameView.GetScore() > sharedPreferences.getInt("Score",0))
    	{
    		SavePreferences("Score", gameView.GetScore());
    	}
    	if(e.getAction() == MotionEvent.ACTION_UP)
    		gameView.SetMove(false);

		return true;
    	 
    }
    private void SavePreferences(String key, int value){
        //SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
       }
    public int GetHighscore()
    {
    	return sharedPreferences.getInt("Score",0);
    }

}
