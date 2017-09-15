// IGetAllInfo.aidl
package com.yqq.androidmutilprocess;
//aidl接口导包
import com.yqq.androidmutilprocess.MessageReceiver4Test;
interface IGetAllInfo {
            //请求获取所有信息
     void reqAllInfo(in String name, in String content);
      String reqAllInfo2(in String name, in String content);
     //接口回调注册
     void registerReceiveListener(in MessageReceiver4Test allInfosCallBack);
     //接口回调解注册
     void unregisterReceiveListener(in MessageReceiver4Test allInfosCallBack);

}
