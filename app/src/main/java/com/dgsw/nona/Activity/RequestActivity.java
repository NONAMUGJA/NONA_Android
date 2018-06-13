package com.dgsw.nona.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dgsw.nona.R;
import com.dgsw.nona.data.Data;

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
                String food = request_food.getText().toString();
                String count = request_count.getText().toString();
                String comment = request_comment.getText().toString();

                if(Check(v, food, count, comment)){
                    /*new AddTask().setTaskListener(new AddTask.TaskListener() {
                        @Override
                        public void onTaskFinished(Boolean result) {
                            if (result) {*/
                                Toast.makeText(getApplicationContext(), "등록 됨", Toast.LENGTH_SHORT).show();
                                finish();
                            /*}
                        }
                    }).execute(Integer.toString(boxNo), Data.getUserID(), System.currentTimeMillis()+".jpg", food, count, comment, 3
                            lock);*/
                }
            }
        };

        request_btn.setOnClickListener(requestListener);


    }

    private boolean Check(View view, String food, String count, String comment){

        if(food.isEmpty()){
            Snackbar.make(view, "교환할 음식을 입력하십시오.", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if (count.isEmpty()){
            Snackbar.make(view, "음식의 개수를 입력하십시오.", Snackbar.LENGTH_LONG).show();
            return false;
        }
        else if (comment.isEmpty()){
            Snackbar.make(view, "흥정이라도 해주세요 !", Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
