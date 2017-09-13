package com.example.abhinav.aidl2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.abhinav.aidlexample.IRemoteService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    IRemoteService remoteService;
    ServiceConnection serviceConnection;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        Intent intent=new Intent();
        intent.setComponent(new ComponentName("com.example.abhinav.aidlexample","com.example.abhinav.aidlexample.CustomService"));
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                remoteService= IRemoteService.Stub.asInterface(service);
                try {
                    Toast.makeText(MainActivity.this,remoteService.getThings(),Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        button.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
        {
            try {
                Toast.makeText(this,remoteService.getThings(),Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
