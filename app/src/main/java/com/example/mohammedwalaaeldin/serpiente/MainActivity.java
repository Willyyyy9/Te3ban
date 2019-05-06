package com.example.mohammedwalaaeldin.serpiente;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
private TextView tvWelcome;
private ImageView ivUser;

    String current;
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
    HomeWatcher mHomeWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvWelcome = findViewById(R.id.tv_welcome);
        ivUser = findViewById(R.id.iv_user);
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });


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
    protected void onResume() {
        super.onResume();
        if (mServ != null) {
            mServ.resumeMusic();
        }


        try {
            SharedPreferences myFile = getSharedPreferences("userInfo",MODE_PRIVATE);

            String currentUser = myFile.getString("currentUser","User");
            if (currentUser.equalsIgnoreCase("Mohamed Walaa Eldeen")){
                tvWelcome.setText("Welcome Welwel");
                ivUser.setImageResource(R.drawable.welwelface);
            }
            else if (currentUser.equalsIgnoreCase("Youssef Tarek")){
                tvWelcome.setText("Welcome Joe");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Abdelrahman Omar")){
                tvWelcome.setText("Welcome 7ex");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Ahmed Adel El sergany")){
                tvWelcome.setText("Welcome Mex");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Youssef Amr")){
                tvWelcome.setText("Welcome Johney");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Mohamed Ali")){
                tvWelcome.setText("Welcome Atef");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Mahmoud Issa")){
                tvWelcome.setText("Welcome Issa");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Mohamed Amgad")){
                tvWelcome.setText("Welcome Amjad");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Merna Muhammed")){
                tvWelcome.setText("Welcome ya3am Momel");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Mariam Ahmed")){
                tvWelcome.setText("Welcome Meryam");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Melesia Ashraf")){
                tvWelcome.setText("Welcome Malizya");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Mira Nasser")){
                tvWelcome.setText("Welcome Marmour");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("May Hatem")){
                tvWelcome.setText("Welcome May");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Rawda Bahnasawy")){
                tvWelcome.setText("Welcome ya Bahnasawy");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Salma Ashraf")){
                tvWelcome.setText("Welcome Salma");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Menna Hesham")){
                tvWelcome.setText("Welcome Menna");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Walaa Eldin Ragheb")){
                tvWelcome.setText("Welcome Yaba El7ag");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Lotus Rostom")){
                tvWelcome.setText("Welcome Yaset El7abayeb");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Aly Ragheb")){
                tvWelcome.setText("Welcome Ya Zeft");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Ahmed Waleed")){
                tvWelcome.setText("Welcome Ya3am Ahmad");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Waleed Yehia Ragheb")){
                tvWelcome.setText("Welcome 3amo Waleed");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Aya Hamzwai")){
                tvWelcome.setText("Welcome ya Hamzawi");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Felo Osama")){
                tvWelcome.setText("Welcome yabol Falafeel");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Yasmin Ahmed")){
                tvWelcome.setText("Welcome Yasmin");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Ahmed Asem")){
                tvWelcome.setText("Welcome ya Ahmaaa");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else if (currentUser.equalsIgnoreCase("Habiba Khaled")){
                tvWelcome.setText("Welcome Habiba");
                ivUser.setImageResource(R.drawable.femaleface);
            }
            else if (currentUser.equalsIgnoreCase("Ahmad Fawzy")){
                tvWelcome.setText("Welcome ya 7ag Ahmed");
                ivUser.setImageResource(R.drawable.maleface);
            }
            else {
                String gender = myFile.getString("Gender","male");
                if (gender == "male"){
                    tvWelcome.setText("Welcome Yasta");
                    ivUser.setImageResource(R.drawable.maleface);
                }
                else if (gender == "female"){
                    tvWelcome.setText("Welcome ya Amar");
                    ivUser.setImageResource(R.drawable.femaleface);
                }

            }
                current = currentUser;





            }catch (Exception e){

            }



    }


    public void myStart(View view) {
        if(current.equals("")){
            Intent i = new Intent(MainActivity.this,UserName.class);
            startActivity(i);
        } else {
            Intent i = new Intent(MainActivity.this,SnakeActivity.class);
            startActivity(i);
        }

    }

    public void myExit(View view) {
        finish();
    }

    public void myCreate(View view) {
        Intent i = new Intent(MainActivity.this,UserName.class);
        startActivity(i);
    }

    public void myLoad(View view) {
        Intent i = new Intent(MainActivity.this,LoadUser.class);
        startActivity(i);
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
