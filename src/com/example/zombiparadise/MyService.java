package com.example.zombiparadise;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
public class MyService extends Service {
        private static final String TAG = "MyService";
        MediaPlayer player;
        
        @Override
        public IBinder onBind(Intent intent) {
                return null;
        }
        
        @Override
        public void onCreate() {             
                
                player = MediaPlayer.create(this, R.raw.phonmusic);
                player.setLooping(true); // зацикливаем
        }

        @Override
        public void onDestroy() {
                player.stop();
        }
        
        @Override
        public void onStart(Intent intent, int startid) {
                //Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
                player.start();
        }
        
        private void onBackPressed() 
        {
            stopService(new Intent(this, MyService.class));
        }
}
