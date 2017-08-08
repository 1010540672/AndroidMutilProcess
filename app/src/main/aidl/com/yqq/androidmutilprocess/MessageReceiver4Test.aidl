// MessageReceiver4Yjq.aidl
package com.yqq.androidmutilprocess;

import com.yqq.androidmutilprocess.bean.TestInfo;

interface MessageReceiver4Test {

        //获取的信息用于主进程使用
        void onAllInfoReceiver4Test(in TestInfo testInfos,in String rs);

          //发送获取信息后的反馈主进程使用
        void onReqHotInfoCallBack(in String rs);

}
