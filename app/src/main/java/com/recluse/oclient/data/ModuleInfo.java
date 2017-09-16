package com.recluse.oclient.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by recluse on 17-9-13.
 */

public class ModuleInfo implements Serializable{
    public String moduleName;
    public int moduleType;
    public int style;
    public int moduleId;
    public Object classifyId;
    public Object classifyType;
    public int hasMore;
    public Object jumpTo;
    public int weight;
    public List<ModuleItemInfo> items;

    public boolean hasMore() {
        return hasMore != 0;
    }
}
