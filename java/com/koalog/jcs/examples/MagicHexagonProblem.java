package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.DefaultSplitSolver;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.ConstantSum;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;

/**
 * Find the unique diameter-5 magic (wrt symmetries) hexagon such that:
 * <UL>
 * <LI>each integer from 1 to 19 appears exactly once, 
 * <LI>the sum of the numbers along any column or diagonal is the same.
 * </UL>
 *
 * <P>See http://mathworld.wolfram.com/MagicHexagon.html for more details.
 *
 * <P>Note: This is problem #23 in CSPLib.
 *
 * @author Magnus Agren
 * @author Yan Georget
 */
public class MagicHexagonProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public MagicHexagonProblem() {
        super();
        // Note about the modelling: 
        // this problem is not suited for AllDifferentConstantSum
        IntegerVariable a = new IntegerVariable("a", 1,19);
        IntegerVariable b = new IntegerVariable("b", 1,19);
        IntegerVariable c = new IntegerVariable("c", 1,19);
        IntegerVariable d = new IntegerVariable("d", 1,19);
        IntegerVariable e = new IntegerVariable("e", 1,19);
        IntegerVariable f = new IntegerVariable("f", 1,19);
        IntegerVariable g = new IntegerVariable("g", 1,19);
        IntegerVariable h = new IntegerVariable("h", 1,19);
        IntegerVariable i = new IntegerVariable("i", 1,19);
        IntegerVariable j = new IntegerVariable("j", 1,19);
        IntegerVariable k = new IntegerVariable("k", 1,19);
        IntegerVariable l = new IntegerVariable("l", 1,19);
        IntegerVariable m = new IntegerVariable("m", 1,19);
        IntegerVariable n = new IntegerVariable("n", 1,19);
        IntegerVariable o = new IntegerVariable("o", 1,19);
        IntegerVariable p = new IntegerVariable("p", 1,19);
        IntegerVariable q = new IntegerVariable("q", 1,19);
        IntegerVariable r = new IntegerVariable("r", 1,19);
        IntegerVariable s = new IntegerVariable("s", 1,19);
        IntegerVariable[] vars = new IntegerVariable[]
            {a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s};
        // TODO: use ConstantWeightedSums?
        add(new ConstantSum(new IntegerVariable[]{a,b,c}, 38));
        add(new ConstantSum(new IntegerVariable[]{d,e,f,g}, 38));
        add(new ConstantSum(new IntegerVariable[]{h,i,j,k,l}, 38));
        add(new ConstantSum(new IntegerVariable[]{m,n,o,p}, 38));
        add(new ConstantSum(new IntegerVariable[]{q,r,s}, 38));
        add(new ConstantSum(new IntegerVariable[]{a,d,h}, 38));
        add(new ConstantSum(new IntegerVariable[]{b,e,i,m}, 38));
        add(new ConstantSum(new IntegerVariable[]{c,f,j,n,q}, 38));
        add(new ConstantSum(new IntegerVariable[]{g,k,o,r}, 38));
        add(new ConstantSum(new IntegerVariable[]{l,p,s}, 38));
        add(new ConstantSum(new IntegerVariable[]{c,g,l}, 38));
        add(new ConstantSum(new IntegerVariable[]{b,f,k,p}, 38));
        add(new ConstantSum(new IntegerVariable[]{a,e,j,o,s}, 38));
        add(new ConstantSum(new IntegerVariable[]{d,i,n,r}, 38));
        add(new ConstantSum(new IntegerVariable[]{h,m,q}, 38));
        add(new AllDifferent(vars));
        // removing symmetries (1 solution instead of 12)
        add(new Less(a, c)); 
        add(new Less(a, h)); 
        add(new Less(a, l)); 
        add(new Less(a, q)); 
        add(new Less(a, s)); 
        add(new Less(c, h)); 
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
        new DefaultSplitSolver(new MagicHexagonProblem()).solve();
    }
}

