package com.dgsw.nona;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_request);

        Button request_btn = findViewById(R.id.request_button);

        View.OnClickListener requestListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(RequestActivity.this, MainActivity.class);
                startActivity(nextIntent);
            }
        };

        request_btn.setOnClickListener(requestListener);

    }
}
