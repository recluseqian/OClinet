package com.recluse.oclient.event;

import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.VideoSubscribeEntity;

/**
 * Created by recluse on 17-9-22.
 */

public class VideoSubscribeEvent extends BaseEvent<VideoSubscribeEntity> {

    public VideoSubscribeEvent(int uniqueId, int code, VideoSubscribeEntity data) {
        super(uniqueId, code, data);
    }
}
