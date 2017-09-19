package com.recluse.oclient.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by recluse on 17-9-13.
 */

public class HomeModuleEntity implements Serializable {
    public int code;
    public List<HomeModuleInfo> data;
}
