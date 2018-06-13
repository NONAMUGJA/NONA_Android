package com.dgsw.nona.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.dgsw.nona.adapter.MainAdapter;
import com.dgsw.nona.data.BoxData;
import com.dgsw.nona.task.ImageLoadTask;
import com.dgsw.nona.task.MainTask;

import com.dgsw.nona.R;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = findViewById(R.id.recyclerView);

        new MainTask().setTaskListener(new MainTask.TaskListener() {
            @Override
            public void onTaskFinished(final BoxData[] boxDataArray) {
                new ImageLoadTask(MainActivity.this, boxDataArray).setTaskListener(new ImageLoadTask.TaskListener() {
                    @Override
                    public void onTaskFinished(BoxData[] results) {
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setAdapter(new MainAdapter(MainActivity.this, results));
                    }

                    @Override
                    public void onTaskExceptionCanceled(Exception e) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).execute();
            }
        }).execute();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRefresh() {
        new MainTask().setTaskListener(new MainTask.TaskListener() {
            @Override
            public void onTaskFinished(final BoxData[] boxDataArray) {
                new ImageLoadTask(MainActivity.this, boxDataArray).setTaskListener(new ImageLoadTask.TaskListener() {
                    @Override
                    public void onTaskFinished(BoxData[] results) {
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setAdapter(new MainAdapter(MainActivity.this, results));
                    }

                    @Override
                    public void onTaskExceptionCanceled(Exception e) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).execute();
            }
        }).execute();
    }
}
