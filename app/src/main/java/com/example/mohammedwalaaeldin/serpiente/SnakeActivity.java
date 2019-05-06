package com.example.mohammedwalaaeldin.serpiente;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.Display;
import android.view.KeyEvent;

public class SnakeActivity extends Activity {

    SnakeEngine snakeEngine;
    SnakeEngine instance;
    String score;
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        snakeEngine = new SnakeEngine(SnakeActivity.this,size);
        setContentView(snakeEngine);
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                 score = intent.getStringExtra("Highscore");

            }
        };



    }

    @Override
    protected void onResume() {
        super.onResume();
        snakeEngine.resume();





            if (mServ != null) {
                mServ.resumeMusic();
            }

        }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }

    @Override
    protected void onPause() {


        super.onPause();
        snakeEngine.pause();
        PowerManager pm = (PowerManager)
                getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }

        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            doUnbindService();
            Intent music = new Intent();
            music.setClass(this,MusicService.class);
            stopService(music);
            // activity switch stuff..

        }
        else if (keyCode == KeyEvent.KEYCODE_BACK){
            onBackPressed();
        }
        return false;
    }

}
