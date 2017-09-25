package com.recluse.oclient.data;


import java.util.List;

public class VideoInfo {

    public int type;
    public String title;

    public VideoInfo(int type) {
        this.type = type;
    }

    public VideoSubscribeEntity.DataBean mSubscribeInfo;

    public VideoDetailEntity.DataBean mVideoDescInfo;

    public List<VideoDetailEntity.VideoListBean> mVideoList;

    public List<VideoSubscribeEntity.SubscribeListBean> mSubscribeRecomList;

    public List<VideoDetailEntity.RecommendListBean> mVideoRecomList;

}
