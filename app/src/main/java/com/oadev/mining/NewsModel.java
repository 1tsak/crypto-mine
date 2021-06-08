package com.oadev.mining;

public class NewsModel {
    private String newsTittle;
    private String imglink;
    private String time;
    private String content;

    public NewsModel(String newsTittle, String imglink, String time, String content) {
        this.newsTittle = newsTittle;
        this.imglink = imglink;
        this.time = time;
        this.content = content;
    }

    public String getNewsTittle() {
        return newsTittle;
    }

    public String getImglink() {
        return imglink;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
