package com.ensogo.movie.network;

import android.support.annotation.NonNull;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

public class RequestGenerator
{
    /**
     * Adds default header for all requests
     *
     * @param builder
     */
    private static void addDefaultHeaders(@NonNull Request.Builder builder)
    {
        builder.header("Accept", "application/json");
    }


    /**
     * Builds a get Request object
     *
     * @param strUrl
     */
    public static Request get(@NonNull String strUrl)
    {
        HttpUrl url = addDefaultQueryParams(strUrl);
        Request.Builder builder = new Request.Builder().url(url);
        addDefaultHeaders(builder);
        return builder.build();
    }

    @NonNull
    private static HttpUrl addDefaultQueryParams(@NonNull String strUrl) {
        return HttpUrl.parse(strUrl).newBuilder()
                .setQueryParameter("api_key", "489597e1cc7d9b3455ff12256353c0a7").build();
    }
}
