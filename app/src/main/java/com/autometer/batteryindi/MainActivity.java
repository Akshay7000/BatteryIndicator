package com.autometer.batteryindi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView battery; //= findViewById(R.id.batteryLevel);

    private BroadcastReceiver batterylevelReciver = new BroadcastReceiver() {
       // @SuppressLint("SetTextI18n")

        @Override
        public void onReceive(Context context, Intent intent) {
            int defaultValue = 0 ;
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, defaultValue);
            battery.setText(String.valueOf(level)+"%");
        }
    };

    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        battery = findViewById(R.id.batteryLevel);
        this.registerReceiver(this.batterylevelReciver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED ));

            bt = (Button) findViewById(R.id.clickme);
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.sample);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mp.start();
                }
            });

    }
}
