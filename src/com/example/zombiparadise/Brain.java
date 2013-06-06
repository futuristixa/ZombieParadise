package com.example.zombiparadise;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.SoundPool;

public class Brain extends GameObject{
	boolean alive = false;
	int speed = 0;
	Rect rect;
	boolean intersects;
	int live = 5;
	private SoundPool sounds;
	private int sExplosion;
	public Brain(GameView gameView, Bitmap bmp)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		x = 0;
		y = 0;
		
	}
	public void update()
	{
		//speed = gameView.getHeight()/30;
		speed =gameView.getWidth() / 40; 
		Random rnd = new Random();
		if(intersects == true)
    	{
    		alive = false;
    		intersects = false;
    	}
		if(alive == false)
    	{
    		x = rnd.nextInt(gameView.getWidth() - bmp.getWidth());
			//x = 30;
    		alive = true;
    		y = 0;
    	}
    	if(alive == true)
    	{
    		y += speed;
    	}	
    	if((y > gameView.getHeight()-bmp.getHeight() ) & (alive !=false))          	
    	{
    		alive = false;
    		live--;	
    		
    	}	
    	
    	rect = new Rect(x,y,x+bmp.getWidth(),y+bmp.getHeight());
   	
    	
	}
	public void onDraw(Canvas c)
	{
		update();
		c.drawBitmap(bmp, x, y, null);
	}
	public Rect getRect()
	{
		return rect;
	}
	public void setInersects(boolean a)
	{	
		intersects = a;
	}
	public int getLive()
	{
		return live;
	}
	public void setLive(int a)
	{
		live = a;
	}
	public void setALive(boolean a)
	{
		alive = a;
	}
}
