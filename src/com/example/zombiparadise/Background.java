package com.example.zombiparadise;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends GameObject{
	
	public  Background(GameView gameView, Bitmap bmp)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		x = 0;
		y = 0;
	}
	
	public void onDraw(Canvas c)
	{
		c.drawBitmap(bmp, x, y, null);
	}

}
