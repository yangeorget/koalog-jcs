package com.koalog.jcs.solver;

import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.variable.Variable;

/**
 * Variable heuristic that 
 * chooses the best variable according to an evaluation function.
 * 
 * <P>Subclasses of <CODE>BaseVariableHeuristic</CODE> implement the abstract 
 * <CODE>eval</CODE> method.</P>
 *
 * @see Variable
 * @author Yan Georget
 */
public abstract class BaseVariableHeuristic implements VariableHeuristic {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The current problem. */
    protected Problem problem;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Default constructor.
     */
    public BaseVariableHeuristic() {
    }

    /**
     * Auxilliary constructor.
     * @param problem the current problem
     * @since 2.2
     */
    public BaseVariableHeuristic(Problem problem) {
        this.problem = problem;
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Evaluates a variable.
     * @param var a variable
     * @return an integer
     */
    public abstract int eval(Variable var);

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses the variable with the maximal value.
     * @param variables an array of variables
     * @return a variable
     */
    public Variable chooseVariable(Variable[] variables) {
        Variable variable = null;
        int value = Integer.MIN_VALUE;
        for (int i=0; i<variables.length; i++) {
            Variable var = variables[i];
            if (var.isNotInstantiated()) {
                int val = eval(var);
                if (val > value) {
                    variable = var;
                    value = val;
                } 
            }
        } 
        return variable;
    }
}
/*
 * $Log$
 * Revision 1.7  2004/11/29 09:32:24  yan
 * using isNotInstantiated
 *
 * Revision 1.6  2004/07/21 15:46:49  yan
 * added constructor
 *
 * Revision 1.5  2002/06/16 16:25:20  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:59:14  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
