package com.recluse.oclient.data;

import java.io.Serializable;
import java.util.List;

public class VideoDetailEntity implements Serializable {

    public DataBean data;
    public int code;

    public static class DataBean {

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
        public String includeVirtual;
        public Object ccPic;
        public Object ccUrl;
        public String tags;
        public String source;
        public int hits;
        public String largeImgurl;
        public Object outUrl;
        public long ltime;
        public Object opening;
        public boolean isStore;
        public List<VideoListBean> videoList;
        public List<RecommendListBean> recommendList;




    }

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
        public int hits;
        public int iosShow;
        public int protoVersion;
        public int mp4SdSize;
        public int mp4HdSize;
        public int mp4ShdSize;
        public int mp4SdSizeOrign;
        public int mp4HdSizeOrign;
        public int mp4ShdSizeOrign;
        public int m3u8SdSize;
        public int m3u8HdSize;
        public int m3u8ShdSize;
        public int m3u8SdSizeOrign;
        public int m3u8HdSizeOrign;
        public int m3u8ShdSizeOrign;
        public String mp4SdUrl;
        public String mp4HdUrl;
        public String mp4ShdUrl;
        public String mp4SdUrlOrign;
        public String mp4HdUrlOrign;
        public String mp4ShdUrlOrign;
        public String m3u8SdUrl;
        public String m3u8HdUrl;
        public String m3u8ShdUrl;
        public String m3u8SdUrlOrign;
        public String m3u8HdUrlOrign;
        public String m3u8ShdUrlOrign;
        public String mp4ShareUrl;
        public boolean isStore;
        public List<?> subList;

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
