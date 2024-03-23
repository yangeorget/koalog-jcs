package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.util.matrix.SquareMatrix;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.ConstantSum;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;

/**
 * This is the famous magic square problem.
 * It consists in finding n*n matrix such that:
 * <ul>
 * <li>all the lines, columns and big diagonals have the same sum,</li>
 * <li>all the elements of the matrix are different.</li>
 * </ul>
 *
 * <P>Note: This is problem #19 in CSPLib.  
 *
 * @author Yan Georget
 */
public class MagicSquareProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    IntegerVariable[] elements;
    IntegerVariable[] firstDiagonal;
    IntegerVariable[] secondDiagonal;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the square
     */
    public MagicSquareProblem(int n) {
        super();
        // Note about the modelling: 
        // this problem is not suited for AllDifferentConstantSum
        int m = (n*n-1)*n/2;
        elements = new IntegerVariable[n*n];
        for (int i=0; i<n*n; i++) {
            elements[i] = new IntegerVariable("_"+i, 0, n*n-1);
        }
        SquareMatrix a = new SquareMatrix(n, elements);
        add(new AllDifferent(elements));
        // TODO: use ConstantWeightedSums?
        for (int i=0; i<n; i++) {
            add(new ConstantSum((IntegerVariable[]) a.getLine(i), 
                                        m));
            add(new ConstantSum((IntegerVariable[]) a.getColumn(i), 
                                        m));
        }
        firstDiagonal = (IntegerVariable[]) a.getFirstDiagonal();
        add(new ConstantSum(firstDiagonal, m));
        secondDiagonal = (IntegerVariable[]) a.getSecondDiagonal();
        add(new ConstantSum(secondDiagonal, m));
        // removing symetries 
        IntegerVariable topLeft = firstDiagonal[0];
        IntegerVariable bottomRight = firstDiagonal[n-1];
        IntegerVariable topRight = secondDiagonal[n-1];
        IntegerVariable bottomLeft = secondDiagonal[0];
        add(new Less(topLeft, topRight));
        add(new Less(topLeft, bottomLeft));
        add(new Less(topLeft, bottomRight));
        add(new Less(topRight, bottomLeft));
        setVariables(elements);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the problem.
     * @param args the command line arguments
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new MagicSquareSolver(new MagicSquareProblem(4)).solve();
        // new MagicSquareAdaptiveSolver(new MagicSquareProblem(20)).solve();
    }
}
