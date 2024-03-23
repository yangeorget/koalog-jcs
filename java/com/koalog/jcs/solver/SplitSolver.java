package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import com.koalog.jcs.constraint.Problem;
import com.koalog.jcs.variable.Variable;

/**
 * <P><CODE>SplitSolver</CODE> is a concrete sub-class of 
 * <CODE>BacktrackSolver</CODE>.
 * It constructs choices by:
 * <UL>
 * <LI>choosing a variable;</LI> 
 * <LI>choosing a new domain for this variable 
 * (for example by instantiating the variable).</LI>
 * </UL>
 * </P>
 * 
 * <P>The constructor of the <CODE>SplitSolver</CODE> class 
 * takes three arguments:
 * <UL>
 * <LI>a problem;</LI>
 * <LI> a variable heuristic, used to select the variable;</LI>
 * <LI> a domain heuristic, used to create the new domain.</LI>
 * </UL>
 * </P>
 *
 * @see VariableHeuristic
 * @see DomainHeuristic
 * @author Yan Georget
 */
public class SplitSolver extends BacktrackSolver { 
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(SplitSolver.class);
    private static final boolean DEBUG = cat.isDebugEnabled();

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The heuristic used to choose a variable.*/
    protected VariableHeuristic variableHeuristic;
    /** The heuristic used to choose a sub-domain.*/
    protected DomainHeuristic domainHeuristic;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Constructs a split solver given a problem and heuristics 
     * (for variable and domain).
     * @param problem the problem to solve
     * @param variableHeuristic an heuristic to choose variables
     * @param domainHeuristic an heuristic to choose sub-domains
     */
    public SplitSolver(Problem problem, 
                       VariableHeuristic variableHeuristic,
                       DomainHeuristic domainHeuristic) {
        this(problem);
        this.variableHeuristic = variableHeuristic;
        this.domainHeuristic = domainHeuristic;
    }
    
    /**
     * Internal constructor.
     * @param problem the problem to solve
     * @since 2.2
     */
    protected SplitSolver(Problem problem) {
        super(problem);
    }
 
    //------------------------------------------------------------------------
    // ACCESSORS 
    //------------------------------------------------------------------------  
    /**
     * Sets the heuristic to choose a variable.
     * @param variableHeuristic a variable heuristic
     */
    public final void 
    setVariableHeuristic(VariableHeuristic variableHeuristic) {
        this.variableHeuristic = variableHeuristic;
    }
    
    /**
     * Returns the heuristic to choose a variable.
     * @return a variable heuristic
     */
    public final VariableHeuristic getVariableHeuristic() {
        return variableHeuristic;
    }

    /**
     * Sets the heuristic to choose a sub-domain.
     * @param domainHeuristic a domain heuristic
     */
    public final void setDomainHeuristic(DomainHeuristic domainHeuristic) {
        this.domainHeuristic = domainHeuristic;
    }
    
    /**
     * Returns the heuristic to choose a sub-domain.
     * @return a domain heuristic
     */
    public final DomainHeuristic getDomainHeuristic() {
        return domainHeuristic;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /** @see com.koalog.jcs.solver.BacktrackSolver */
    public boolean choice() {
        return choice(problem.getVariables());
    }

    /**
     * Makes a choice by choosing a variable among an array of variables,
     * then a sub-domain for this variable. 
     * @param variables an array of variables
     * @return a boolean indicating if a choice has been made
     */
    public boolean choice(Variable[] variables) {
        Variable variable = variableHeuristic.chooseVariable(variables);
        if (variable == null) {
            return false;
        }
        if (DEBUG) {
            cat.debug("choosing " + variable);
        }
        choicePoints.push();
        domainHeuristic.chooseDomain(choicePoints, 
                                     constraintScheduler, 
                                     variable);
        if (DEBUG) {
            cat.debug("choice " + variable);
        }
        return true;
    }
}
/*
 * $Log$
 * Revision 1.17  2004/07/21 16:57:58  yan
 * added constructor
 *
 * Revision 1.16  2004/05/05 11:43:42  yan
 * fixed style
 *
 * Revision 1.15  2004/04/09 14:43:41  yan
 * removal of events
 *
 * Revision 1.14  2004/04/01 19:20:13  yan
 * fixed style
 *
 * Revision 1.13  2004/03/14 18:03:22  yan
 * changed filter API
 *
 * Revision 1.12  2003/07/12 17:56:58  yan
 * added commented debug statements
 *
 * Revision 1.11  2003/03/27 10:44:53  yan
 * commented some code
 *
 * Revision 1.10  2003/03/09 18:51:19  yan
 * fixed style
 *
 * Revision 1.9  2003/03/09 18:43:45  yan
 * optimized modifiedVariables usage
 *
 * Revision 1.8  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.7  2002/10/25 09:05:21  yan
 * cleaned category
 *
 * Revision 1.6  2002/06/16 19:36:33  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 13:59:15  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
