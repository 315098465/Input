package com.pvirtech.licenseplate.input;

/**
 * Created by pd on 2016/8/25.
 */
public class MyApplication extends android.app.Application {
    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*程序崩溃生成日志*/
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
    }

}
