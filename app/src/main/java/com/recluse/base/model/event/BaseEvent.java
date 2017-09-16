package com.recluse.base.model.event;

/**
 * Created by recluse on 17-9-13.
 */

public class BaseEvent<T> {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAILED = 1;

    public int uniqueId;
    public int code;
    public T data;

    public BaseEvent() {

    }

    public BaseEvent(int uniqueId, int code, T data) {
        this.uniqueId = uniqueId;
        this.code = code;
        this.data = data;
    }

}
