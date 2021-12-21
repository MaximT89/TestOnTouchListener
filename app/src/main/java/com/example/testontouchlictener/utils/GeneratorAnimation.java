package com.example.testontouchlictener.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import com.example.testontouchlictener.R;

import java.util.Random;

public class GeneratorAnimation {

    public static View makeShakeAnimation(Context context, View view, int duration){
        RotateAnimation anim = new RotateAnimation(-5, 5, 50, 50);
        anim.setDuration(duration);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        view.startAnimation(anim);
        return view;
    }

    public static int getRandomDuration(){
        return new Random().nextInt(200) + 200;
    }
}
