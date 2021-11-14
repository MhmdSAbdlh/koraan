package com.mhmdsabdlh.koraan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void btnClick(View view) {
        Intent intent = new Intent("com.mhmdsabdlh.koraan");
        startActivity(intent);
    }

    public void newE(View view) {

        finish();
    }
}