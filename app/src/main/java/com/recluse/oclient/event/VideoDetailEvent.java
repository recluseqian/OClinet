package com.recluse.oclient.event;

import com.recluse.base.model.event.BaseEvent;
import com.recluse.oclient.data.VideoDetailEntity;

public class VideoDetailEvent extends BaseEvent<VideoDetailEntity> {

    public VideoDetailEvent(int uniqueId, int code, VideoDetailEntity data) {
        super(uniqueId, code, data);
    }
}
