package com.recluse.oclient.data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class SubscribeModuleInfo implements Serializable{

    public int subId;
    public int subscribeId;
    public String subscribeName;
    public String subscribeLogo;
    public String subscribeContentId;
    public int subscribeContentType;
    public String pageurl;
    public String contentTitle;
    public String contentDesc;
    public String plid;
    public String quantity;
    public String image;
    public int viewCount;
    public int commentCount;
    public int ownerRole;
    public long publishTime;
    public double score;

    public String description;
    public int subscribeCount;
    public int subscribeArticleCount;
    public int subscribeStatus;

    public List<SubscribeModuleInfo> subscribeList;

    @Expose
    public List<BannerInfo> mBannerInfoList;
}
