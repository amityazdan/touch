package com.example.blacknblack.youtubetouchblocker;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends Service implements SensorEventListener {
    private View mView;
    private WindowManager mWindowManager;
    boolean alert = false;
    private SensorManager manager;
    private Sensor accelerometer;
    MediaPlayer mediaPlayer;


    public MyIntentService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView = LayoutInflater.from(this).inflate(R.layout.mainxml, null);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mediaPlayer=MediaPlayer.create(MyIntentService.this,R.raw.sv);





        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        paramsF.gravity = Gravity.BOTTOM | Gravity.LEFT;
        paramsF.x = 0;
        paramsF.y = 200;
        paramsF.windowAnimations = android.R.style.Animation_Translucent;

        mView.findViewById(R.id.openBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyIntentService.this, MyService.class);
                startService(intent);
            }
        });


        final Button alertBTN = (Button) mView.findViewById(R.id.button2);
        alertBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (alert == false) {
                    alertBTN.setBackgroundColor(Color.RED);
                    alert = true;

                    manager.registerListener(MyIntentService.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    alertBTN.setBackgroundColor(Color.DKGRAY);
                    alert = false;
                    manager.unregisterListener(MyIntentService.this, accelerometer);
                }
            }
        });

        mWindowManager.addView(mView, paramsF);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(MyIntentService.this, accelerometer);
        if (mView != null) {
            mWindowManager.removeView(mView);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
//        float z= sensorEvent.values[2];

        if (x > 2 || x < -2 || y > 2 || y < -2) {
            if (mediaPlayer.isPlaying()==false){
                mediaPlayer.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

/*

    class alertClass implements SensorEventListener {
        SensorManager manager2 = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = manager2.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];

            if (x > 2 || x < -2 || y > 2 || y < -2) {
                Toast.makeText(MyIntentService.this, "x and y :)", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
*/


}
