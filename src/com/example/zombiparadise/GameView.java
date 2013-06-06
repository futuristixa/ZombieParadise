package com.example.zombiparadise;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable
{
	/**Объект класса GameLoopThread*/
	Activity1 act = new Activity1();
	private GameThread mThread;		
	private Thread thred = new Thread(this);
	private SoundPool sounds;
	private int sExplosion;
	private SoundPool sounds2;
	private int sExplosion2;
	private Zombi zombi;
	private Brain brain;
	private Background background;
	private Cloud cloud;
	private Menu menu;
	//Activity1 act1;	
    static int width=0;
    static int height=0;
	private Bitmap bmp;
    Bitmap picture1;
    Bitmap picture2;
    Bitmap picture3;
    Bitmap picture4;
    Bitmap picture5;
    Bitmap picture6;
    Bitmap picture7;
    Bitmap pictureBrain;
    Bitmap picturebackground;
    Bitmap picturecloud;
    Bitmap heart;
    Bitmap blackheart;
    Bitmap restart;
    Bitmap start;
    Bitmap pause;
    Bitmap home;
    Bitmap menuphon;
    int score = 0;
	public int x = 0;
    public int y = 0; 
    boolean game=true;
    int live = 5;
    public boolean move;
    boolean Home;
    static boolean intersect;
    
    DisplayMetrics displayMetrics;
    /**Переменная запускающая поток рисования*/
    private boolean running = false;
	
  //-------------Start of GameThread--------------------------------------------------\\
    
	public class GameThread extends Thread
	{
		/**Объект класса*/
	    private GameView view;	 
	    
	    /**Конструктор класса*/
	    public GameThread(GameView view) 
	    {
	          this.view = view;
	    }

	    /**Задание состояния потока*/
	    public void setRunning(boolean run) 
	    {
	          running = run;
	    }

	    /** Действия, выполняемые в потоке */
	    public void run()
	    {
	    	//int lol=1;
	        while (running)
	        {
	            Canvas canvas = null;
	            try
	            {
	                // подготовка Canvas-а
	                canvas = view.getHolder().lockCanvas();
	                synchronized (view.getHolder())
	                {
	                	
	                    // собственно рисование
	                    onDraw(canvas);
	                    
	                     if((live == 0)&(game == true))//&( lol==1 ))
	                     {
	                    	 sounds2.play(sExplosion2, 1.0f, 1.0f, 0, 0, 1.5f);
	                    	 //lol=2;
	                    	 game = false; 
	                     }
	                     if ((testCollision(zombi.getRect(),brain.getRect()) == true) & (game == true)) 
	                     {
	                    	 intersect = true;
	                    	 brain.setALive(false);
	                    	 brain.setInersects(intersect);
	                    	 
	                    	  
	                    	 score++;
	                    	 cloud.setScore(score);
	                    	 
	                    	 sounds.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);   
	                    	 if((score%50 == 0) & (brain.getLive() < 50))
	                 		{
	                 			//live++;
	                    		 if(brain.getLive() < 6 )
	                 			brain.setLive(brain.getLive()+1);
	                 		}
	                    	
	                     }
	                     if(menu.GetPause() == true)
	                     {
	                    	 menu.setPause(false);
	                    	game = false;
	                     }
	                     //if((menu.GetOldGame() == true) & (menu.getHome() == true))
	                     
	                     if(menu.GetNewGame() == true)
	                     {
	                    	 menu.setNewGame(false);
	                    	 game = true;
	                    	 brain.setLive(5);
	                    	 score = 0;
	                    	 brain.setALive(false);
	                    	 zombi.setX(0);
	                    	 cloud.setScore(0);
	                     }
	                     if((menu.GetOldGame() == true) & (live != 0))
	                     {
	                    	 menu.setOldGame(false);
	                    	 game = true;
	                     }
	                     if(menu.getHome() == true)
	                     {
	                    	
	                    	menu.setHome(false);
	                    	Home = true;
	                     }
	                     
	                     intersect = false;                       		 
	                     live = brain.getLive();
	                     
	                     cloud.setLive(live);
	                    
	                }
	            }
	            catch (Exception e) { }
	            finally
	            {
	                if (canvas != null)
	                {
	                	view.getHolder().unlockCanvasAndPost(canvas);
	                }
	            }
	        }
	    }
}

	//-------------End of GameThread--------------------------------------------------\\
	
	public GameView(Context context) 
	{
		super(context);
		
		mThread = new GameThread(this);

		thred.start();
        
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		sExplosion = sounds.load(context, R.raw.collected , 1);
		
		sounds2 = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		sExplosion2 = sounds2.load(context, R.raw.monsterkilled, 3);
        /*Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback() 
        {
      	  	 /*** Уничтожение области рисования */
               public void surfaceDestroyed(SurfaceHolder holder) 
               {
            	   boolean retry = true;
            	    mThread.setRunning(false); 
            	    while (retry)
            	    {
            	        try
            	        {
            	            // ожидание завершение потока
            	            mThread.join();
            	            retry = false;
            	        }
            	        catch (InterruptedException e) { }
            	    }
               }
               /** Создание области рисования */
               public void surfaceCreated(SurfaceHolder holder) 
               {
            	   mThread.setRunning(true);
            	   mThread.start();
               }

               /** Изменение области рисования */
               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
               {
               }
        });
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.brain);
        picture1			= BitmapFactory.decodeResource(getResources(), R.drawable.zombi1);
        picture2			= BitmapFactory.decodeResource(getResources(), R.drawable.zombi2);
        picture3			= BitmapFactory.decodeResource(getResources(), R.drawable.zombi3);
        picture4			= BitmapFactory.decodeResource(getResources(), R.drawable.zombi4);
        picture5 			= BitmapFactory.decodeResource(getResources(), R.drawable.zombi5);
        picture6 			= BitmapFactory.decodeResource(getResources(), R.drawable.zombi6);
        picture7            = BitmapFactory.decodeResource(getResources(), R.drawable.zombi7);    
        pictureBrain        = BitmapFactory.decodeResource(getResources(), R.drawable.brain); 
        picturebackground   = BitmapFactory.decodeResource(getResources(), R.drawable.background1);
        heart			    = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        blackheart			= BitmapFactory.decodeResource(getResources(), R.drawable.bkackheart);
        picturecloud		= BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
        menuphon			= BitmapFactory.decodeResource(getResources(), R.drawable.menu);
        start				= BitmapFactory.decodeResource(getResources(), R.drawable.play);
        restart   			= BitmapFactory.decodeResource(getResources(), R.drawable.restart);
        pause				= BitmapFactory.decodeResource(getResources(), R.drawable.pause);
        home 				= BitmapFactory.decodeResource(getResources(), R.drawable.home);
        
	}
	
	 /**Функция рисующая все спрайты и фон*/
    protected void onDraw(Canvas canvas) {     	
          canvas.drawColor(Color.WHITE);
          //canvas.drawBitmap(picture3, 10, 10, null);
          background.onDraw(canvas); 
          if(game == true)
          {
          zombi.onDraw(canvas);     
          brain.onDraw(canvas);
          }
          cloud.onDraw(canvas);
          menu.onDraw(canvas,live);
    }
    
    /*Проверка на столкновения*/
    private boolean testCollision(Rect a,Rect b) {
    	
    	if (a.intersect(b) == true)
    	return true;
    	else
    	return false;
    	
		
	}
	public void run() {
		while(true) {
			Random rnd = new Random();
			try {
				Thread.sleep(rnd.nextInt(2000));
				//enemy.add(new Enemy(this, enemies));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    public void setWidth(int a)
    {
    	width = a;    
    }
    public void setHeight(int a)
    {
    	height = a;    
    }
    public void setMetrics(DisplayMetrics dm)
    {
    	displayMetrics = dm;
    	picturebackground = Bitmap.createScaledBitmap(picturebackground, dm.widthPixels, dm.heightPixels, false);
    	
    	picturecloud = Bitmap.createScaledBitmap(picturecloud, dm.widthPixels, dm.heightPixels /4, false);
    	
    	pictureBrain = Bitmap.createScaledBitmap(pictureBrain, dm.widthPixels/10,dm.widthPixels/10, false);   	
    	picture1 = Bitmap.createScaledBitmap(picture1, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        picture2 = Bitmap.createScaledBitmap(picture2, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        picture3 = Bitmap.createScaledBitmap(picture3, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        picture4 = Bitmap.createScaledBitmap(picture4, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        picture5 = Bitmap.createScaledBitmap(picture5, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        picture6 = Bitmap.createScaledBitmap(picture6, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        picture7 = Bitmap.createScaledBitmap(picture7, dm.widthPixels / 7, (dm.widthPixels / 7) + (dm.widthPixels / 14), false);
        start = Bitmap.createScaledBitmap(start, dm.widthPixels / 7, dm.widthPixels / 7, false);
        pause = Bitmap.createScaledBitmap(pause, dm.widthPixels / 7, dm.widthPixels / 7, false);
        restart = Bitmap.createScaledBitmap(restart, dm.widthPixels / 7, dm.widthPixels / 7, false);
        home = Bitmap.createScaledBitmap(home, dm.widthPixels / 7, dm.widthPixels / 7, false);
        heart = Bitmap.createScaledBitmap(heart, dm.widthPixels / 14, dm.widthPixels / 14, false);
        blackheart = Bitmap.createScaledBitmap(blackheart, dm.widthPixels / 14, dm.widthPixels / 14, false);
        menuphon = Bitmap.createScaledBitmap(menuphon,dm.widthPixels/6*4 , dm.widthPixels/6*4/2, false);
        //button = Bitmap.createScaledBitmap(button, menuphon.getWidth() / 6*4 ,(menuphon.getWidth() / 6*4 )/ 4, false);
        
        background = new Background(this,picturebackground);
        brain = new Brain(this,pictureBrain);
        cloud = new Cloud(this,picturecloud,heart,blackheart,pictureBrain);
        zombi = new Zombi(this,bmp, picture1, picture2, picture3, picture4, picture5, picture6, picture7);
        menu = new Menu(this,home,menuphon,start,restart,pause);
    }
    public int getLive()
    {
    	return live;
    }
    public void SetNewGame()
    {
    	menu.setNewGame(false);	
    }
    public void NewGame()
    {
    	live = 5;
    	game = true;
    }
    public int GetX()
    {
    	return x;
    }
    public int GetY()
    {
    	return y;
    }
    public void SetX(int a)
    {
    	x = a;
    }
    public void SetY(int a)
    {
    	y = a;
    }
    public void SetMove(boolean a)
    {
    	zombi.SetMove(a);
    }
    public boolean GetHome()
    {
    return Home;
    }
    public int GetScore()
    {
    	return score;
    }
    /*
      private void SavePreferences(String key, String value){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
       }
    /*public void savePreferences()
    {
    // получить доступ к объекту Editor, чтобы изменить общие настройки.
    SharedPreferences.Editor editor = mySharedPreferences.edit();
    // задать новые базовые типы в объекте общих настроек.
    editor.putBoolean("isTrue", true);
    editor.putFloat("floatNumber", 1f);

    editor.putInt("intNumber", 2);
    editor.putLong("longNumber", 3l);
    editor.putString("textValue", "Not Empty");
    editor.commit();
    }*/
}
