package com.diploma.client.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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
        try {
            okhttp3.Response response = httpClient.newCall(request).execute();
            if (((int) response.code()) == 200) {
                return response.body().string();
            } else if (((int) response.code()) == 403) {
                throw new SecurityException("invalid login data");
            } else
                throw new IOException(response.message());
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IOException("idk");
    }

    public static String doPostRequest(String url, String json) throws IOException, SecurityException {
        String csrfmiddlewaretoken = "dummy";
        for (Cookie cookie : cookieJar.loadForRequest(HttpUrl.parse(url))) {
            if (cookie.name().contains("csrf")) {
                csrfmiddlewaretoken = cookie.value();
                break;
            }
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("X-CSRFToken", csrfmiddlewaretoken)
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
