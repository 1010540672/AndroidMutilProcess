package com.yqq.androidmutilprocess.seivice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.yqq.androidmutilprocess.IGetAllInfo;
import com.yqq.androidmutilprocess.MessageReceiver4Test;
import com.yqq.androidmutilprocess.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class CoreService2 extends Service {


    private static final String TAG = "CoreService2";

    private RemoteCallbackList<MessageReceiver4Test>  mList=new RemoteCallbackList<>();

    @Override
    public void onCreate() {
        super.onCreate();

//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                Log.e(TAG, "onBind555555555");
//                stopSelf();
//            }
//        },0,500);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return  mGetAllInfo;
    }



    private IBinder mGetAllInfo=new IGetAllInfo.Stub(){
        @Override
        public void reqAllInfo(String name, String content) throws RemoteException {



            String processName="进程"+android.os.Process.myPid()+"---"+System.currentTimeMillis();
            Log.e(TAG, "reqAllInfo -->processName"+processName+"name->"+name+"content->"+content);
            Log.e(TAG,"***********************oreqAllInfo所在线程**********************"+ Thread.currentThread().getName());



            /**
             * RemoteCallbackList的遍历方式
             * beginBroadcast和finishBroadcast一定要配对使用
             */
            final int listenerCount = mList.beginBroadcast();
            Log.d(TAG, "listenerCount == " + listenerCount);
            for (int i = 0; i < listenerCount; i++) {
                MessageReceiver4Test messageReceiver = mList.getBroadcastItem(i);
                if (messageReceiver != null) {
                    try {
                        messageReceiver.onReqHotInfoCallBack("======测试回调====");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
            mList.finishBroadcast();




        }

        @Override
        public void registerReceiveListener(MessageReceiver4Test allInfosCallBack) throws RemoteException {

            mList.register(allInfosCallBack);
        }

        @Override
        public void unregisterReceiveListener(MessageReceiver4Test allInfosCallBack) throws RemoteException {

            mList.unregister(allInfosCallBack);
        }

        @Override
        public String reqAllInfo2(String name, String content) throws RemoteException {

            String processName="进程"+android.os.Process.myPid()+"---"+System.currentTimeMillis();
            Log.e(TAG, "reqAllInfo2 -->processName"+processName+"name->"+name+"content->"+content);
            Log.e(TAG,"***********************reqAllInfo2**********************"+ Thread.currentThread().getName());
           // SystemClock.sleep(10000);


            return "耗时操作测试";
        }
    };





    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
