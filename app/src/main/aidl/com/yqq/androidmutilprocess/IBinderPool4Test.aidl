package com.yqq.androidmutilprocess;

//Binder连接池  IBinderPool4Test.aidl
interface IBinderPool4Test {

    /**
     * @param binderCode, the unique token of specific Binder<br/>
     * @return specific Binder who's token is binderCode.
     */
    IBinder queryBinder(in int binderCode);
}