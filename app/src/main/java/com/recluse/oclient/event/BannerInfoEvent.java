package com.recluse.oclient.event;

import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.BannerInfoEntity;

/**
 * Created by recluse on 17-9-18.
 */

public class BannerInfoEvent extends BaseEvent<BannerInfoEntity> {

    public BannerInfoEvent(int uniqueId, int code, BannerInfoEntity data) {
        super(uniqueId, code, data);
    }
}
