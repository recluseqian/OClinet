package com.recluse.oclient.data;

import java.io.Serializable;
import java.util.List;

public class VideoSubscribeEntity implements Serializable {

    public DataBean data;
    public int code;

    public static class DataBean {

        public int subscribeId;
        public String subscribeName;
        public String subscribeLogo;
        public String description;
        public int subscribeCount;
        public int subscribeArticleCount;
        public int subscribeStatus;
        public Object backgroundImgUrl;
        public Object pageUrl;
        public Object isPush;
        public long dbCreateTime;
        public List<SubscribeListBean> subscribeList;


    }

    public static class SubscribeListBean {

        public Object subId;
        public String contentDesc;
        public int subscribeContentType;
        public String contentTitle;
        public String plid;
        public String subscribeContentId;
        public String quantity;
        public String image;
        public long publishTime;
        public String pageurl;
        public Object weiboUrl;
        public Object weiboName;
        public Object liveStatus;
        public Object startTime;
        public Object courseType;
    }
}
