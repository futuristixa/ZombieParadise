package com.example.zombiparadise;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Zombi extends GameObject {
	int xMouse = 0;
    int yMouse = 0;
    int speed = 0;
    int rotation = 2;
    boolean move = false;
    Bitmap picture1;
    Bitmap picture2;
    Bitmap picture3;
    Bitmap picture4;
    Bitmap picture5;
    Bitmap picture6;
    Bitmap picture7;
    private int currentFrame = 0;
    Rect rect;
	public Zombi(GameView gameView,
	Bitmap bmp,
    Bitmap picture1,
    Bitmap picture2,
    Bitmap picture3,
    Bitmap picture4,
    Bitmap picture5,
    Bitmap picture6,
    Bitmap picture7)
	{
		this.gameView = gameView;
		this.bmp = picture1;
		this.picture1 = picture1;
		this.picture2 = picture2;
		this.picture3 = picture3;
		this.picture4 = picture4;
		this.picture5 = picture5;
		this.picture6 = picture6;
		this.picture7 = picture7;	   
		x = 0;

		y = gameView.getHeight() - bmp.getHeight();

		
	}
	
	public void update()
	{
		speed = gameView.getWidth() / 25; 
		//speed = 40;
		//x += 5;
		if(move == true)
			{
			xMouse = gameView.x;
			yMouse = gameView.y;	
			if(x > xMouse -bmp.getWidth()/2)
				{
				x-=speed;
				rotation = 3;
				}
			if(x < xMouse-bmp.getWidth()/2)
				{
				x+=speed;
				rotation = 1;
				}
			}
		else
			rotation = 2;
		y = gameView.getHeight() - bmp.getHeight();
	    
		if(x < 0)
  		{
  			x=0; 
  		}
    	if(x > gameView.getWidth()- bmp.getWidth())
    	{
    		x=gameView.getWidth()- bmp.getWidth();
    	}
    	rect = new Rect(x,y,x+bmp.getWidth(),y+bmp.getHeight());
    	currentFrame = ++currentFrame % 3;
	}
	public void onDraw(Canvas canvas)
	{
		update();

		
        
		if(rotation == 2)
			canvas.drawBitmap(picture7, x, y, null);                    	
        if(rotation == 1)
        	{
        		if(currentFrame ==0)
        			canvas.drawBitmap(picture4, x, y, null);
        		if(currentFrame ==1)
        			canvas.drawBitmap(picture5, x, y, null);
        		if(currentFrame ==2)
        			canvas.drawBitmap(picture6, x, y, null);
        	}
        if(rotation == 3)
        	{
        		if(currentFrame ==0)
        			canvas.drawBitmap(picture1, x, y, null);
        		if(currentFrame ==1)
        			canvas.drawBitmap(picture2, x, y, null);
        		if(currentFrame ==2)
        			canvas.drawBitmap(picture3, x, y, null);
        	}
	}
	public void SetMove(boolean m)
	{
		move = m;
	}
	public Rect getRect()
	{
		return rect;
	}
	public void setX(int a)
	{
		x = a;
	}
}
