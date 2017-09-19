package com.recluse.oclient.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by recluse on 17-9-18.
 */

public class BannerInfoEntity implements Serializable{
    public String message;
    public int code;
    public List<BannerInfo> data;
}
