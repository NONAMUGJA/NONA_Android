package com.dgsw.nona.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dgsw.nona.R;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_request);

        Button request_btn = findViewById(R.id.request_button);
        final EditText request_food = findViewById(R.id.request_food);
        final EditText request_count = findViewById(R.id.request_count);
        final EditText request_comment = findViewById(R.id.request_comment);

        View.OnClickListener requestListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(RequestActivity.this, MainActivity.class);
                String food = request_food.getText().toString();
                String count = request_count.getText().toString();
                String comment = request_comment.getText().toString();

                if(Check(food, count, comment) == 1){
                    startActivity(nextIntent);
                }
            }
        };

        request_btn.setOnClickListener(requestListener);


    }

    private int Check(String food, String count, String comment){

        if(food.isEmpty()){
            Toast.makeText(this, "교환할 음식을 입력하십시오.", Toast.LENGTH_LONG);
            return -1;
        }
        else if (count.isEmpty()){
            Toast.makeText(this, "음식의 개수를 입력하십시오.", Toast.LENGTH_LONG);
            return -1;
        }
        else if (comment.isEmpty()){
            Toast.makeText(this, "흥정이라도 해주세요 !", Toast.LENGTH_LONG);
            return -1;
        }

        return 1;
    }
}
