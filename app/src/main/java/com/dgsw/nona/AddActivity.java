package com.dgsw.nona;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add);

        Button Add_btn = findViewById(R.id.add_button);

        View.OnClickListener AddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(nextIntent);
            }
        };
    }
}
