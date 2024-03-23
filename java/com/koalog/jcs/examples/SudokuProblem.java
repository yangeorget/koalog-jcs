package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.util.matrix.SquareMatrix;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.AllDifferent_SPARSE;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * A basic model for the Sudoku problem.
 *
 * @author Yan Georget
 */
public class SudokuProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param givens the givens (from 0 to 9, 0 being used for unknown values)
     */
    public SudokuProblem(int[][] givens) {
        super();
        IntegerVariable[][] num = new IntegerVariable[9][9];
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                if (givens[i][j] != 0) {
                    num[i][j]= 
                        new IntegerVariable("num" + i + "_" + j, 
                                            new SparseDomain(givens[i][j]));
                } else {
                    num[i][j] = 
                        new IntegerVariable("num" + i + "_" + j, 
                                            new SparseDomain(1, 9));
                }
            }
        }
        // basic model on num
        SquareMatrix mnum = new SquareMatrix(num);
        for (int j=0; j<9; j++) {
            add(new AllDifferent_SPARSE((IntegerVariable[]) mnum.getColumn(j)));
        }
        for (int i=0; i<9; i++) {
            add(new AllDifferent_SPARSE((IntegerVariable[]) mnum.getLine(i)));
        }
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                add(new AllDifferent_SPARSE(new IntegerVariable[]{
                    num[0+3*i][0+3*j], num[0+3*i][1+3*j], num[0+3*i][2+3*j],
                    num[1+3*i][0+3*j], num[1+3*i][1+3*j], num[1+3*i][2+3*j],
                    num[2+3*i][0+3*j], num[2+3*i][1+3*j], num[2+3*i][2+3*j]}));
            }
        }
        setVariables((IntegerVariable[]) mnum.getElements());
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
        new DefaultFFSolver(new SudokuProblem(LM)).solve();
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(SudokuProblem.class);

    /** An expert instance generated for Le Monde. */
    public static final int[][] LM = {
        {0,0,0,0,3,0,0,0,0},
        {2,8,9,0,0,0,0,0,0},
        {0,0,5,7,0,0,0,9,0},
        {0,0,0,0,0,0,8,0,6},
        {0,0,0,3,0,0,1,0,0},
        {7,1,0,0,0,6,0,0,2},
        {0,6,3,0,0,0,0,0,0},
        {0,0,0,0,4,0,2,0,0},
        {0,0,1,0,5,0,6,0,0}
    };

    /** A very hard instance generated for http://sudoku.koalog.com. */
    public static final int[][] FO4 = {
        {6,0,0,0,1,0,0,8,0},
        {5,1,7,4,0,0,0,0,0},
        {0,0,3,0,0,0,0,4,0},
        {0,0,0,0,0,0,0,0,1},
        {0,0,0,5,0,0,3,0,0},
        {1,6,0,0,0,9,0,5,2},
        {2,5,9,6,0,0,0,0,0},
        {0,0,0,0,7,0,0,0,0},
        {0,0,0,0,5,0,4,0,0}
    };        

    /** An expert instance generated for http://sudoku.koalog.com. */
    public static final int[][] FO5 = {
        {4,0,0,0,3,9,0,2,0},
        {0,5,6,0,0,0,0,0,0},
        {0,0,0,0,0,0,6,0,4},
        {0,0,0,0,0,0,9,0,0},
        {5,0,0,1,0,0,2,0,0},
        {0,9,0,0,2,7,0,3,0},
        {0,3,7,0,0,0,0,0,0},
        {0,0,0,0,0,0,8,0,6},
        {9,0,8,0,1,0,0,0,0}
    };        

    /** An easy instance. */
    public static final int[][] EASY = {
        {6,0,0,0,5,8,4,9,0},
        {0,2,4,0,0,0,0,0,0},
        {0,0,0,0,2,0,0,3,6},
        {0,0,0,0,0,0,9,0,7},
        {7,0,0,3,0,0,8,0,0},
        {1,5,0,0,8,9,0,0,0},
        {0,3,1,0,0,0,0,0,0},
        {0,0,0,0,3,0,5,0,0},
        {0,0,8,0,9,5,0,0,0}
    };
}


