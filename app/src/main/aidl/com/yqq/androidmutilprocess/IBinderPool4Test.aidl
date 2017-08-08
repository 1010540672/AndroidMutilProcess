package com.yqq.androidmutilprocess;

//Binder连接池
interface IBinderPool4Test {

    /**
     * @param binderCode, the unique token of specific Binder<br/>
     * @return specific Binder who's token is binderCode.
     */
    IBinder queryBinder(in int binderCode);
}