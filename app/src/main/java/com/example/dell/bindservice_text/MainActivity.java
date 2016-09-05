package com.example.dell.bindservice_text;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   private static final String TAG="MainActivityTag";
    MyService myService=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ServiceConnection serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v(TAG,"onServiceConnected");
                myService=((MyService.LocalBinder)service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.v(TAG,"onServiceDisconnected");
            }
        };
        Button btnStart=(Button)findViewById(R.id.button_StartService);
        Button btnEnd=(Button)findViewById(R.id.button_StopService);
        Button btnUse=(Button)findViewById(R.id.button_UseService);
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyService.class);
                bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                unbindService(serviceConnection);
            }
        });
        btnUse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(myService!=null){
                    Log.v(TAG,"Using Service:"+myService.add(1,2));
                }
            }
        });
    }
}
