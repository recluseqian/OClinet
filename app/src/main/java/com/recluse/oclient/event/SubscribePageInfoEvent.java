package com.recluse.oclient.event;

import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.SubscribeEntity;

/**
 * Created by recluse on 17-9-15.
 */

public class SubscribePageInfoEvent extends BaseEvent<SubscribeEntity> {

    public SubscribePageInfoEvent(int uniqueId, int code, SubscribeEntity data) {
        super(uniqueId, code, data);
    }
}
