package com.jiedai.customview;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Create by yuheng
 * date：2019/4/16
 * description：
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
