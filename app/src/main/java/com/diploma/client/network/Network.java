package com.diploma.client.network;

import android.content.Context;
import android.os.Build;
import android.webkit.ValueCallback;

import androidx.annotation.RequiresApi;

import com.diploma.client.data.model.Picture;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;

import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Network {
    static final String homeUrl = "http://192.168.31.207:8000/api/";


    static CookieJar cookieJar = new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };
    static OkHttpClient httpClient = new OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build();

    public static String doGetRequest(String url) throws IOException, SecurityException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Response response = httpClient.newCall(request).execute();


        if (((int) response.code()) == 200) {
            return response.body().string();
        } else if (((int) response.code()) == 403) {
            throw new SecurityException("invalid login data");
        } else
            throw new IOException(response.message());
    }

    public static String doPostRequest(String url, String json) throws IOException, SecurityException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = httpClient.newCall(request).execute();
        if (((int) response.code()) == 200 || ((int) response.code()) == 201) { // 201 = creation
            return response.body().string();
        } else if (((int) response.code()) == 403) {
            throw new SecurityException("Invalid user data");
        } else
            throw new IOException(response.message());
    }


}
