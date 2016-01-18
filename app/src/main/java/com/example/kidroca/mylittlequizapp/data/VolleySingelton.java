package com.example.kidroca.mylittlequizapp.data;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.kidroca.mylittlequizapp.QuizApplication;
import com.example.kidroca.mylittlequizapp.authentication.models.Token;
import com.example.kidroca.mylittlequizapp.data.auth.Session;

/**
 * Created by kidroca on 18.1.2016 г..
 */
public class VolleySingelton {

    public static final String TAG = VolleySingelton.class.getSimpleName();

    private static VolleySingelton sInstance = null;

    private Token authToken;

    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    private VolleySingelton() {
        mRequestQueue = Volley.newRequestQueue(QuizApplication.getAppContext());
    }

    public synchronized static VolleySingelton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingelton();
        }

        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(QuizApplication.getAppContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(getRequestQueue(), new VolleyImageCache());
        }

        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Token getAuthToken() {
        if (authToken == null) {
            authToken = Session.getsInstance().getToken();
        }

        return authToken;
    }

    private class VolleyImageCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> cache = new LruCache<>(
                (int) (Runtime.getRuntime().maxMemory()/1024/8));

        @Override
        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    }
}
