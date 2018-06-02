package com.example.halilgnal.mathsolver;

public class Step {
    private int itsNum;
    private int itsResult;
    private String itsSign;


    Step() {
    }

    public int getNum() {
        return itsNum;
    }

    public String getSign() {
        return itsSign;
    }

    public int getResult() {
        return itsResult;
    }

    public void setNum(int itsNum) {
        this.itsNum = itsNum;
    }

    public void setSign(String itsSign) {
        this.itsSign = itsSign;
    }

    public void setResult(int itsResult) {
        this.itsResult = itsResult;
    }
}
