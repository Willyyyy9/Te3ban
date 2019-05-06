package com.example.mohammedwalaaeldin.serpiente;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LoadUser extends AppCompatActivity {
    ListView lvUsers;
    ArrayList<Integer> faces = new ArrayList<>();
    ArrayList<String> Users = new ArrayList<>();
    private boolean mIsBound = false;
    private MusicService mServ;
    HomeWatcher mHomeWatcher;
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
        setContentView(R.layout.activity_load_user);
        lvUsers = findViewById(R.id.lv_user);
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
        try {
            SharedPreferences myFile = getSharedPreferences("userInfo", MODE_PRIVATE);
            Set<String> set = new HashSet<String>();
            set = myFile.getStringSet("listUsers", null);
            ArrayList<String> users = new ArrayList<String>(set);
            Users = users;


                if (mServ != null) {
                    mServ.resumeMusic();
                }
                lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences myFile = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor myEditor = myFile.edit();
                        myEditor.putString("currentUser",Users.get(position));
                        myEditor.commit();
                        Intent i = new Intent(LoadUser.this,MainActivity.class);
                        startActivity(i);


                    }
                });




            for (int i = 0;i < users.size(); i++){

                if (users.get(i).equalsIgnoreCase("Mohamed Walaa Eldeen")){
                    faces.add(R.drawable.welwelface);
                }
                else if (users.get(i).equalsIgnoreCase("Youssef Tarek")){
                    faces.add(R.drawable.yousseftarekface);
                }
                else if (users.get(i).equalsIgnoreCase("Abdelrahman Omar")){
                    faces.add(R.drawable.abdelrahmanomarface);
                }
                else if (users.get(i).equalsIgnoreCase("Ahmed Adel El sergany")){
                    faces.add(R.drawable.serganyface);
                }
                else if (users.get(i).equalsIgnoreCase("Youssef Amr")){
                    faces.add(R.drawable.youssefamrface);
                }
                else if (users.get(i).equalsIgnoreCase("Mohamed Ali")){
                    faces.add(R.drawable.atefface);
                }
                else if (users.get(i).equalsIgnoreCase("Mahmoud Issa")){
                    faces.add(R.drawable.issaface);
                }
                else if (users.get(i).equalsIgnoreCase("Mohamed Amgad")){
                    faces.add(R.drawable.amgadface);
                }
                else if (users.get(i).equalsIgnoreCase("Merna Muhammed")){
                    faces.add(R.drawable.mernaface);
                }
                else if (users.get(i).equalsIgnoreCase("Mariam Ahmed")){
                    faces.add(R.drawable.mariamface);
                }
                else if (users.get(i).equalsIgnoreCase("Melesia Ashraf")){
                    faces.add(R.drawable.melesiaface);
                }
                else if (users.get(i).equalsIgnoreCase("Mira Nasser")){
                    faces.add(R.drawable.miraface);
                }
                else if (users.get(i).equalsIgnoreCase("May Hatem")){
                    faces.add(R.drawable.mayface);
                }
                else if (users.get(i).equalsIgnoreCase("Rawda Bahnasawy")){
                    faces.add(R.drawable.rawdaface);
                }
                else if (users.get(i).equalsIgnoreCase("Salma Ashraf")){
                    faces.add(R.drawable.salmaface);
                }
                else if (users.get(i).equalsIgnoreCase("Menna Hesham")){
                    faces.add(R.drawable.mennaface);
                }
                else if (users.get(i).equalsIgnoreCase("Walaa Eldin Ragheb")){
                    faces.add(R.drawable.walaaface);
                }
                else if (users.get(i).equalsIgnoreCase("Lotus Rostom")){
                    faces.add(R.drawable.lotusface);
                }
                else if (users.get(i).equalsIgnoreCase("Aly Ragheb")){
                    faces.add(R.drawable.alyface);

                }
                else if (users.get(i).equalsIgnoreCase("Ahmed Waleed")){
                    faces.add(R.drawable.ahmedface);

                }
                else if (users.get(i).equalsIgnoreCase("Waleed Yehia Ragheb")){
                    faces.add(R.drawable.waleedface);

                }
                else if (users.get(i).equalsIgnoreCase("Aya Hamzwai")){
                    faces.add(R.drawable.ayahamzawi);
                }
                else if (users.get(i).equalsIgnoreCase("Felo Osama")){
                    faces.add(R.drawable.feloosama);;
                }
                else if (users.get(i).equalsIgnoreCase("Yasmin Ahmed")){
                    faces.add(R.drawable.yasminahmed);
                }
                else if (users.get(i).equalsIgnoreCase("Ahmed Asem")){
                    faces.add(R.drawable.ahmedasem);
                }
                else if (users.get(i).equalsIgnoreCase("Habiba Khaled")){
                    faces.add(R.drawable.habibakhaledface);
                }
                else {
                    String gender = myFile.getString("Gender", "male");
                    if (gender == "male") {
                        faces.add(R.drawable.maleface);
                    } else if (gender == "female") {
                        faces.add(R.drawable.femaleface);

                    }

                }
                CustomAdapter customAdapter = new CustomAdapter();
                lvUsers.setAdapter(customAdapter);



        }} catch (Exception e) {

        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);

    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Users.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlistview,null);
            ImageView imageView = convertView.findViewById(R.id.img_icon);
            TextView textView = convertView.findViewById(R.id.tv_name);
            imageView.setImageResource(faces.get(position));
            textView.setText(Users.get(position));
            return convertView;
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


