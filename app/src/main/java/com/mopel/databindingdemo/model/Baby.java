package com.mopel.databindingdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Author: yanhao(amosbake@gmail.com)
 * Date : 2015-09-17
 * Time: 14:21
 */
public class Baby implements Parcelable{
    private static final String TAG = "Baby";
    private String name;//宝宝名字
    private int age;//宝宝年龄
    private SexType mSex;//宝宝性别
    private String note;//宝宝日记内容
    private Date recordTime;//宝宝日记记录时间
    private boolean isLike;//宝宝记录是否点赞

    protected Baby(Parcel in) {
        name = in.readString();
        age = in.readInt();
        note = in.readString();
        recordTime=new Date(in.readLong());
        isLike=in.readInt()==0;
        mSex=SexType.values()[in.readInt()];
    }

    public static final Creator<Baby> CREATOR = new Creator<Baby>() {
        @Override
        public Baby createFromParcel(Parcel in) {
            return new Baby(in);
        }

        @Override
        public Baby[] newArray(int size) {
            return new Baby[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(note);
        dest.writeLong(recordTime.getTime());
        dest.writeInt(isLike ? 0 : 1);
        dest.writeInt(mSex.ordinal());
    }

    public enum SexType{
        GIRL("girl"),
        BOY("boy");
        private String str;

        SexType(String str) {
            this.str = str;
        }

        public static SexType fromString(String str){
            if (str!=null){
                for (SexType sexType :SexType.values()){
                    if (str.equalsIgnoreCase(sexType.str)){
                        return sexType;
                    }
                }
            }
            return null;
        }
    }

    public Baby() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public SexType getSex() {
        return mSex;
    }

    public void setSex(SexType sex) {
        mSex = sex;
    }
}
