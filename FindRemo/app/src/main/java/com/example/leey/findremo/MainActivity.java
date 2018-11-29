package com.example.leey.findremo;

import android.hardware.ConsumerIrManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int S_val[];
    ConsumerIrManager mCIR;
    private static final String TAG = "ConsumerIRTest";
    Object irdaService;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.powerOn:
                int S_powerOn[] ={8500,4300, 550,500, 550,500, 600,1500, 550,1600, 500,550, 550,500, 550,1550, 550,1550, 550,1600, 500,1600, 550,1550, 550,500, 550,1550, 600,500, 500,550, 550,500, 550,1550, 550,500, 550,550, 500,550, 550,1550, 550,500, 550,1600, 500,550, 500,550, 550,500, 550,1550, 550,550, 500,550, 550,500, 550,500, 550,550, 500,550, 550,500, 550,500, 550,550, 500,550, 500,550, 550,1550, 550,1550, 550,550, 550,500, 550,500, 550,500, 550,550, 500,550, 500,550, 550,500, 550,550};
                S_val = S_powerOn;
                break;

            case R.id.powerOff:
                int S_powerOff[] = {8500,4300, 500,550, 550,550, 500,1600, 500,1600, 550,500, 550,500, 550,1600, 500,1600, 500,1600, 500,1600, 500,1600, 500,550, 550,1600, 500,550, 500,550, 550,500, 500,1600, 550,550, 500,550, 500,550, 500,1600, 550,550, 500,1600, 500,550, 550,500, 500,600, 500,1600, 500,550, 550,500, 500,550, 550,550, 500,550, 500,550, 550,500, 550,550, 500,550, 550,500, 500,550, 500,1600, 550,1600, 500,550, 500,550, 500,550, 550,550, 500,550, 500,550, 550,500, 500,550, 550,550};
                S_val = S_powerOff;
                break;

            case R.id.strong:
                int S_strong[] = {8500,4300, 550,550, 500,550, 500,1600, 500,1600, 500,550, 550,550, 500,1600, 500,1600, 550,1550, 500,1600, 500,1600, 550,550, 500,550, 500,550, 500,550, 550,1600, 500,1600, 500,550, 500,550, 500,550, 550,1600, 500,550, 550,1550, 500,550, 500,550, 550,550, 500,1600, 500,550, 550,500, 550,550, 500,550, 550,500, 500,550, 550,550, 500,550, 500,550, 500,550, 550,550, 500,1600, 500,1600, 500,550, 500,550, 550,550, 500,550, 500,550, 500,550, 550,550, 500,550, 500,550};
                S_val = S_strong;
                break;

            case R.id.medium:
                int S_medium[] = {8550,4250, 550,550, 500,550, 500,1600, 550,1550, 500,550, 550,550, 500,1600, 550,1550, 500,1600, 500,1600, 550,1600, 500,550, 500,550, 500,550, 550,1550, 550,550, 500,1600, 550,500, 500,550, 500,600, 500,1600, 500,550, 500,1600, 500,550, 550,550, 500,550, 500,1600, 550,500, 500,550, 550,550, 500,550, 500,550, 500,550, 550,550, 500,550, 500,550, 550,500, 550,550, 500,1600, 500,1600, 500,550, 500,550, 550,550, 500,550, 500,550, 550,500, 550,550, 500,550, 550,500};
                S_val =S_medium;
                break;

            case R.id.weak:
                int S_weak[] = {8550,4250, 550,550, 500,550, 550,1550, 500,1600, 500,550, 550,550, 500,1600, 500,1600, 500,1600, 550,1550, 550,1550, 550,550, 550,500, 550,1550, 500,550, 550,550, 500,1600, 500,550, 500,550, 550,550, 500,1600, 500,550, 550,1550, 500,550, 500,600, 500,550, 500,1600, 500,550, 550,500, 550,550, 500,550, 550,500, 500,550, 550,550, 500,550, 500,550, 500,550, 550,550, 500,1600, 500,1600, 550,500, 550,500, 550,550, 500,550, 500,550, 500,550, 550,550, 500,550, 500,550};
                S_val = S_weak;
                break;
        }
        mCIR.transmit(38400,S_val);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
