package com.recluse.oclient.event;

import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.ModuleEntity;

/**
 * Created by recluse on 17-9-13.
 */

public class HomePageModuleEvent extends BaseEvent<ModuleEntity> {

    public HomePageModuleEvent(int uniqueId, int code, ModuleEntity data) {
        super(uniqueId, code, data);
    }
}
