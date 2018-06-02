package com.example.halilgnal.mathsolver;

public interface HandleGame {
    void initializeGame(Game theGame);
    boolean isSolvable(Game theGame);
    boolean compareResult(Game theGame, int theCalc);
    boolean addStep(Step step);
    String printSteps();
}
