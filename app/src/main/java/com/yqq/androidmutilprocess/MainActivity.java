package com.yqq.androidmutilprocess;


import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yqq.androidmutilprocess.bean.TestInfo;
import com.yqq.androidmutilprocess.seivice.AllInfoImpl;
import com.yqq.androidmutilprocess.seivice.BinderPool;
import com.yqq.androidmutilprocess.utils.ThreadPoolUtils;

import java.lang.ref.WeakReference;

/**
 * IPC  支持的数据类型 java基本类型  list  map parcable
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private final MessageReceived mMessageReceived=new MessageReceived(this);
    private IGetAllInfo mAllInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupService();



    }


    static  class MessageReceived extends MessageReceiver4Test.Stub{
        private WeakReference<MainActivity> mOutCtx;
     public    MessageReceived(MainActivity ctx){
         mOutCtx=new WeakReference<MainActivity>(ctx);
        }

        @Override
        public void onAllInfoReceiver4Test(TestInfo info, String rs) throws RemoteException {
            Log.e(TAG,"info==="+info.mName+info.mContent);
            //运行在客户端binder线程池
            if(null!=mOutCtx&&null!=mOutCtx.get()){
                mOutCtx.get().doTest(rs);
            }
        }

        @Override
        public void onReqHotInfoCallBack(String rs) throws RemoteException {
            //运行在客户端binder线程池
            if(null!=mOutCtx&&null!=mOutCtx.get()){
                mOutCtx.get().doTest(rs);
            }
        }
    }


    private void doTest(final String rs){
        Log.e(TAG,"服务端返回的数据===="+rs);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,rs,Toast.LENGTH_LONG).show();
            }
        });
    }



    private void setupService() {

        Log.e(TAG,"setupService ======= 所在的进程ID "+android.os.Process.myPid());
        Log.e(TAG,"***********************setupService**所在线程**********************"+ Thread.currentThread().getName());
        //使用binder连接池方式处理
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInsance(MainActivity.this);
                IBinder securityBinder = binderPool
                        .queryBinder(BinderPool.BINDER_ALL_INFO);
                mAllInfo =  AllInfoImpl
                        .asInterface(securityBinder);
                try {
                    mAllInfo.registerReceiveListener(mMessageReceived);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


    }


      //主进程客户端调用
    private void getAllInfo4IPC(final String name, final String content){
        ThreadPoolUtils.execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"======binder 发送请求======");
                if(null==mAllInfo) {
                    setupService();
                }
                if(null!=mAllInfo){
                    try {
                        Log.e(TAG,"======binder 发送请求2======");
                        mAllInfo.reqAllInfo(name,content);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }

                }
            }
        });


    }



    public void doTest(View v){
        getAllInfo4IPC("CodeMonkey","========这是一个IPC测试========");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除监听接口
        if ( null!=mAllInfo && mAllInfo.asBinder().isBinderAlive()) {
            try {
                mAllInfo.unregisterReceiveListener(mMessageReceived);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
