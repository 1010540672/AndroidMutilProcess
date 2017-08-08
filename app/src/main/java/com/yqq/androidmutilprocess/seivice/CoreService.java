package com.yqq.androidmutilprocess.seivice;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class CoreService extends Service {


    private static final String TAG = "CoreService";

    private Binder mBinderPool = new BinderPool.BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        //自定义permission方式检查权限
        if (checkCallingOrSelfPermission("com.yqq.aidl.permission.REMOTE_SERVICE_PERMISSION") == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG,"权限校验失败");
            return null;
        }
        return mBinderPool;
    }







    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
