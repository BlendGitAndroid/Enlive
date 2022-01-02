package com.blend.base.net;

import android.app.Application;

import com.blend.base.net.cookie.BlendCookieStore;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class RxHttpManager {

    /**
     * OkHttp 初始化
     *
     * @param context
     * @return
     */
    public static OkHttpClient init(Application context) {
        final int timeout = 20;
        File file = new File(context.getExternalCacheDir(), "RxHttpCookie");
        OkHttpClient client = null;
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .callTimeout(timeout, TimeUnit.SECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(new BlendCookieStore(file))
                .build();
        return client;
    }

}
