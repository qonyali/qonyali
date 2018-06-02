package com.example.halilgnal.mathsolver;

import com.example.halilgnal.mathsolver.util.QUtil;

public interface Game {
    int[] getNumbers();
    int getTarget();
    String getSolution();
    QUtil.GameLevel getLevel();
    void updateGame(int[] theNumbers, int theTarget,String theSolution);
}