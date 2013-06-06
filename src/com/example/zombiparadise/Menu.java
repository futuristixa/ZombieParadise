package com.example.zombiparadise;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Menu extends GameObject{
	int score;
	int xMouse = 0;
    int yMouse = 0;
	Rect rectStart;
	Rect rectRestart;
	Rect rectHome;
	Rect rectMouse;
	Rect rectPause;
	int live;
	
	Bitmap menu;
    Bitmap restart;
    Bitmap start;
    Bitmap pause;
    Bitmap home;
    
	boolean NewGame = false;
	boolean OldGame = false;
	boolean Pause = false;
	boolean Home = false;
	public  Menu(GameView gameView, Bitmap bmp1,Bitmap bmp2,Bitmap bmp3,Bitmap bmp4,Bitmap bmp5)
	{
		this.gameView = gameView;
		this.home = bmp1;
		this.menu = bmp2;
		this.start = bmp3;
		this.restart = bmp4;
		this.pause = bmp5;
	}
	public void update()
	{					
		xMouse = gameView.x;
		yMouse = gameView.y;	
		rectMouse = new Rect(xMouse,yMouse,xMouse+1,yMouse+1);
		rectStart = new Rect(
				gameView.getWidth()/6 + start.getWidth()/2, 
    			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1),
    			gameView.getWidth()/6 + start.getWidth()/2 + start.getWidth(), 
    			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1) + start.getHeight());    
		rectRestart = new Rect(gameView.getWidth()/6 + menu.getWidth()/2 - start.getWidth()/2 , 
    			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1),
    			gameView.getWidth()/6 + menu.getWidth()/2 - start.getWidth()/2 + start.getWidth(), 
    			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1) + start.getHeight());
				
		rectHome = new Rect(gameView.getWidth()/6 + menu.getWidth() - 3*(start.getWidth()/2) , 
    			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1) ,
    			gameView.getWidth()/6 + menu.getWidth() - 3*(start.getWidth()/2) + start.getWidth(), 
    			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1) + start.getHeight());
		rectPause =new Rect(gameView.getWidth()/2 - pause.getWidth()/2,
				0,gameView.getWidth()/2 - pause.getWidth()/2 + pause.getWidth(),
				0 + pause.getHeight()); 
			if((rectStart.intersect(rectMouse)) & (gameView.game == false))
			{
			OldGame = true;
			}
			if((rectRestart.intersect(rectMouse)) & (gameView.game == false))
			{
			NewGame = true;
			}
			if(rectPause.intersect(rectMouse))
			{
			Pause = true;
			}
			if((rectHome.intersect(rectMouse))& (gameView.game == false))
			{
			Home = true;
			}
								
			
	}
	public void onDraw(Canvas c,int a)
	{     
        
		this.live = a;
		update();
        c.drawBitmap(pause,gameView.getWidth()/2 - pause.getWidth()/2,0, null);
        if(gameView.game == false)
        {
        	
        	//c.drawBitmap(menu,gameView.getWidth()/6 , gameView.getHeight()/6 ,null);
        	c.drawBitmap(start,gameView.getWidth()/6 + start.getWidth()/2, 
        			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1)  ,null);
        	c.drawBitmap(restart,gameView.getWidth()/6 + menu.getWidth()/2 - start.getWidth()/2 , 
        			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1) ,null);
        	//if((rectStart.intersect(rectMouse)) & (gameView.game == false))
        	c.drawBitmap(home,gameView.getWidth()/6 + menu.getWidth() - 3*(start.getWidth()/2) , 
        			(gameView.getHeight()/6) +(menu.getHeight() / 16) +(menu.getHeight()/4*1) ,null);
        }
	}
	public boolean GetNewGame()
	{
		return NewGame;
	}
	public boolean GetOldGame()
	{
		return OldGame;
	}
	public boolean GetPause()
	{
		return Pause;
	}
	public void setNewGame(boolean a)
	{
		NewGame = a;
	}
	public void setOldGame(boolean a)
	{
		OldGame = a;
	}
	public void setPause(boolean a)
	{
		Pause = a;
	}
	public boolean getHome()
	{
		return Home ;
	}
	public void setHome(boolean a)
	{
		Home = a;
	}
}
