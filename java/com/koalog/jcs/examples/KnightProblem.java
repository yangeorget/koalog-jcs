package com.koalog.jcs.examples;

import java.util.Set;
import java.util.HashSet;
import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Cycle;
import com.koalog.jcs.constraint.arithmetic.AllDifferent_SPARSE;
import com.koalog.jcs.domain.SparseDomain;

/**
 * A knight has to visit once all positions of a chessboard 
 * (except the starting point twice). 
 *
 * @author Yan Georget
 */
public class KnightProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public KnightProblem() {
        super();
        IntegerVariable[] next = new IntegerVariable[64];
        for (int i=0; i<64; i++) {
            Set s = new HashSet();
            move(i, 1, 2, s);
            move(i, 1, -2, s);
            move(i, -1, 2, s);
            move(i, -1, -2, s);
            move(i, 2, 1, s);
            move(i, 2, -1, s);
            move(i, -2, 1, s);
            move(i, -2, -1, s);
            next[i] = new IntegerVariable("n"+i, new SparseDomain(s));
        }
        add(new AllDifferent_SPARSE(next));
        add(new Cycle(next));
        setVariables(next);
    }
    
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    private static void move(int i, int x, int y, Set s) {
        int jx = x+i%8;
        int jy = y+i/8;
        if (0<=jx && jx<=7 && 0<=jy && jy<=7) {
            s.add(new Integer(jx + 8*jy));
        }
    }

    /**
     * Runs the problem.
     * @param args the command line arguments:
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        new DefaultFFSolver(new KnightProblem()).solve(1);
    }
}
