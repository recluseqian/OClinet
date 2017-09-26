package com.recluse.oclient.data;


import java.util.List;

public class VideoDetailInfo {

    public String title;
    public String subtitle;
    public String plid;
    public String imgPath;
    public String school;
    public String director;
    public int playCount;
    public int updatedPlayCount;
    public String type;
    public String description;
    public String tags;
    public String source;
    public List<VideoListBean> videoList;
    public List<RecommendListBean> recommendList;

    public static class VideoListBean {

        public String mid;
        public String plid;
        public String title;
        public int mLength;
        public String imgPath;
        public int pNumber;
        public String commentId;
        public String subtitle;
        public String subtitleLanguage;
        public String webUrl;
        public String shortWebUrl;
        public String mp4SdUrl;
        public String mp4HdUrl;
    }

    public static class RecommendListBean {

        public String plid;
        public String rid;
        public int rtype;
        public String title;
        public String description;
        public String courseType;
        public String tagBgColor;
        public String quantity;
        public long publishTime;
        public String picUrl;
        public String pageUrl;
        public int viewcount;
        public int userStore;

    }
}
