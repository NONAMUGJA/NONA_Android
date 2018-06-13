package com.dgsw.nona.data;

import android.os.AsyncTask;

public class ExchangeData {
    private String member;
    private String itemTitle;
    private String count;
    private String comment;
    private String targetBox;

    public ExchangeData(String member, String itemTitle, String count, String comment, String targetBox) {
        this.member = member;
        this.itemTitle = itemTitle;
        this.count = count;
        this.comment = comment;
        this.targetBox = targetBox;
    }

    public String getMember() {
        return member;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getCount() {
        return count;
    }

    public String getComment() {
        return comment;
    }

    public String getTargetBox() {
        return targetBox;
    }
}
