package com.example.halilgnal.mathsolver;

import com.example.halilgnal.mathsolver.util.QUtil;

public class GameImpl implements Game {

    private int[] itsNumbers;
    private int itsTarget;
    private QUtil.GameLevel itsLevel;
    private String itsSolution;

    GameImpl(QUtil.GameLevel theLevel){
        itsLevel = theLevel;
    }

    @Override
    public int[] getNumbers() {
        return itsNumbers;
    }

    @Override
    public int getTarget() {
        return itsTarget;
    }

    @Override
    public QUtil.GameLevel getLevel() {
        return itsLevel;
    }

    @Override
    public String getSolution() {
        return itsSolution;
    }

    @Override
    public void updateGame(int[] theNumbers, int theTarget, String theSolution) {
        itsNumbers = theNumbers;
        itsTarget = theTarget;
        itsSolution = theSolution;
    }
}
