package com.example.firebasecrud;

public class PostModel {
    private String postid;
    private String text;


    public PostModel() {
    }

    public PostModel(String postid, String text) {
        this.postid = postid;
        this.text = text;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
