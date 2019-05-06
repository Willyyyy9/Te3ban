package com.example.mohammedwalaaeldin.serpiente;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserName extends AppCompatActivity {

    private EditText etUserName;
    private RadioGroup radioGroup;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    ArrayList<String> users = new ArrayList<String>();
    Set<String> set = new HashSet<String>();
    Set<String> set_1 = new HashSet<String>();
    Set<String> set_2 = new HashSet<String>();
    Set<String> set_3 = new HashSet<String>();
    HomeWatcher mHomeWatcher;
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
        setContentView(R.layout.activity_user_name);
        etUserName = findViewById(R.id.et_newuser);
        maleBtn = findViewById(R.id.RadioButton1);
        femaleBtn = findViewById(R.id.RadioButton2);
        radioGroup = findViewById(R.id.radioGroup);
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
        mHomeWatcher.startWatch();

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

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }


    public void mySubmit(View view) {
        SharedPreferences myFile = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor myEditor = myFile.edit();
        String userName = etUserName.getText().toString();
        if (myFile.getStringSet("listUsers", null) == null) {
            set.addAll(users);
            myEditor.putStringSet("users", set);
            myEditor.commit();
            set_1 = myFile.getStringSet("users", null);
            ArrayList<String> listUsers = new ArrayList<String>(set_1);
            listUsers.add(userName);
            set_2.addAll(listUsers);
            myEditor.putStringSet("listUsers", set_2);
            myEditor.putString("currentUser", userName);
            myEditor.commit();
        } else {
            set_3 = myFile.getStringSet("listUsers", null);
            ArrayList<String> Users = new ArrayList<>(set_3);

            for (int i = 0; i > Users.size(); i++) {
                if (userName == Users.get(i)) {
                    myEditor.putString("currentUser", userName);
                    myEditor.commit();
                    Toast.makeText(this, "You're already Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } }
                    Users.add(userName);
                    set_3.addAll(Users);
                    myEditor.putStringSet("listUsers", set_3);
                    myEditor.putString("currentUser", userName);
                    myEditor.commit();

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    TextView myMsg = new TextView(this);
                    myMsg.setText("Ahla Mesa ?");
                    myMsg.setTextSize(32);
                    myMsg.setTextColor(Color.rgb(6,27,61));
                    builder.setTitle("");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    builder.setView(myMsg);
                    builder.setPositiveButton("Ahla Mesa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(UserName.this, "Gamedddd", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserName.this, MainActivity.class);
                            startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(UserName.this, "Kont 3aref enno No 3ala fekra!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserName.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button posBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    posBtn.setTextColor(Color.rgb(6,27,61));
                    negBtn.setTextColor(Color.rgb(6,27,61));



                    myEditor.commit();
                }


            if (maleBtn.isChecked()) {
                myEditor.putString("Gender", "male");
                myEditor.commit();

            } else if (femaleBtn.isChecked()) {
                myEditor.putString("Gender", "female");
                myEditor.commit();

            } else {
                Toast.makeText(this, "Please Enter Gender", Toast.LENGTH_SHORT).show();
            }
        }
}




