package com.example.ewejobapp;

//import android.app.Application;
//import android.content.Context;
//
//    public class EWeJobApp extends Application {
//
//        private static EWeJobApp sInstance;
//
//        public static synchronized EWeJobApp getInstance() {
//            return sInstance;
//        }
//
//        public static Context getAppContext() {
//            return sInstance.getApplicationContext();
//        }
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//            sInstance=this;
//        }
//    }

    import android.app.Application;
    import android.text.TextUtils;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.toolbox.ImageLoader;
    import com.android.volley.toolbox.Volley;

public class EWeJobApp extends Application {

    public static final String TAG = EWeJobApp.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static EWeJobApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized EWeJobApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /*
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
     */

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
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
}

