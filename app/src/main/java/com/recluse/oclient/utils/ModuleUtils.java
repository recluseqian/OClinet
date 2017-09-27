package com.recluse.oclient.utils;

public class ModuleUtils {

    public static String getModuleType(int type) {
        switch (type) {
            case 2:
                return "视频";
            case 4:
                return "文章";
            case 6:
                return "音频";

            default:
                return "";
        }
    }
}
