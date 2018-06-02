package com.example.halilgnal.mathsolver;

import com.example.halilgnal.mathsolver.util.QUtil;

import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        Game aGame = new GameImpl( QUtil.GameLevel.HARD );
        HandleGame hg = new HandleGameImpl();
        if(aGame != null)
            hg.initializeGame(aGame);
        else
            System.out.println("HELL NOO");

        System.out.println(Arrays.toString(aGame.getNumbers()));
        System.out.println(aGame.getTarget());
        System.out.println(aGame.getSolution());
        hg.initializeGame(aGame);
        System.out.println(aGame.getSolution());

        System.out.println(Arrays.toString(aGame.getNumbers()));
        System.out.println(aGame.getTarget());
    }
}
