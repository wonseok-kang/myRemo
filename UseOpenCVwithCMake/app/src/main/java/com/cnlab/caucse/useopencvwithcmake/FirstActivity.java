package com.cnlab.caucse.useopencvwithcmake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

    }

    public void start(View view) {//dowon century
        Intent intent;
        switch (view.getId()) {
            case R.id.fisrtStart:
                intent = new Intent(this, cameraActivity.class);
                startActivity(intent);
                break;
        }
    }
}
