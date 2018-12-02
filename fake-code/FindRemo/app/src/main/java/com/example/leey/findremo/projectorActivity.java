package com.example.leey.findremo;

import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class projectorActivity extends AppCompatActivity {
    int S_val[];
    ConsumerIrManager mCIR;
    private static final String TAG = "projector";
    Object irdaService;

    // Used to load the 'native-lib' library on application startup.
/*    static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projector);

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());

        mCIR = (ConsumerIrManager)getSystemService(this.CONSUMER_IR_SERVICE);

        if(!mCIR.hasIrEmitter()){
            Log.e(TAG,"IR 송신기를 찾을 수 없음");
            return;
        }
        Log.e(TAG,"IR 송신기 발견");

    }

    public void irSend(View view){
        switch (view.getId()){
            case R.id.pjpowerOn:
                int pjpowerOn[] ={500,500, 550,1450, 450,500, 500,450, 500,500, 500,500, 500,500, 450,550, 450,1500, 450,1500, 500,500, 450,1550, 450,1500, 450,1550, 450,1450, 500,550, 450,1500, 450,1500, 450,1550, 450,1500, 450,500, 500,500, 450,550, 450,550, 450,550, 450,500, 500,500, 450,550, 450,500, 500,500, 450,550, 450,500, 500,500, 450,550, 450,500, 500,500, 450,550, 450,500, 500,500, 450,500, 500,550, 450,550, 450,500, 500,500, 450,550, 450,500, 500,500, 450,550, 450,500, 500,500, 450,550, 450,500, 500,1500, 450,1500, 450,1500, 500,1500, 500,2950, 2950};  // SANYO FFFFFFFF
                S_val = pjpowerOn;
                break;

            case R.id.pjwindup:
                int pjwindup[] = {500,500, 450,1500, 500,500, 500,450, 550,450, 500,500, 450,500, 550,450, 500,1500, 450,1500, 450,550, 450,1500, 550,1400, 500,1500, 500,1450, 500,500, 500,1450, 500,1500, 450,1500, 500,1500, 400,550, 450,550, 450,550, 450,500, 500,500, 500,500, 450,500, 500,500, 500,500, 500,450, 550,450, 500,500, 450,500, 500,500, 500,500, 450,500, 500,550, 450,500, 450,550, 450,550, 450,500, 450,550, 500,500, 450,500, 500,500, 500,500, 450,500, 550,450, 500,500, 500,450, 550,450, 500,500, 500,1450, 500,1500, 500,1450, 450,1550, 500,2900, 2950};  // SANYO FFFFFFFF
                S_val = pjwindup;
                break;

            case R.id.pjwinddown:
                int pjwinddown[] = {500,500, 500,1450, 500,550, 450,500, 550,400, 500,550, 500,450, 500,500, 450,1500, 550,1450, 500,450, 500,1500, 450,1500, 500,1450, 500,1500, 450,500, 550,1450, 450,1500, 500,1450, 550,1450, 450,550, 450,550, 450,500, 450,550, 450,550, 500,450, 550,450, 500,500, 450,550, 450,500, 500,500, 450,500, 500,500, 550,450, 450,500, 500,500, 550,450, 500,500, 500,450, 550,450, 450,550, 500,450, 550,450, 450,550, 500,450, 500,500, 500,500, 500,500, 450,500, 500,500, 500,450, 550,450, 500,1450, 550,1450, 550,1450, 500,1450, 450,3000, 2950};  // SANYO FFFFFFFF
                S_val =pjwinddown;
                break;

            case R.id.pjchomise:
                int pjchomise[] = {450,500, 500,1500, 450,500, 500,500, 450,550, 450,500, 500,500, 450,550, 450,1500, 450,1500, 500,500, 450,1550, 400,600, 400,1550, 450,1500, 500,500, 450,1500, 500,1500, 450,1500, 450,1500, 500,500, 450,500, 500,500, 500,500, 450,500, 500,500, 500,500, 450,550, 450,500, 500,500, 500,500, 500,450, 500,500, 500,500, 500,450, 500,500, 500,500, 500,450, 500,500, 500,500, 500,1450, 500,500, 500,500, 450,550, 400,550, 450,550, 450,550, 450,500, 500,500, 500,500, 450,500, 500,500, 500,1450, 500,1500, 450,1500, 500,1450, 500,2950, 2950};  // SANYO FFFFFFFF
                S_val = pjchomise;
                break;
        }
        mCIR.transmit(38400,S_val);
    }
}
