package com.dsa.dsadaovang.adapters;

public class AppModel {
    private String mName;
    private String mLink;
    private String mImage;

    public AppModel(String name, String link, String image) {
        mName = name;
        mLink = link;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

}
