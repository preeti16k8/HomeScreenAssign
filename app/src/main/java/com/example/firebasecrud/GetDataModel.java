package com.example.firebasecrud;

import java.util.List;

public class GetDataModel {
    private String userid;
    private List<PostModel> postModelList;

    public GetDataModel() {
    }

    public GetDataModel(String userid, List<PostModel> postModelList) {
        this.userid = userid;
        this.postModelList = postModelList;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<PostModel> getPostModelList() {
        return postModelList;
    }

    public void setPostModelList(List<PostModel> postModelList) {
        this.postModelList = postModelList;
    }
}
