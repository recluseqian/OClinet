package com.recluse.oclient.data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeModuleInfo extends BaseModuleInfo implements BannerListProvider{
    public String moduleName;
    public int moduleType;
    public int style;
    public int moduleId;
    public Object classifyId;
    public Object classifyType;
    public int hasMore;
    public Object jumpTo;
    public int weight;
    public List<HomeModuleSubInfo> items;

    public boolean hasMore() {
        return hasMore != 0;
    }

    @Expose
    private List<BannerInfo> mBannerInfoList;
    @Override
    public List<BannerInfo> getBannerList() {
        if (mBannerInfoList == null) {
            mBannerInfoList = new ArrayList<>();
        }
        return mBannerInfoList;
    }
}
