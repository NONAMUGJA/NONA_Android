package com.dgsw.nona.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dgsw.nona.R;
import com.dgsw.nona.data.Data;
import com.dgsw.nona.task.AddTask;
import com.dgsw.nona.task.ImageUploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private ImageView add_image;

    private int boxNo;

    public static final int PICK_GALLERY = 0;
    public static final int PICK_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add);

        Button add_btn = findViewById(R.id.add_button);
        add_image = findViewById(R.id.add_image);
        final EditText add_food = findViewById(R.id.add_food);
        final EditText add_count = findViewById(R.id.add_count);
        final EditText add_comment = findViewById(R.id.add_comment);
        final EditText add_lock = findViewById(R.id.add_lock);

        boxNo = getIntent().getIntExtra("boxNo", 0);

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(AddActivity.this, R.style.AppTheme_Dialog));
                builder.setMessage("이미지 선택");
                builder.setPositiveButton("사진 촬영", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takePhotoFromCamera();
                    }
                });
                builder.setNeutralButton("앨범 선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choosePhotoFromGallery();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        View.OnClickListener AddListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Intent nextIntent = new Intent(AddActivity.this, MainActivity.class);
                final String food = add_food.getText().toString();
                final String count = add_count.getText().toString();
                final String comment = add_comment.getText().toString();
                final String lock = add_lock.getText().toString();

                if (Check(v, food, count, comment, lock) == 1) {
                   /* new ImageUploadTask().setTaskListener(new ImageUploadTask.TaskListener() {
                        @Override
                        public void onTaskFinished(String result) {*/
                            new AddTask().setTaskListener(new AddTask.TaskListener() {
                                @Override
                                public void onTaskFinished(Boolean result) {
                                    if (result) {
                                        Toast.makeText(getApplicationContext(), "등록 됨", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            }).execute(Integer.toString(boxNo), Data.getUserID(), System.currentTimeMillis()+".jpg", food, count, comment, lock);
                        /*}
                    }).execute((BitmapDrawable) add_image.getDrawable());*/
                }
            }
        };

        add_btn.setOnClickListener(AddListener);
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, PICK_GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    add_image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == PICK_CAMERA) {
            if (data.getExtras() != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                add_image.setImageBitmap(thumbnail);
            }
        }
    }

    private int Check(View view, String food, String count, String comment, String lock) {

        if (food.isEmpty()) {
            Snackbar.make(view, "음식 이름을 입력하십시오.", Snackbar.LENGTH_LONG).show();
            return -1;
        } else if (count.isEmpty()) {
            Snackbar.make(view, "개수를 입력하십시오.", Snackbar.LENGTH_LONG).show();
            return -1;
        } else if (comment.isEmpty()) {
            Snackbar.make(view, "하고싶은 말을 입력하십시오.", Snackbar.LENGTH_LONG).show();
            return -1;
        } else if (lock.isEmpty()) {
            Snackbar.make(view, "자물쇠 번호를 입력하십시오.", Snackbar.LENGTH_LONG).show();
            return -1;
        }
        return 1;
    }
}
