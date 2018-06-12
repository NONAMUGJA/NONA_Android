package com.dgsw.nona.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgsw.nona.R;

public class MainViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView foodImage;
    public TextView foodName;
    public TextView foodCount;

    public MainViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        foodImage = itemView.findViewById(R.id.food_image);
        foodName = itemView.findViewById(R.id.food_name);
        foodCount = itemView.findViewById(R.id.food_count);
    }
}
