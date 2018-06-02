package com.example.halilgnal.mathsolver.util;

import android.content.Context;
import android.content.res.Resources;

import com.example.halilgnal.mathsolver.App;
import com.example.halilgnal.mathsolver.MainActivity;
import com.example.halilgnal.mathsolver.R;

import java.util.ArrayList;
import java.util.Arrays;
import android.app.Application;

public final class QUtil  {

    public static final int itsVeryEasyMin = 1, itsVeryEasyMax = 10;
    public static final int itsEasyMin = 10, itsEasyMax = 100;
    public static final int itsNormalMin = 100, itsNormalMax = 300;
    public static final int itsHardMin = 300, itsHardMax = 1000;
    public static final int itsVeryHardMin = 1000, itsVeryHardMax = 10000;

    public static final int itsArrayLength = 6;

    public static final ArrayList<Integer> itsHardValues = new ArrayList<>(Arrays.asList(25, 50, 75, 100));

    public QUtil() {
    }



    public enum GameLevel {

        VERY_EASY (App.getContext().getResources().getString(R.string.VERY_EASY)),
        EASY (App.getContext().getResources().getString(R.string.EASY)),
        NORMAL (App.getContext().getResources().getString(R.string.NORMAL)),
        HARD (App.getContext().getResources().getString(R.string.HARD)),
        VERY_HARD (App.getContext().getResources().getString(R.string.VERY_HARD));

        private final String name;

        GameLevel(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            // (otherName == null) check is not needed because name.equals(null) returns false
            return name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }
    }

    public static int generateNumber(int min, int max) {
        int range = Math.abs(max - min) + 1;
        return (int) (Math.random() * range) + (min <= max ? min : max);
    }

    public static boolean containsValue(int[] array,int value){

        int i = 0;
        for (; i < array.length; i++) {
            if(array[i] == value){
                return true;
            }
        }
        return false;
    }
}
