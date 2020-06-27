package com.autometer.batteryindi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable batteryAnimation;
    private TextView battery; //= findViewById(R.id.batteryLevel);
    MediaPlayer mp;
    EditText editText;
    int value=100;
    private Context mContext;
    private TextView mTextView;
    boolean usbCharge;
    boolean isCharging;


    private BroadcastReceiver batterylevelReciver = new BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {


            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                    ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            if(isCharging){
                mTextView.setText("Charging : Yes.");
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,-1);
                usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                if(usbCharge){
                    mTextView.setText(mTextView.getText()+"\nUSB Charging");

                }
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                if(acCharge){
                    mTextView.setText(mTextView.getText()+"\nAC Charging");
                }
                boolean wirelessCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_WIRELESS;
                if(wirelessCharge){
                    mTextView.setText(mTextView.getText()+"\nWireless Charging");
                }
                batteryAnimation.start();
            }
            else {
                // Display the battery charging state
                mTextView.setText("Charging : No.");
                batteryAnimation.stop ();


            }
            int defaultValue = 0 ;
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, defaultValue);
            battery.setText(level +"%");


            if(level == value) {
                mp.start();
                Toast.makeText(mContext, "Charging Complected "+value+"%", Toast.LENGTH_SHORT).show();
            }

        }


    };



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button1 = (Button)findViewById (R.id.button);
        final EditText editText =(EditText)findViewById (R.id.editText);
        battery = findViewById(R.id.batteryLevel);
        this.registerReceiver(this.batterylevelReciver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED ));

        mp = MediaPlayer.create(this, R.raw.sample);
        ImageView imageView = findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        batteryAnimation = (AnimationDrawable) imageView.getBackground ();


        mContext = getApplicationContext();
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(batterylevelReciver,iFilter);
        mTextView = findViewById(R.id.tv);

        button1.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                value= Integer.parseInt (editText.getText ().toString ());
                Toast.makeText(mContext, "Alert Set to "+value+"%", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(isCharging) {
            batteryAnimation.start();
        }

    }
}
