package com.example.zombiparadise;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cloud extends GameObject{
	int score;
	Bitmap heart;
	Bitmap blackheart;
	Bitmap Brain;
	int live;
	boolean j = true;
	public  Cloud(GameView gameView, Bitmap bmp , Bitmap a,Bitmap b,Bitmap c)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		this.heart = a;
		this.blackheart = b;
		this.Brain = c;
		x = 0;
		y = 0 ;
	}
	public void update()
	{
		if((score%10 == 0) & (j = true))
		{
			live++;
			j = false;
		}
		if(live > 5 )
			live = 5;
	}
	public void onDraw(Canvas c)
	{
		//update();
		Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        paint1.setTextSize(40);
        
        //c.drawText(, 10, 70, paint1);
        
		c.drawBitmap(bmp, x, y, null);
			
		for (int i = 0;i<5;i++)
		{
			c.drawBitmap(blackheart, (gameView.getWidth()-heart.getWidth()) - i*(heart.getWidth() + heart.getWidth()/4),0, null);
		}
		for (int i = 0;i<live;i++)
		{
			c.drawBitmap(heart, (gameView.getWidth()-heart.getWidth()) - i*(heart.getWidth() + heart.getWidth()/4),0, null);
		}
		c.drawBitmap(Brain, 0, 0,null);
		c.drawText(" " +score,Brain.getHeight(),Brain.getHeight(),paint1);
	}
	public void setScore(int a)
	{
		j = true;
		score = a;
		
	}
	public void setLive(int a)
	{
		live = a;
	}
}
