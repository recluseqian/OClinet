package com.recluse.oclient.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by recluse on 17-9-15.
 */

public class CookieManager implements CookieJar {

    private static Map<String, List<Cookie>> sCache = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        sCache.put(url.toString(), cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return sCache.get(url.toString());
    }
}
