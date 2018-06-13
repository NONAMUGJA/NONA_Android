package com.dgsw.nona.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgsw.nona.activity.DetailActivity;
import com.dgsw.nona.R;
import com.dgsw.nona.data.BoxData;
import com.dgsw.nona.viewholder.MainViewHolder;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private WeakReference<Activity> activityWeakReference;

    private BoxData[] boxDataArray;

    public MainAdapter(Activity activity, BoxData[] boxDataArray) {
        this.activityWeakReference = new WeakReference<>(activity);
        this.boxDataArray = boxDataArray;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_box, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MainViewHolder holder, final int position) {
        BoxData boxData = null;
        if (boxDataArray != null)
        boxData = boxDataArray[position];

        if (boxData != null) {
            Drawable drawable = boxData.getDrawable();
            if (drawable != null)
                holder.foodImage.setImageDrawable(drawable);
            else
                holder.foodImage.setImageDrawable(activityWeakReference.get().getDrawable(R.drawable.ic_image_none));

            holder.foodName.setText(boxData.getFoodName());
            holder.foodCount.setText(boxData.getFoodCount());
        }

        final BoxData finalBoxData = boxData;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityWeakReference.get(), DetailActivity.class);
                if (finalBoxData != null) {
                    //FIXME userName
                    intent.putExtra("owner", finalBoxData.getUserID());
                    intent.putExtra("comment", finalBoxData.getComment());
                    intent.putExtra("title", finalBoxData.getFoodName());
                    intent.putExtra("subTitle", finalBoxData.getFoodCount());
                    intent.putExtra("receiver", finalBoxData.getReceiverID());

                    Drawable drawable = finalBoxData.getDrawable();

                    if (drawable instanceof BitmapDrawable) {
                        Bitmap bitmap = ((BitmapDrawable) holder.foodImage.getDrawable()).getBitmap();
                        if (bitmap != null) {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            intent.putExtra("foodImage", byteArrayOutputStream.toByteArray());
                        }
                    }
                } else {
                    intent.putExtra("empty", true);
                }
                intent.putExtra("boxNo", holder.getAdapterPosition());
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(activityWeakReference.get(), v, "imageView");

                activityWeakReference.get().startActivity(intent, activityOptions.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
