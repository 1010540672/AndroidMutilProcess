package com.yqq.androidmutilprocess.seivice;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;


import com.yqq.androidmutilprocess.IGetAllInfo;
import com.yqq.androidmutilprocess.MessageReceiver4Test;
import com.yqq.androidmutilprocess.bean.TestInfo;


public class AllInfoImpl extends IGetAllInfo.Stub {

    private static  final String TAG="AllInfoImpl";

    //RemoteCallbackList专门用来管理多进程回调接口
    private RemoteCallbackList<MessageReceiver4Test> listenerList = new RemoteCallbackList<>();




    @Override
    public void reqAllInfo(String name, String content) throws RemoteException {
        //子进程发接口并且回调到主进程 运行在子进程的binder线程池中
        Log.e(TAG,"收到主进程发来的消息====name===="+name);
        Log.e(TAG,"收到主进程发来的消息====content===="+content);

        final int listenerCount = listenerList.beginBroadcast();
        Log.e(TAG, "listenerCount == " + listenerCount);
        for (int i = 0; i < listenerCount; i++) {
            MessageReceiver4Test messageReceiver = listenerList.getBroadcastItem(i);
            if ( null!=messageReceiver) {
                try {
                    Log.e(TAG, "listenerCount ========================================================== ");
                    TestInfo info=new TestInfo();
                    info.mName="子进程"+android.os.Process.myPid()+"---"+System.currentTimeMillis();
                    info.mContent="内容======"+System.currentTimeMillis();
                    messageReceiver.onAllInfoReceiver4Test(info,"子进程返回主进程的ack==="+System.currentTimeMillis());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        listenerList.finishBroadcast();

    }

    @Override
    public void registerReceiveListener(MessageReceiver4Test allInfosCallBack) throws RemoteException {
        listenerList.register(allInfosCallBack);
    }

    @Override
    public void unregisterReceiveListener(MessageReceiver4Test allInfosCallBack) throws RemoteException {
        listenerList.unregister(allInfosCallBack);
    }
}
