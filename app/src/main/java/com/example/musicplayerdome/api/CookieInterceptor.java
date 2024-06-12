package com.example.musicplayerdome.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {
    private String cookieValue;

    public CookieInterceptor(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        // 从原始请求中获取 URL
        HttpUrl originalUrl = originalRequest.url();

        // 获取原始请求中的查询参数
        String existingCookie = originalUrl.queryParameter("cookie");

        // 创建一个新的 URL，将额外的 Cookie 添加为查询参数
        HttpUrl.Builder urlBuilder = originalUrl.newBuilder();
        if (existingCookie != null) {
            // 如果原始请求中已存在 cookie 参数，则将其保留
            urlBuilder.setQueryParameter("cookie", existingCookie);
        }
        // 添加额外的 cookie 参数
        urlBuilder.addQueryParameter("cookie", cookieValue);

        // 创建一个新的请求，使用新的 URL
        Request modifiedRequest = originalRequest.newBuilder()
                .url(urlBuilder.build())
                .build();

        return chain.proceed(modifiedRequest);
    }
}
