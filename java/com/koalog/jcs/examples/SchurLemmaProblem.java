package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;

import com.koalog.util.matrix.BaseMatrix;

import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.SmallerSum;
import com.koalog.jcs.constraint.arithmetic.Exactly;
import com.koalog.jcs.constraint.arithmetic.LexIncreasing;

/**
 * This is Schur's lemma.
 * <P>The problem is to put n balls labelled 1,...n into 3 boxes 
 * so that for any triple of balls (x,y,z) with x+y=z, 
 * not all are in the same box.
 *
 * <P>Note: This is problem #15 in CSPLib.  
 *
 * @author Yan Georget
 */
public class SchurLemmaProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the problem
     */
    public SchurLemmaProblem(int n) {
        super();
        BooleanVariable[][] bb = new BooleanVariable[n][3];
        for (int i=0; i<n; i++) {
            for (int k=0; k<3; k++) {
                bb[i][k] = new BooleanVariable();
            }
        }
        for (int i=0; i<n; i++) {
            // each ball is exactly in one box
            add(new Exactly(1, bb[i], 1));
        }
        for (int i=1; i<=n-1; i++) {
            for (int j=i+1; j<=n; j++) {
                if (i+j<=n) {
                    for (int k=0; k<3; k++) {
                        // faster than Atmost
                        add(new SmallerSum(new BooleanVariable[] {bb[i-1][k], 
                                                                  bb[j-1][k], 
                                                                  bb[i+j-1][k]},
                                           3));
                    }
                }
            }  
        }
        BaseMatrix bbMatrix = new BaseMatrix(bb);
        // removing symmetries on columns (values symmetries)
        add(new LexIncreasing(bbMatrix, false, true));
        setVariables((BooleanVariable[]) bbMatrix.getElements());
//      a classical modelling:
//         IntegerVariable[] box = new IntegerVariable[n];
//         for (int i=0; i<n; i++) {
//             box[i] = new IntegerVariable("b"+i, 
//                                          new SparseDomain(0,2));
//         }
//         for (int i=1; i<=n-1; i++) {
//             for (int j=i+1; j<=n; j++) {
//                 if (i+j<=n) {
//                     add(new GCC_SPARSE(new IntegerVariable[] {box[i-1], 
//                                                               box[j-1], 
//                                                               box[i+j-1]},
//                                        new int[] {0,1,2},
//                                        0,
//                                        2));
//                 }
//             }  
//         }
//         setVariables(box);
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
        new DefaultSplitSolver(new SchurLemmaProblem(24)).solve(1);
    }
}
