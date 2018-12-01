package com.example.leey.findremo;

import android.content.Intent;
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

    }

    public void remote(View view){//dowon century
        Intent intent;
        switch (view.getId()){
            case R.id.golgtv:
                intent = new Intent(this,lgtvRemoteActivity.class);
                startActivity(intent);
                break;

            case R.id.gocentury:
                intent = new Intent(this,centuryRemoteActivity.class);
                startActivity(intent);
                break;

            case R.id.goprojector:
                intent = new Intent(this,projectorActivity.class);
                startActivity(intent);
                break;

            case R.id.gosamtv:
                intent = new Intent(this,smatvRemoteActivity.class);
                startActivity(intent);
                break;

            case R.id.gosamair:
                intent = new Intent(this,samairRemoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
   // public native String stringFromJNI();
}
