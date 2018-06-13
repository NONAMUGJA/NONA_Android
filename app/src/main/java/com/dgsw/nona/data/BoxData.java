package com.dgsw.nona.data;

import android.graphics.drawable.Drawable;

public class BoxData {
    private String userID;
    private String imageID;
    private String comment;
    private String receiverID;
    private String foodName;
    private String foodCount;
    private String lockPW;
    private Drawable drawable;

    public BoxData(String userID, String imageID, String comment, String receiverID, String foodName, String foodCount, String lockPW, Drawable drawable) {
        this.userID = userID;
        this.imageID = imageID;
        this.comment = comment;
        this.receiverID = receiverID;
        this.foodName = foodName;
        this.foodCount = foodCount;
        this.lockPW = lockPW;
        this.drawable = drawable;
    }

    public String getUserID() {
        return userID;
    }

    public String getComment() {
        return comment;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodCount() {
        return foodCount;
    }

    public String getLockPW() {
        return lockPW;
    }

    public String getImageID() {
        return imageID;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
