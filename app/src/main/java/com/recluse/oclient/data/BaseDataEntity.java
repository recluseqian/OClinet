package com.recluse.oclient.data;

import java.io.Serializable;

/**
 * Created by recluse on 17-9-25.
 */

public class BaseDataEntity<T> implements Serializable {
    public String message;
    public int code;
    public T data;
}
