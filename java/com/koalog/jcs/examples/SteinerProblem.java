package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.set.ConstantCard;
import com.koalog.jcs.constraint.set.IntersectionMaxSize;
import com.koalog.jcs.domain.SetDomain;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.FromSetMaxDomainHeuristic;
import com.koalog.jcs.variable.SetVariable;

/**
 * <P>A ternary Steiner system of order n is 
 * a set of n * (n-1)\6 triplets of distinct elements 
 * taking their values between 1 and n, 
 * such that all the pairs included in two different triplets are different. 
 *
 * <P>The goal is to build the set of triplets for a given value of n
 * (n has to be congruent to 1 or 3 modulo 6 for the problem to be solvable).
 *
 * <P>Note: This is problem #44 in CSPLib.  
 *
 * @author Yan Georget
 */
public class SteinerProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the order of the Steiner system
     */
    public SteinerProblem(int n) {
        super();
        Set ub = new HashSet();
        for (int i = 1; i<=n; i++) {
            ub.add(new Integer(i));
        }
        SetVariable[] sets = new SetVariable[n*(n-1)/6];
        for (int i=0; i<sets.length; i++) {
            sets[i] = new SetVariable("s"+i, 
                                      new SetDomain(Collections.EMPTY_SET,
                                                    ub));
            add(new ConstantCard(3, sets[i]));
        }
        for (int i=0; i<sets.length-1; i++) {
            for (int j=i+1; j<sets.length; j++) {
                add(new IntersectionMaxSize(1, sets[i], sets[j]));
            }
        }
        setVariables(sets);
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
        new SplitSolver(new SteinerProblem(7),
                        new KeepOrderVariableHeuristic(),
                        new FromSetMaxDomainHeuristic()).solve(1);
    }
}
