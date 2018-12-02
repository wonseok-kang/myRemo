package com.example.leey.findremo;

import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class lgtvRemoteActivity extends AppCompatActivity {
    int S_val[];
    ConsumerIrManager mCIR;
    private static final String TAG = "lgtvRemoteIRTest";
    Object irdaService;

    // Used to load the 'native-lib' library on application startup.
 /*   static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lgtv_remote);

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
            case R.id.lgtvpowerOn:
                int lgtvpowerOn[] ={8950,4400, 650,500, 600,500, 650,1550, 700,450, 600,500, 650,500, 600,500, 650,450, 650,1600, 600,1600, 650,450, 650,1600, 600,1600, 650,1550, 700,1550, 650,1550, 650,450, 650,500, 600,500, 650,1600, 600,500, 650,450, 650,500, 600,500, 650,1550, 650,1600, 600,1600, 650,500, 600,1600, 600,1600, 650,1550, 650,1600, 600};  // NEC 20DF10EF
                S_val = lgtvpowerOn;
                break;

            case R.id.lgtvvolup:
                int lgtvvolup[] = {9000,4400, 650,450, 700,450, 650,1550, 650,450, 700,450, 650,450, 700,450, 650,450, 650,1550, 700,1550, 650,450, 650,1550, 700,1550, 650,1550, 650,1550, 700,1550, 650,450, 650,1550, 700,450, 650,450, 650,500, 650,450, 650,450, 700,450, 650,1550, 650,450, 700,1550, 650,1550, 650,1550, 700,1550, 650,1550, 650,1550, 700};  // NEC 20DF40BF
                S_val = lgtvvolup;
                break;

            case R.id.lgtvvoldown:
                int lgtvvoldown[] = {9000,4400, 650,450, 650,500, 650,1550, 650,450, 700,450, 650,450, 650,500, 650,450, 650,1550, 700,1550, 650,450, 650,1550, 700,1550, 650,1550, 650,1550, 700,1550, 650,1550, 650,1550, 650,500, 650,450, 650,450, 700,450, 650,450, 650,500, 650,450, 650,450, 700,1550, 650,1550, 650,1550, 700,1550, 650,1550, 650,1550, 700};  // NEC 20DFC03F
                S_val =lgtvvoldown;
                break;

            case R.id.lgtvchup:
                int lgtvchup[] = {9000,4400, 650,450, 650,500, 650,1550, 650,450, 700,450, 650,450, 650,500, 650,450, 650,1550, 650,1600, 650,450, 650,1550, 700,1550, 650,1550, 650,1550, 650,1600, 650,450, 650,450, 700,450, 650,450, 700,450, 650,450, 650,450, 700,450, 650,1550, 650,1550, 700,1550, 650,1550, 650,1550, 700,1550, 650,1550, 650,1550, 700};  // NEC 20DF00FF
                S_val = lgtvchup;
                break;

            case R.id.lgtvchdown:
                int lgtvchdown[] = {8950,4450, 600,500, 650,500, 600,1600, 600,500, 650,500, 600,500, 650,450, 650,500, 650,1550, 650,1550, 650,500, 650,1550, 650,1600, 600,1600, 600,1600, 650,1550, 650,1600, 600,500, 650,500, 600,500, 650,450, 650,500, 600,500, 650,500, 600,500, 650,1550, 650,1600, 600,1600, 650,1550, 650,1600, 600,1600, 650,1550, 650};  // NEC 20DF807F
                S_val = lgtvchdown;
                break;
        }
        mCIR.transmit(38400,S_val);
    }
}
