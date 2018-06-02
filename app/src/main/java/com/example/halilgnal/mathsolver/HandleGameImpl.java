package com.example.halilgnal.mathsolver;


import android.util.Log;

import com.example.halilgnal.mathsolver.util.QUtil;
import com.example.halilgnal.mathsolver.util.Solver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HandleGameImpl implements HandleGame {

    Solver itsSolver;
    ArrayList<Step> itsSteps;

    @Override
    public void initializeGame(Game theGame) {
        itsSteps.clear();
        try {
            int[] aNumbers = generateNumbers(theGame.getLevel());
            int aTarget = generateTarget(theGame.getLevel());
            theGame.updateGame(aNumbers, aTarget, getSolution());
            isSolvable(theGame);
            theGame.updateGame(aNumbers, aTarget, getSolution());

            while (!isSolvable(theGame)) {
                aNumbers = generateNumbers(theGame.getLevel());
                aTarget = generateTarget(theGame.getLevel());
                theGame.updateGame(aNumbers, aTarget, getSolution());
            }
        } catch (Exception e) {
            Log.e("Initialization Error", e.getMessage());
        }
    }

    @Override
    public boolean isSolvable(Game theGame) {
        boolean aSolution = false;
        if (theGame == null) {
            throw new NullPointerException("Game object is null");
        }

        aSolution = itsSolver.findSolution(theGame.getNumbers(), theGame.getNumbers().length, theGame.getTarget());
        return aSolution;
    }

    @Override
    public boolean compareResult(Game theGame, int theCalc) {
        if (theGame.getTarget() == theCalc) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addStep(Step aStep) {
        itsSteps.add(aStep);
        return false;
    }

    @Override
    public String printSteps() {
        int i;
        String aStep = "";
        try {
            if (itsSteps.size() % 2 == 0) {
                for (i = 0; i < itsSteps.size(); i = i + 2) {

                    aStep += +itsSteps.get(i).getNum() + itsSteps.get(i + 1).getSign() + itsSteps.get(i + 1).getNum()
                            + "=" + itsSteps.get(i + 1).getResult()
                            + System.getProperty("line.separator");
                }
            }
        } catch (Exception e){
            Log.e("printSteps", "Calculation steps can not be printed: ", e);
        }
        return aStep;
    }

    HandleGameImpl() {
        itsSolver = new Solver();
        itsSteps = new ArrayList<>();
    }

    public String getSolution() {
        return itsSolver.printSolution();
    }

    public int generateTarget(QUtil.GameLevel theLevel) throws Exception {
        int aGeneratedNumber;
        switch (theLevel) {
            case VERY_EASY:
                aGeneratedNumber = QUtil.generateNumber(QUtil.itsVeryEasyMin, QUtil.itsVeryEasyMax);
                break;
            case EASY:
                aGeneratedNumber = QUtil.generateNumber(QUtil.itsEasyMin, QUtil.itsEasyMax);
                break;
            case NORMAL:
                aGeneratedNumber = QUtil.generateNumber(QUtil.itsNormalMin, QUtil.itsNormalMax);
                break;
            case HARD:
                aGeneratedNumber = QUtil.generateNumber(QUtil.itsHardMin, QUtil.itsHardMax);
                break;
            case VERY_HARD:
                aGeneratedNumber = QUtil.generateNumber(QUtil.itsVeryHardMin, QUtil.itsVeryHardMax);
                break;
            default:
                throw new Exception("Cannot generate Target");
        }
        return aGeneratedNumber;
    }

    public int[] generateNumbers(QUtil.GameLevel theLevel) throws Exception {
        int[] lstGeneratedNumbers = new int[QUtil.itsArrayLength];
        int aGeneratedNumbers;
        ArrayList<Integer> lstHardValues = new ArrayList<>(QUtil.itsHardValues);


        switch (theLevel) {

            //TODO:
            case VERY_EASY:
                //aGeneratedNumbers = QUtil.generateNumber(QUtil.itsVeryEasyMin, QUtil.itsVeryEasyMax);
                break;
            case EASY:
                //aGeneratedNumbers =  QUtil.generateNumber(QUtil.itsEasyMin, QUtil.itsEasyMax);
                break;
            case NORMAL:
                //generatedNumber =  QUtil.generateNumber(QUtil.itsNormalMin, QUtil.itsNormalMax);
                break;
            case HARD:

                for (int i = 0; i < QUtil.itsArrayLength; i++) {
                    if (i < 4) {
                        aGeneratedNumbers = QUtil.generateNumber(1, 10);
                        if (!QUtil.containsValue(lstGeneratedNumbers, aGeneratedNumbers)) {
                            lstGeneratedNumbers[i] = aGeneratedNumbers;
                        } else {
                            i--;
                        }
                    } else {
                        Collections.shuffle(lstHardValues);
                        lstGeneratedNumbers[i] = lstHardValues.remove(0);
                    }
                }
                break;
            case VERY_HARD:
                //generatedNumber =  QUtil.generateNumber(QUtil.itsVeryHardMin, QUtil.itsVeryHardMax);
                break;
            default:
                throw new Exception("Cannot generate Target");
        }
        Arrays.sort(lstGeneratedNumbers);

        return lstGeneratedNumbers;
    }

}
