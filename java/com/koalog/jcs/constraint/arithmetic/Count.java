package com.koalog.jcs.constraint.arithmetic;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * Counts the number of occurences of a value.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">when variables have interval domains</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">variables.length</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Count extends AbstractCount {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Count.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The counter. */
    protected IntegerVariable p;
    int n;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param p an integer variable (non instantiated)
     * @param x an array of integer variables
     * @param c an integer
     */    
    public Count(IntegerVariable p, IntegerVariable[] x, int c) {
        super(c);
        checkArguments(p);
        name = "count(" + p + ", x, " + c + ")";
        this.p = p;
        n = x.length;
        variables = new IntegerVariable[n + 1];
        System.arraycopy(x, 0, variables, 0, n);
        variables[n] = p;
        complexity = n;
        idempotent = p.isMinMax() && !IntegerVariable.oneSparse(x);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(IntegerVariable p) {
        if (p.isInstantiated()) {
            throw new InvalidConstraintException("counter instantiated");
        }
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp, ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        p.adjustMinMax(cp, cs, this, 0, n);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        vars.clear();
        final IntegerDomain pDom = (IntegerDomain) p.getDomain();
        final int pMin = pDom.getMin();
        if (pDom.isSingleton()) {
            if (pMin == 0) {
                enforceDifferent(cp, cs, vars, n);
                return;
            } else if (pMin == n) {
                enforceEqual(cp, cs, vars, n);
                return;
            }
        }
        final int nbEqualInit = pDom.getMax(); 
        final int nbDifferentInit = n - pMin; 
        nbEqual = nbEqualInit;
        nbDifferent = nbDifferentInit;
        for (int i=n; --i>=0;) {
            final Variable var = variables[i];
            final IntegerDomain dom = (IntegerDomain) var.getDomain();
            if (dom.contains(c)) {
                if (dom.isSingleton()) { // then, equals to c
                    if (--nbEqual == 0) {
                        enforceDifferent(cp, cs, vars, i);
                        p.adjustMinToMax(cp, cs, this);
                        return;
                    }
                } else {
                    vars.add(var);
                }
            } else { // does not contain c
                if (--nbDifferent == 0) {
                    enforceEqual(cp, cs, vars, i);
                    p.adjustMaxToMin(cp, cs, this);
                    return;
                }
            }
        }
        p.adjustMinMax(cp, 
                       cs, 
                       this, 
                       nbEqualInit - nbEqual, 
                       n-nbDifferentInit+ nbDifferent);
    }
}
/*
 * $Log$
 * Revision 1.16  2005/07/22 14:53:24  yan
 * pretty variable name
 *
 * Revision 1.15  2005/07/22 12:09:28  yan
 * fixed bug 0021280
 *
 * Revision 1.14  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.13  2004/11/26 16:54:50  yan
 * optimized
 *
 * Revision 1.12  2004/11/26 16:26:40  yan
 * optimized counters
 *
 * Revision 1.11  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.10  2004/06/15 13:26:45  yan
 * various optimizations and refactoring
 *
 * Revision 1.9  2004/06/10 14:38:08  yan
 * various refactoring
 *
 * Revision 1.8  2004/05/15 15:45:26  yan
 * various optims
 *
 * Revision 1.7  2004/05/05 14:01:09  yan
 * factorized some code
 *
 * Revision 1.6  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.5  2004/04/13 15:32:43  yan
 * adjustDifferent can only be used for variables with a sparse domain
 *
 * Revision 1.4  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.3  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.2  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.1  2003/06/15 18:02:45  yan
 * initial revision
 *
 */




