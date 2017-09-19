package com.recluse.oclient.event;

import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.HomeModuleEntity;

/**
 * Created by recluse on 17-9-13.
 */

public class HomePageModuleEvent extends BaseEvent<HomeModuleEntity> {

    public HomePageModuleEvent(int uniqueId, int code, HomeModuleEntity data) {
        super(uniqueId, code, data);
    }
}
