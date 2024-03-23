package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.util.matrix.SquareMatrix;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.AllDifferentConstantSum_SPARSE;
import com.koalog.jcs.solver.DefaultFFSolver;

/**
 * A basic model for the Kakuro problem.
 *
 * @author Yan Georget
 */
public class KakuroProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param grid the boolean grid 
     * (0 is used for white cells, 1 for black cells)
     * @param hclues an array of arrays of horizontal clues
     * @param vclues an array of arrays of vertical clues
     */
    public KakuroProblem(int[][] grid,
                         int[][] hclues,
                         int[][] vclues) {
        super();
        int height = grid.length;
        int width = grid[0].length;
        IntegerVariable[][] num = new IntegerVariable[height][width];
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if (grid[i][j] == 0) {
                    num[i][j]= new IntegerVariable("num" + i + "_" + j, 
                                                   new SparseDomain(1, 9));
                } else {
                    // not necessary but useful to make the pb more regular
                    num[i][j]= new IntegerVariable("num" + i + "_" + j, 0);
                }
            }
        }
        // horizontal clues
        for (int i=0; i<height; i++) {
            for (int j=0, c=0; c < hclues[i].length; c++) {
                for (;j<width && grid[i][j] !=0; j++) {}
                int jMax = j;
                for (;jMax<width && grid[i][jMax] ==0; jMax++) {}
                IntegerVariable[] vars = new IntegerVariable[jMax-j];
                for (int v=0; v<vars.length; v++) {
                    vars[v] = num[i][j+v];
                }
                add(new AllDifferentConstantSum_SPARSE(vars, hclues[i][c]));
                j = jMax;
            }
        }
        // vertical clues
        for (int j=0; j<width; j++) {
            for (int i=0, c=0; c < vclues[j].length; c++) {
                for (;i<height && grid[i][j] !=0; i++) {}
                int iMax = i;
                for (;iMax<height && grid[iMax][j] ==0; iMax++) {}
                IntegerVariable[] vars = new IntegerVariable[iMax-i];
                for (int v=0; v<vars.length; v++) {
                    vars[v] = num[i+v][j];
                }
                add(new AllDifferentConstantSum_SPARSE(vars, vclues[j][c]));
                i = iMax;
            }
        }
        setVariables((IntegerVariable[]) new SquareMatrix(num).getElements());
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
        new DefaultFFSolver(new KakuroProblem(KS20060802_GRID,
                                              KS20060802_HCLUES,
                                              KS20060802_VCLUES)).solve();
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(KakuroProblem.class);

    /** A malicious instance by Kakuro-San: the grid. */
    public static final int[][] KS20060802_GRID = {
        {0,0,0,1,1,0,0,0,1},
        {0,0,0,1,0,0,0,0,0},
        {0,0,1,0,0,0,1,0,0},
        {0,0,0,0,0,1,0,0,1},
        {1,1,0,0,1,1,0,0,0},
        {0,0,0,0,1,0,0,0,0},
        {0,0,0,1,1,0,0,1,1},
        {1,0,0,1,0,0,0,0,0},
        {0,0,1,0,0,0,1,0,0},
        {0,0,0,0,0,1,0,0,0},
        {1,0,0,0,1,1,0,0,0},
    };
    /** A malicious instance by Kakuro-San: the horizontal clues. */
    public static final int[][] KS20060802_HCLUES = {
        {7, 7},
        {6, 24},
        {10, 23, 4},
        {35, 6},
        {17, 21},
        {16, 29},
        {18, 10},
        {5, 30},
        {16, 13, 11},
        {24, 7},
        {15, 9}
    };
    /** A malicious instance by Kakuro-San: the vertical clues. */
    public static final int[][] KS20060802_VCLUES = {
        {11, 10, 17},
        {21, 22},
        {4, 34, 17},
        {30, 6},
        {23, 6},
        {17, 29},
        {4, 29, 9},
        {22, 21},
        {3, 17, 11}
    };
}


