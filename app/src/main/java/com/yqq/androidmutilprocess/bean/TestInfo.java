package com.yqq.androidmutilprocess.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class TestInfo  implements Parcelable{
    public String mName;
    public String mContent;


    public TestInfo(Parcel in) {
        mName = in.readString();
        mContent = in.readString();
    }

    public TestInfo() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestInfo> CREATOR = new Creator<TestInfo>() {
        @Override
        public TestInfo createFromParcel(Parcel in) {
            return new TestInfo(in);
        }

        @Override
        public TestInfo[] newArray(int size) {
            return new TestInfo[size];
        }
    };
}
