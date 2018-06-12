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

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add);

        Button add_btn = findViewById(R.id.add_button);
        final EditText add_food = findViewById(R.id.add_food);
        final EditText add_count = findViewById(R.id.add_count);
        final EditText add_comment = findViewById(R.id.add_comment);
        final EditText add_lock = findViewById(R.id.add_lock);


        View.OnClickListener AddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(AddActivity.this, MainActivity.class);
                String food = add_food.getText().toString();
                String count = add_count.getText().toString();
                String comment = add_comment.getText().toString();
                String lock = add_lock.getText().toString();

                if (Check(food, count, comment, lock) == 1){
                    startActivity(nextIntent);
                }

            }
        };

        add_btn.setOnClickListener(AddListener);
    }

    private int Check(String food, String count, String comment, String lock) {

        if (food.isEmpty()){
            Toast.makeText(this, "음식 이름을 입력하십시오.", Toast.LENGTH_LONG);
            return -1;
        }
        else if (count.isEmpty()){
            Toast.makeText(this, "개수를 입력하십시오.", Toast.LENGTH_LONG);
            return -1;
        }
        else if (comment.isEmpty()){
            Toast.makeText(this, "하고싶은 말을 입력하십시오.", Toast.LENGTH_LONG);
            return -1;
        }
        else if (lock.isEmpty()){
            Toast.makeText(this, "자물쇠 번호를 입력하십시오.", Toast.LENGTH_LONG);
            return -1;
        }
        return 1;
    }
}