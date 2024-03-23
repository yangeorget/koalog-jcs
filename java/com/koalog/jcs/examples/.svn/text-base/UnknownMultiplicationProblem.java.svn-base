package com.koalog.jcs.examples;

import org.apache.log4j.PropertyConfigurator;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.solver.SplitSolver;
import com.koalog.jcs.solver.KeepOrderVariableHeuristic;
import com.koalog.jcs.solver.DecreasingOrderDomainHeuristic;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.arithmetic.Exactly;
import com.koalog.jcs.constraint.arithmetic.WeightedSum;
import com.koalog.jcs.constraint.arithmetic.ConstantWeightedSum;
import com.koalog.jcs.constraint.arithmetic.Prod;

/**
 * Find the value of each digit verifying the following multiplication and 
 * such that each digit (0,1,...,9) appears exactly twice:                 
 *                                               
 * <PRE>
 *                   X1  X2  X3                                            
 *                 * X4  X5  X6                                            
 *                  -----------                                            
 *                   X7  X8  X9                                          
 *        +      X10 X11 X12                                               
 *        + X13 X14 X15                                                    
 *        = -------------------                                            
 *          X16 X17 X18 X19 X20                                            
 * </PRE>                                
 *
 * @author Daniel Diaz
 * @author Yan Georget
 */
public class UnknownMultiplicationProblem extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public UnknownMultiplicationProblem() {
        super();
        IntegerVariable[] vars = new IntegerVariable[20];
        for (int i=1; i<=20; i++) {
            vars[i-1] = new IntegerVariable("x"+i, 0, 9);
        }
        for (int j=0; j<10; j++) {
            add(new Exactly(2, vars, j));
        }
        IntegerVariable y = new IntegerVariable(0, 999); 
        add(new WeightedSum(y,
                            new int[]{100, 10, 1}, 
                            new IntegerVariable[] {
                                vars[0], vars[1], vars[2]
                            }));
        IntegerVariable z1 = new IntegerVariable(0, 999);
        add(new WeightedSum(z1,
                            new int[]{100, 10, 1}, 
                            new IntegerVariable[] {
                                vars[6], vars[7], vars[8]
                            }));
        IntegerVariable z2 = new IntegerVariable(0, 999);
        add(new WeightedSum(z2,
                            new int[]{100, 10, 1}, 
                            new IntegerVariable[] {
                                vars[9], vars[10], vars[11]
                            }));
        IntegerVariable z3 = new IntegerVariable(0, 999);
        add(new WeightedSum(z3,
                            new int[]{100, 10, 1}, 
                            new IntegerVariable[] {
                                vars[12], vars[13], vars[14]
                            }));
        add(new Prod(z1, y, vars[5]));
        add(new Prod(z2, y, vars[4]));
        add(new Prod(z3, y, vars[3]));
        add(new ConstantWeightedSum(new int[]{100, 
                                              10,
                                              1,
                                              1000,
                                              100,
                                              10,
                                              10000,
                                              1000,
                                              100,
                                              -10000,
                                              -1000,
                                              -100,
                                              -10,
                                              -1},
                                    new IntegerVariable[] {vars[6], 
                                                           vars[7], 
                                                           vars[8], 
                                                           vars[9], 
                                                           vars[10], 
                                                           vars[11], 
                                                           vars[12], 
                                                           vars[13], 
                                                           vars[14],
                                                           vars[15], 
                                                           vars[16], 
                                                           vars[17], 
                                                           vars[18], 
                                                           vars[19]},
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
        new SplitSolver(new UnknownMultiplicationProblem(),
                        new KeepOrderVariableHeuristic(),
                        new DecreasingOrderDomainHeuristic()).solve(2);
    }
}



