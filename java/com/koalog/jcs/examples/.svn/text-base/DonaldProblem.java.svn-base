package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;

/**
 * This is the famous crypto-arithmetic puzzle DONALD+GERARD=ROBERT.
 * The letters represents digits that have to be all different.
 *
 * @author Yan Georget
 */
public class DonaldProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public DonaldProblem() {
        super();
        IntegerVariable t = new IntegerVariable("t",0,9);
        IntegerVariable r = new IntegerVariable("r",0,9);
        IntegerVariable o = new IntegerVariable("o",0,9);
        IntegerVariable n = new IntegerVariable("n",0,9);
        IntegerVariable l = new IntegerVariable("l",0,9);
        IntegerVariable g = new IntegerVariable("g",0,9);
        IntegerVariable e = new IntegerVariable("e",0,9);
        IntegerVariable d = new IntegerVariable("d",0,9);
        IntegerVariable b = new IntegerVariable("b",0,9);
        IntegerVariable a = new IntegerVariable("a",0,9);
        IntegerVariable[] vars = {t,r,o,n,l,g,e,d,b,a};
        add(new AllDifferent(vars));
        add(new ConstantWeightedSum(new int[] {
            1, 99010, 0, -1000, -20, -100000, -9900, -100002, 1000, -200
        }, 
                                  vars,
                                  0));
        setVariables(vars);
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
        new DefaultFFSolver(new DonaldProblem()).solve(1);
    }
}
