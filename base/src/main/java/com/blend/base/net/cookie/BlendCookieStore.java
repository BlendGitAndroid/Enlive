package com.blend.base.net.cookie;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.internal.cache.DiskLruCache;


public class BlendCookieStore implements ICookieJar {

    //TODO 这个cookie是什么

    private File directory;
    private long maxSize;
    private DiskLruCache diskCache; //磁盘缓存
    private Map<String, List<Cookie>> memoryCache; //内存缓存

    public BlendCookieStore(File directory) {
        this(directory, Integer.MAX_VALUE, true);
    }

    /**
     * 配置Cookie存储策略，注意：内存缓存、磁盘缓存至少要开启一个，否则抛出非法异常
     *
     * @param directory    磁盘缓存目录，传入null，代表不开启磁盘缓存
     * @param maxSize      磁盘缓存最大size，默认为Integer.MAX_VALUE
     * @param enableMemory 是否开启内存缓存
     */
    public BlendCookieStore(File directory, long maxSize, boolean enableMemory) {
        if (!enableMemory && directory == null) {
            throw new IllegalArgumentException("memory or disk cache must be enable");
        }
        if (enableMemory) {
            memoryCache = new ConcurrentHashMap<>();
        }
        this.directory = directory;
        this.maxSize = maxSize;
    }

    /**
     * 保存url对应的cookie，线程安全，若开启了磁盘缓存，建议在子线程调用
     *
     * @param url     HttpUrl
     * @param cookies Cookie
     */
    @Override
    public void saveCookie(HttpUrl url, List<Cookie> cookies) {


    }

    @Override
    public void saveCookie(HttpUrl url, Cookie cookie) {

    }

    @Override
    public List<Cookie> loadCookie(HttpUrl url) {
        return null;
    }

    @Override
    public void removeCookie(HttpUrl url) {

    }

    @Override
    public void removeAllCookie() {

    }
}
