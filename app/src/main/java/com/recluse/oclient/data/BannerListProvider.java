package com.recluse.oclient.data;

import java.util.List;

public interface BannerListProvider extends ModuleInfo{
    List<BannerInfo> getBannerList();
}
