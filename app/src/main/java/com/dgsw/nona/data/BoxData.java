package com.dgsw.nona.data;

import android.graphics.drawable.Drawable;

public class BoxData {
    private int boxNo;
    private String userID;
    //private String imageID;
    private String comment;
    private String receiverID;
    private String foodName;
    private String foodCount;
    private String lockPW;
    private Drawable drawable;

    public BoxData(int boxNo, String userID, String comment, String receiverID, String foodName, String foodCount, String lockPW, Drawable drawable) {
        this.boxNo = boxNo;
        this.userID = userID;
        this.comment = comment;
        this.receiverID = receiverID;
        this.foodName = foodName;
        this.foodCount = foodCount;
        this.lockPW = lockPW;
        this.drawable = drawable;
    }

    public int getBoxNo() {
        return boxNo;
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

    public Drawable getDrawable() {
        return drawable;
    }
}
