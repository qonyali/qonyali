package com.example.halilgnal.mathsolver.util;

import com.example.halilgnal.mathsolver.Operation.Add;
import com.example.halilgnal.mathsolver.Operation.Divide;
import com.example.halilgnal.mathsolver.Operation.Multiply;
import com.example.halilgnal.mathsolver.Operation.Operation;
import com.example.halilgnal.mathsolver.Operation.Subtract;

import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
/*
    public static void main(String[] args){
        int[] numbers = {3,5,9,10,50,75};
        int total = 337;
        Solver sl = new Solver();
        if (sl.findSolution(numbers, numbers.length, total)) {
            System.out.print(sl.printSolution() );
        }
    }
*/

    private static Operation[] OPERATIONS = {new Add(), new Subtract(), new Multiply(), new Divide()};
    private ArrayList<String> solution = new ArrayList<>();

    public boolean findSolution(int[] t, int nb, int total) {
        solution.clear();
        int [] aTmp = Arrays.copyOf(t,t.length);

        for (int i = 0; i < nb; i++) {
            if (aTmp[i] == total) {
                return true;
            }

            for (int j = i + 1; j < nb; j++) {
                for (int k = 0; k < OPERATIONS.length; k++) {
                    int res = OPERATIONS[k].eval(aTmp[i], aTmp[j]);

                    if (res != 0) {
                        int savei = aTmp[i], savej = aTmp[j];
                        aTmp[i] = res;
                        aTmp[j] = aTmp[nb - 1];

                        if (findSolution(aTmp, nb - 1, total)) {
                            solution.add(Math.max(savei, savej) + " " +
                                    OPERATIONS[k].symbol() + " " +
                                    Math.min(savei, savej) + " = " + res);
                            return true;
                        }

                        aTmp[i] = savei;
                        aTmp[j] = savej;
                    }
                }
            }
        }

        return false;
    }

    public String printSolution() {
        String aSolution = "";
        for (int i = solution.size() - 1; i >= 0; i--) {
            aSolution += solution.get(i) + System.getProperty("line.separator");
        }
        return aSolution;
    }
}
