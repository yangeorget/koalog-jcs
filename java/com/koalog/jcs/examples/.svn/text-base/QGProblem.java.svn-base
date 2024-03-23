package com.koalog.jcs.examples;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.util.matrix.SquareMatrix;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Element_3;
import com.koalog.jcs.constraint.arithmetic.LatinSquare_SPARSE;

/**
 * The quasigroup existence problem.
 *
 * <P>Note: This is problem #3 in CSPLib.
 *
 * @author Yan Georget
 */
public class QGProblem extends BaseProblem {
    /** The numbers, with sparse domains, by row then by column. */
    IntegerVariable[][] num; 
    /** The rows, with sparse domains, by number then by column. */
    IntegerVariable[][] row;
    /** The columns, with sparse domains, by number then by row. */
    IntegerVariable[][] col;
    SquareMatrix mNum;
    SquareMatrix mRow;
    SquareMatrix mCol;
    int n;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the quasigroup
     */
    public QGProblem(int n) {
        super();
        this.n = n;
        num = new IntegerVariable[n][n]; 
        row = new IntegerVariable[n][n]; 
        col = new IntegerVariable[n][n]; 
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (i == j) { // idempotence
                    num[i][j] = new IntegerVariable(i+"_"+j, 
                                                    new SparseDomain(i));
                } else {
                    if (j == n-1 && i>1) { // symmetry breaking
                        num[i][j] = new IntegerVariable(i+"_"+j, 
                                                        new SparseDomain(i-1, 
                                                                         n-1));
                    } else {
                        num[i][j] = new IntegerVariable(i+"_"+j, 
                                                        new SparseDomain(0, 
                                                                         n-1));
                    }
                }
            }
        }
        for (int k=0; k<n; k++) {
            for (int i=0; i<n; i++) {
                if (i == k) {
                    col[k][i] = new IntegerVariable(k+"_"+i, 
                                                    new SparseDomain(i));
                } else { 
                    col[k][i] = new IntegerVariable(k+"_"+i, 
                                                    new SparseDomain(0,
                                                                     n-1));
                }
            }
        }
        for (int k=0; k<n; k++) {
            for (int j=0; j<n; j++) {
                if (j == k) {
                    row[k][j] = new IntegerVariable(k+"_"+j, 
                                                    new SparseDomain(j));
                } else {
                    row[k][j] = new IntegerVariable(k+"_"+j, 
                                                    new SparseDomain(0,
                                                                     n-1));
                }
            }
        }
        mNum = new SquareMatrix(num);
        mRow = new SquareMatrix(row);
        mCol = new SquareMatrix(col);
        add(new LatinSquare_SPARSE(mNum, mRow, mCol));
        Collection variables = new ArrayList(3*n*n);
        variables.addAll(Arrays.asList(mNum.getElements()));
        variables.addAll(Arrays.asList(mRow.getElements()));
        variables.addAll(Arrays.asList(mCol.getElements()));
        setVariables((IntegerVariable[]) variables
                     .toArray(new IntegerVariable[] {}));
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
        new DefaultFFSolver(new QGProblem.QG5(11)).solve();
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * ((b*a)*b)*b = a
     */
    static class QG5 extends QGProblem {
        QG5(int n) {
            super(n);
            for (int j=0; j<n; j++) {
                IntegerVariable[] col = (IntegerVariable[]) mNum.getColumn(j);
                for (int i=0; i<n; i++) {
                    if (i != j) {
                        // faster than Element_3SPARSE
                        add(new Element_3(row[i][j], num[j][i], col));
                    }
                }
            }
        }
    }
}

