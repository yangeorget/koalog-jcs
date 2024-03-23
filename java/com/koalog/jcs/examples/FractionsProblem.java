package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.GreatestDomainVariableHeuristic;
import com.koalog.jcs.solver.SplitLowDomainHeuristic;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.AllDifferent;
import com.koalog.jcs.constraint.arithmetic.WeightedSum;
import com.koalog.jcs.constraint.arithmetic.Sum;
import com.koalog.jcs.constraint.arithmetic.Less;
import com.koalog.jcs.constraint.arithmetic.Prod;

/**
 * Find 9 distinct non-zero digits that satisfy: 
 * 
 * <PRE>
 *     A    D    G
 *    -- + -- + -- == 1
 *    BC   EF   HI
 * </PRE>                                
 *
 * <P>Note: This is problem #41 in CSPLib.  
 *
 * @author Yan Georget
 */
public class FractionsProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public FractionsProblem() {
        super();
        IntegerVariable a = new IntegerVariable("a", 1, 9);
        IntegerVariable b = new IntegerVariable("b", 1, 9); 
        IntegerVariable c = new IntegerVariable("c", 1, 9);
        IntegerVariable d = new IntegerVariable("d", 1, 9);
        IntegerVariable e = new IntegerVariable("e", 1, 9);
        IntegerVariable f = new IntegerVariable("f", 1, 9);
        IntegerVariable g = new IntegerVariable("g", 1, 9);
        IntegerVariable h = new IntegerVariable("h", 1, 9);
        IntegerVariable i = new IntegerVariable("i", 1, 9);
        IntegerVariable[] vars = new IntegerVariable[] {
            a, b, c, d, e, f, g, h, i
        };
        add(new AllDifferent(vars));
        IntegerVariable ef = new IntegerVariable("ef", 12, 98);
        add(new WeightedSum(ef,
                            new int[]{10,1},
                            new IntegerVariable[] {e, f}));
        IntegerVariable hi = new IntegerVariable("hi", 12, 98);
        add(new WeightedSum(hi,
                            new int[]{10,1},
                            new IntegerVariable[] {h, i}));
        IntegerVariable bc = new IntegerVariable("bc", 12, 98);
        add(new WeightedSum(bc,
                            new int[]{10,1},
                            new IntegerVariable[] {b, c}));
        IntegerVariable ef_hi = new IntegerVariable("ef*hi", 12*12, 98*98);
        IntegerVariable a_ef_hi = new IntegerVariable("a*ef*hi", 
                                                      12*12, 
                                                      9*98*98);
        add(new Prod(ef_hi, ef, hi));
        add(new Prod(a_ef_hi, a, ef_hi));
        IntegerVariable bc_hi = new IntegerVariable("bc*hi", 12*12, 98*98);
        IntegerVariable d_bc_hi = new IntegerVariable("d*bc*hi", 
                                                      12*12, 
                                                      9*98*98);
        add(new Prod(bc_hi, bc, hi));
        add(new Prod(d_bc_hi, d, bc_hi));
        IntegerVariable bc_ef = new IntegerVariable("bc*ef", 12*12, 98*98);
        IntegerVariable g_bc_ef = new IntegerVariable("g*bc*ef", 
                                                      12*12, 
                                                      9*98*98);
        add(new Prod(bc_ef, bc, ef));
        add(new Prod(g_bc_ef, g, bc_ef));
        IntegerVariable bc_ef_hi = new IntegerVariable("bc*ef*hi", 
                                                       12*12*12, 
                                                       98*98*98);
        add(new Prod(bc_ef_hi, bc_ef, hi));
        add(new Sum(bc_ef_hi,
                    new IntegerVariable[] {a_ef_hi,
                                           d_bc_hi, 
                                           g_bc_ef}));
        // redundant constraints
        add(new Prod(bc_ef_hi, bc_hi, ef));
        add(new Prod(bc_ef_hi, ef_hi, bc));
        // constraints for breaking symetries
        add(new Less(a, d));
        add(new Less(d, g));
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
        new SplitSolver(new FractionsProblem(),
                        new GreatestDomainVariableHeuristic(),
                        new SplitLowDomainHeuristic()).solve(1);
    }
}



