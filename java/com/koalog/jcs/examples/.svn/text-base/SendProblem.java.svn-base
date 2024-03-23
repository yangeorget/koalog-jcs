package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultFFSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;

/**
 * This is the famous crypto-arithmetic puzzle SEND+MORE=MONEY.
 * The letters represents digits that have to be all different.
 *
 * @author Yan Georget
 */
public class SendProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public SendProblem() {
        super();
        IntegerVariable s = new IntegerVariable("s",1,9);
        IntegerVariable e = new IntegerVariable("e",0,9);
        IntegerVariable n = new IntegerVariable("n",0,9);
        IntegerVariable d = new IntegerVariable("d",0,9);
        IntegerVariable m = new IntegerVariable("m",1,9);
        IntegerVariable o = new IntegerVariable("o",0,9);
        IntegerVariable r = new IntegerVariable("r",0,9);
        IntegerVariable y = new IntegerVariable("y",0,9);
        IntegerVariable[] vars = new IntegerVariable[] {s,e,n,d,m,o,r,y};
        add(new AllDifferent(vars));
        add(new ConstantWeightedSum(new int[]{1000,91,-90,1,-9000,-900,10,-1}, 
                                    vars, 
                                    0));
        setVariables(vars);
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
        new DefaultFFSolver(new SendProblem()).solve(1);
    }
}
