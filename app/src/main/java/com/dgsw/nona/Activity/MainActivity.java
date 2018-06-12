package com.dgsw.nona.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dgsw.nona.Adapter.MainAdapter;
import com.dgsw.nona.data.BoxData;
import com.dgsw.nona.task.MainTask;

import com.dgsw.nona.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //ArrayList<BoxData> boxDataArrayList = new ArrayList<>();

        new MainTask().setTaskListener(new MainTask.TaskListener() {
            @Override
            public void onTaskFinished(BoxData[] boxDataArray) {
                recyclerView.setAdapter(new MainAdapter(MainActivity.this, boxDataArray));
            }
        }).execute();

        /*boxDataArrayList.add(new BoxData(2, "2320", "나의 새우깡의 다음 주인이 될 사람을 찾는다.\n" +
                "목이 매우 몹시 마릅니다. 당신의 탄산음료가 불쌍한 오찬희를 살립니다.", "2201", "새우깡", "1개", "", getDrawable(R.drawable.ic_image_none)));*/


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
