package com.example.blacknblack.youtubetouchblocker;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;

public class MyService extends Service {
    private View mView;
    private WindowManager mWindowManager;

    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView = LayoutInflater.from(this).inflate(R.layout.traslucent,null);


        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        final WindowManager.LayoutParams paramsF = new WindowManager.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        paramsF.gravity = Gravity.TOP | Gravity.LEFT;
        paramsF.x=0;
        paramsF.y=200;
        paramsF.windowAnimations=android.R.style.Animation_Translucent;





        mView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        mWindowManager.addView(mView,paramsF);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mView != null){
            mWindowManager.removeView(mView);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
