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

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    private int boxNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        TextView owner = findViewById(R.id.owner);
        TextView comment = findViewById(R.id.comment);
        TextView state = findViewById(R.id.state);

        SubtitleCollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        ImageView foodImage = findViewById(R.id.foodImage);

        final Intent intent = getIntent();

        if (!intent.getBooleanExtra("empty", false)) {
            collapsingToolbarLayout.setTitle(intent.getStringExtra("title"));
            collapsingToolbarLayout.setSubtitle(intent.getStringExtra("subTitle"));
            boxNo = intent.getIntExtra("boxNo", 0);
            owner.setText(intent.getStringExtra("owner"));
            comment.setText(intent.getStringExtra("comment"));
            if (!getIntent().getStringExtra("receiver").isEmpty()) {
                state.setText(String.format(Locale.KOREA, "%s님과 교환 중...", getIntent().getStringExtra("receiver")));
            } else {
                state.setText("교환 상대를 찾는 중...");
            }
            if (getIntent().hasExtra("foodImage")) {
                Bitmap bmp = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("foodImage"), 0, getIntent().getByteArrayExtra("foodImage").length);
                foodImage.setImageBitmap(bmp);
            } else {
                foodImage.setColorFilter(Color.argb(255, 0, 0, 0));
            }
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(DetailActivity.this, RequestActivity.class);
                    intent1.putExtra("boxNo", boxNo);

                    startActivity(intent1);
                }
            });
        } else {
            collapsingToolbarLayout.setTitle("비어 있음");
            owner.setText("없음");
            state.setText("비어 있음");

            foodImage.setImageDrawable(getDrawable(R.drawable.ic_add));
            floatingActionButton.setImageDrawable(getDrawable(R.drawable.ic_add));
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                    intent.putExtra("boxNo", boxNo);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        floatingActionButton.setVisibility(View.GONE);
        supportFinishAfterTransition();
    }
}
