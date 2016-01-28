package com.dsa.dsadaovang.adapters;

public class MoneyModel {
    private String mId;
    private String mMac;
    private String mName;
    private String mLevel;
    private String mMoney;

    public MoneyModel(String id, String mac, String name, String level,
            String money) {
        this.mId = id;
        this.mMac = mac;
        this.mName = name;
        this.mLevel = level;
        this.mMoney = money;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getMac() {
        return mMac;
    }

    public void setMac(String mac) {
        this.mMac = mac;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        this.mLevel = level;
    }

    public String getMoney() {
        return mMoney;
    }

    public void setMoney(String money) {
        this.mMoney = money;
    }
}
