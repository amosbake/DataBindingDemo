package com.mopel.databindingdemo.util;

import android.util.Log;

import com.mopel.databindingdemo.model.Baby;

import java.util.Date;
import java.util.Random;

/**
 * Author: yanhao(amosbake@gmail.com)
 * Date : 2015-09-17
 * Time: 16:07
 */
public class ModelUtil {
    private static final String TAG = "ModelUtil";
    private static int num=0;

    public static int generateRandomInt(int range) {
        return new Random().nextInt(range);
    }

    private static String generatRandomString(int length) {
        String str = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);//0~61
            sf.append(str.charAt(number));


        }
        return sf.toString();
    }

    public static Baby createBaby() {
        Baby baby = new Baby();
        baby.setName(generatRandomString(4));
        baby.setAge(generateRandomInt(8));
        baby.setSex(num % 2== 0 ? Baby.SexType.BOY : Baby.SexType.GIRL);
        baby.setNote(generatRandomString(32));
        baby.setRecordTime(new Date());
        baby.setIsLike(num % 2 == 0 ? true : false);
        num++;
        return baby;
    }


}
