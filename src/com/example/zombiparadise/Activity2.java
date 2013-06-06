package com.example.zombiparadise;

/*import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
*/
//import com.example.fed.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class Activity2 extends Activity implements OnClickListener{

	/** Called when the activity is first created. */
	TextView t;
	//private String sText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	  
	    
	     

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); 

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity2);
        t=new TextView(this); 
        t=(TextView)findViewById(R.id.textView1); 
        
        SharedPreferences  sharedPreferences = getSharedPreferences("Score", MODE_PRIVATE);
        t.setText("highscore  " + Integer.toString(sharedPreferences.getInt("Score",0)));
        
        //sText = "Step One: unpack egg";
        //t.setText("yes");
        
	    // TODO Auto-generated method stub
	    Button startButton = (Button)findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        
        Button exitButton = (Button)findViewById(R.id.button2);
        exitButton.setOnClickListener(this);
        
        splash = (ImageView) findViewById(R.id.imageView1); //получаем индентификатор ImageView с Splash картинкой
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
        
         //textSavedMem1;
	    
	    //final TextView textSavedMem1 = (TextView)findViewById(R.id.textView1);	    
	    //textSavedMem1.setText("Score");
	    
	    //final TextView text = (TextView)findViewById(R.id.textView1);
        //text.setText("dbv");
    }
	public boolean onTouchEvent(MotionEvent e) 
    { 
		Log.i("fd","dh");
		SharedPreferences  sharedPreferences = getSharedPreferences("Score", MODE_PRIVATE);
       t.setText("highscore  " + Integer.toString(sharedPreferences.getInt("Score",0)));
		return true;   	 
    }
	

    /** Обработка нажатия кнопок */
    public void onClick(View v) {
    	//SharedPreferences  sharedPreferences = getSharedPreferences("Score", MODE_PRIVATE);
        //t.setText("highscore" + Integer.toString(sharedPreferences.getInt("Score",0)));
                switch (v.getId()) {
                        //переход на сюрфейс
                        case R.id.button1: {
                            Intent intent = new Intent();
                            intent.setClass(this, Activity1.class);
                            startActivity(intent);
                        }
                        break;
                        
                         //выход
                        case R.id.button2: 
                        {
                             finish();
                             
                        }break;
                        
                        default:
                                break;
                                //Log.i("fd", "dh");
                }
        }
    private static final int STOPSPLASH = 0;
    private static final long SPLASHTIME = 0; //Время показа Splash картинки 5 секунд
    private ImageView splash;
    
    private Handler splashHandler = new Handler() 
    {
    	//создаем новый хэндлер
             public void handleMessage(Message msg) {
            	 
                 switch (msg.what) {
                 case STOPSPLASH:
                     //убираем Splash картинку - меняем видимость
                     splash.setVisibility(View.GONE);
                     break;
                 }
                 super.handleMessage(msg);
             }
          };
}