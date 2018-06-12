package com.dgsw.nona.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SubtitleCollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgsw.nona.R;

public class DetailActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView owner = findViewById(R.id.owner);
        TextView comment = findViewById(R.id.comment);

        SubtitleCollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        ImageView foodImage = findViewById(R.id.foodImage);

        Intent intent = getIntent();

        if (!intent.getBooleanExtra("empty", false)) {
            collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));
            collapsingToolbarLayout.setSubtitle(intent.getStringExtra("subTitle"));
            owner.setText(intent.getStringExtra("owner"));
            comment.setText(intent.getStringExtra("comment"));
            if(getIntent().hasExtra("foodImage")) {
                Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("foodImage"), 0, getIntent().getByteArrayExtra("foodImage").length);
                foodImage.setImageBitmap(bmp);
            } else {
                foodImage.setColorFilter(Color.argb(255, 0, 0, 0));
            }
        } else {
            collapsingToolbarLayout.setTitle("비어 있음");
            owner.setText("없음");
            foodImage.setImageDrawable(getDrawable(R.drawable.ic_add));
        }


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fab.setVisibility(View.GONE);
        supportFinishAfterTransition();
    }
}
