package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Shift;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This is the famous queens problem.
 * It consists in placing n queens on a n*n chessboard 
 * such that they don't threaten each other.
 *
 * @author Yan Georget
 */
public class QueensProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the size of the problem
     */
    public QueensProblem(int n) {
        super();
        IntegerVariable q[] = new IntegerVariable[n];
        IntegerVariable qp[] = new IntegerVariable[n];
        IntegerVariable qm[] = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            q[i] = new IntegerVariable("q"+i, 0, n-1);
            qp[i] = new IntegerVariable("qp"+i, 0, 2*n-2);
            add(new Shift(qp[i],q[i],i));
            qm[i] = new IntegerVariable("qm"+i, -n+1, n-1);
            add(new Shift(qm[i],q[i],-i));
        }
        add(new AllDifferent(q));
        add(new AllDifferent(qp));
        add(new AllDifferent(qm));
        setVariables(q);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Runs the problem.
     * @param args the command line arguments:
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new DefaultSplitSolver(new QueensProblem(8)).solve();
    }
}
