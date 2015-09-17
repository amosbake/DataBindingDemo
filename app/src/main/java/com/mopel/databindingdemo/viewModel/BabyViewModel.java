package com.mopel.databindingdemo.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.mopel.databindingdemo.R;
import com.mopel.databindingdemo.model.Baby;

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
        return mBaby.isLike() ? Color.RED : Color.TRANSPARENT;
    }

    public View.OnClickListener onClickBaby() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mBaby.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener onClickLike(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaby.setIsLike(!mBaby.isLike());
            }
        };
    }
}
