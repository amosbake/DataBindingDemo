package com.mopel.databindingdemo.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.mopel.databindingdemo.BR;
import com.mopel.databindingdemo.R;
import com.mopel.databindingdemo.model.Baby;
import com.mopel.databindingdemo.util.TintedBitmapDrawable;

/**
 * Author: yanhao(amosbake@gmail.com)
 * Date : 2015-09-17
 * Time: 14:36
 */
public class BabyViewModel extends BaseObservable {
    private static final String TAG = "BabyViewModel";
    private Context mContext;
    private Baby mBaby;

    public BabyViewModel(Context context, Baby baby) {
        mContext = context;
        mBaby = baby;
    }
    @Bindable
    public String getBabyName() {
        return mBaby.getName();
    }

    public Drawable getBabySex() {
        if (mBaby.getSex().compareTo(Baby.SexType.BOY) == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return mContext.getResources().getDrawable(R.mipmap.sex_boy, mContext.getTheme());
            }
            return mContext.getResources().getDrawable(R.mipmap.sex_boy);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return mContext.getResources().getDrawable(R.mipmap.sex_girl, mContext.getTheme());
            }
            return mContext.getResources().getDrawable(R.mipmap.sex_girl);
        }
    }

    public int getBabySexIcon() {
        return mBaby.getSex().compareTo(Baby.SexType.BOY) == 0 ? R.mipmap.sex_boy : R.mipmap.sex_girl;
    }

    public String getBabyAge() {
        return mBaby.getAge() + "岁";
    }

    public String getBabyRecord() {
        return mBaby.getNote();
    }

    public String getRecordTime() {
        return DateUtils.formatDateTime(mContext, mBaby.getRecordTime().getTime(), DateUtils.FORMAT_NUMERIC_DATE);
    }

    public String getRecordContent() {
        return mBaby.getNote();
    }

    public int getLikeColor() {
        return mContext.getResources().getColor(mBaby.isLike()?android.R.color.holo_red_light:android.R.color.holo_orange_light);
    }

    public Drawable getLike(){
        Drawable tintDrawable=new TintedBitmapDrawable(mContext.getResources(),R.drawable.heart,mContext.getResources().getColor(mBaby.isLike()?android.R.color.holo_red_light:android.R.color.holo_blue_bright));
        return tintDrawable;
    }

    public View.OnClickListener onClickBaby() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mBaby.toString(), Toast.LENGTH_SHORT).show();
                mBaby.setName("abc");
                notifyPropertyChanged(BR.babyName);
            }
        };
    }


}
