package com.oadev.model;

public class TeamModel {
    private String teamTittle;
    private String imglink;
    private String status;

    public TeamModel(String teamTittle, String imglink, String status) {
        this.teamTittle = teamTittle;
        this.imglink = imglink;
        this.status = status;
    }

    public String getImglink() {
        return imglink;
    }

    public String getTeamTittle() {
        return teamTittle;
    }

    public String getStatus() {
        return status;
    }
}

