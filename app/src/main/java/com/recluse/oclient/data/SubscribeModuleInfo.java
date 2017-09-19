package com.recluse.oclient.data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by recluse on 17-9-15.
 */

public class SubscribeModuleInfo implements Serializable{
    public int subId;
    public int subscribeId;
    public String subscribeName;
    public String subscribeLogo;
    public String subscribeContentId;
    public int subscribeContentType;
    public Object courseType;
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
    public Object weiboUrl;
    public Object weiboName;
    public Object liveStatus;
    public Object startTime;
    public int isStore;
    public int isSubscribe;
    public int isRecommend;
    public double score;
    public Object subscribers;
    public int cacheType;
    public int index;

    @Expose
    public List<BannerInfo> mBannerInfoList;
}