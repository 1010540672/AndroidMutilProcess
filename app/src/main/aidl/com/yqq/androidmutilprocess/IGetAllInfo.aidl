// IGetAllInfo.aidl

package com.yqq.androidmutilprocess;


import com.yqq.androidmutilprocess.MessageReceiver4Test;
interface IGetAllInfo {


            //请求获取所有信息
     void reqAllInfo(in String name, in String content);


       void registerReceiveListener(in MessageReceiver4Test allInfosCallBack);

        void unregisterReceiveListener(in MessageReceiver4Test allInfosCallBack);

}
