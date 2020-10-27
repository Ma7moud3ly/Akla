/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.api;

import com.ma7moud3ly.akla.App;
import com.ma7moud3ly.akla.util.CheckInternet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CacheInterceptor {
    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        if (CheckInternet.INSTANCE.isConnected(App.context)) {
            int maxAge = 60; // read from cache for 1 minute
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };
}
