package com.cnlab.caucse.useopencvwithcmake;

import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class smatvRemoteActivity extends AppCompatActivity {
    int S_val[];
    ConsumerIrManager mCIR;
    private static final String TAG = "samtvRemoteIRTest";
    Object irdaService;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smatv_remote);

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
            case R.id.stvpowerOn:
                int stvpowerOn[] ={4450,4450, 550,1700, 550,1700, 550,1650, 550,550, 600,550, 550,550, 550,550, 550,600, 550,1650, 550,1700, 550,1650, 600,550, 550,550, 550,550, 600,550, 550,550, 550,550, 550,1700, 550,550, 550,600, 550,550, 550,550, 550,550, 600,550, 550,1650, 550,600, 550,1650, 550,1700, 550,1700, 550,1650, 550,1700, 550,1650, 550};  // SAMSUNG E0E040BF
                S_val = stvpowerOn;
                break;

            case R.id.stvvolup:
                int stvvolup[] = {4450,4450, 550,1650, 600,1650, 600,1600, 600,550, 550,550, 600,500, 550,600, 550,550, 600,1600, 600,1650, 600,1650, 550,550, 600,500, 600,550, 550,550, 600,500, 600,1650, 600,1600, 600,1650, 600,500, 600,550, 550,550, 600,500, 550,600, 550,550, 600,500, 600,500, 600,1650, 600,1650, 550,1650, 600,1650, 550,1700, 550};  // SAMSUNG E0E0E01F
                S_val = stvvolup;
                break;

            case R.id.stvvoldown:
                int stvvoldown[] = {4450,4450, 550,1700, 500,1700, 550,1700, 500,600, 550,600, 500,600, 500,600, 550,550, 550,1700, 550,1700, 500,1700, 550,550, 550,600, 500,600, 550,550, 550,600, 500,1700, 550,1700, 550,550, 550,1700, 500,600, 550,550, 550,600, 500,600, 550,550, 550,600, 500,1700, 550,600, 500,1700, 550,1700, 500,1700, 550,1700, 550};  // SAMSUNG E0E0D02F
                S_val =stvvoldown;
                break;

            case R.id.stvchup:
                int stvchup[] = {4450,4450, 550,1650, 550,1700, 550,1650, 600,550, 550,550, 550,550, 550,600, 550,550, 550,1650, 600,1650, 550,1700, 550,550, 550,550, 550,550, 600,550, 550,550, 550,550, 550,1700, 550,550, 550,550, 600,1650, 550,550, 550,600, 550,550, 550,1700, 550,550, 550,1650, 600,1650, 550,550, 550,1700, 550,1650, 600,1650, 550};  // SAMSUNG E0E048B7
                S_val = stvchup;
                break;
            case R.id.stvchdown:
                int stvchdown[] = {4450,4450, 550,1650, 550,1700, 550,1700, 550,550, 550,550, 550,550, 600,550, 550,550, 550,1700, 550,1650, 550,1700, 550,550, 550,550, 600,550, 550,550, 550,550, 550,600, 550,550, 550,550, 550,600, 550,1650, 550,550, 600,550, 550,550, 550,1700, 550,1650, 550,1700, 550,1650, 600,550, 550,1650, 600,1650, 550,1700, 550};  // SAMSUNG E0E008F7
                S_val = stvchdown;
                break;
        }
        mCIR.transmit(38400,S_val);
    }
}
