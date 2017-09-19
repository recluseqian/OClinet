package com.recluse.oclient.data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by recluse on 17-9-13.
 */

public class HomeModuleInfo implements Serializable {
    public String moduleName;
    public int moduleType;
    public int style;
    public int moduleId;
    public Object classifyId;
    public Object classifyType;
    public int hasMore;
    public Object jumpTo;
    public int weight;
    public List<HomeSubModuleInfo> items;

    public boolean hasMore() {
        return hasMore != 0;
    }

    @Expose
    public List<BannerInfo> mBannerInfoList;
}
