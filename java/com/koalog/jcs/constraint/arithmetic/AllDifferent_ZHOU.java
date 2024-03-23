package com.koalog.jcs.constraint.arithmetic;

import java.util.Arrays;
import java.util.Comparator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.MinMaxDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces that all of its variables are different.
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
 *    <TD align="right">variables.length^2</TD>
 * </TR>
 * </TABLE>
 *
 * <P>It uses the algorithm of Jianyang Zhou 
 * as described in his PhD thesis (LIM, France).
 *
 * @author Yan Georget
 */
public class AllDifferent_ZHOU extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The array of the variable domains sorted using their mins. */
    private MinMaxDomain[] lbs;
    /** The array of the variable domains sorted using their maxs. */
    private MinMaxDomain[] ubs;
    /** A comparator used to sort domains using their mins. */
    private static Comparator lb;
    /** A comparator used to sort domains using their maxs. */
    private static Comparator ub;

    //------------------------------------------------------------------------
    // STATIC CODE
    //------------------------------------------------------------------------
    static {
        ub = new Comparator() {
                public int compare(Object o1, Object o2) {
                    return (((IntegerDomain) o1).getMax() 
                            - ((IntegerDomain) o2).getMax());
                }
            };
        lb = new Comparator() {
                public int compare(Object o1, Object o2) {
                    return (((IntegerDomain) o1).getMin() 
                            - ((IntegerDomain) o2).getMin());
                }
            };
    }

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param variables an array of integer variables
     */
    public AllDifferent_ZHOU(IntegerVariable[] variables) {
        super(variables);
        this.name = "alldifferent";
        this.lbs = this.ubs = new MinMaxDomain[variables.length];
        complexity = variables.length*variables.length;
        idempotent = !IntegerVariable.oneSparse(variables);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        for (int i=0; i<variables.length; i++) {
            lbs[i] = ubs[i] =
                new MinMaxDomain((IntegerDomain) variables[i].getDomain());
        }
        // initialisations of the sorted arrays of domains 
        Arrays.sort(lbs, lb); 
        Arrays.sort(ubs, ub);
        // filtering of the domains
        contractLB(cp, cs);
        contractUB(cp, cs);
    }

    /**
     * Computes and applies the contractors based on 
     * lower bounds of the domains.
     * @param cp a choice point stack
     * @param cs a constraint scheduler
     * @throws ConstraintInconsistencyException when it detects an inconsistency
     */
    private void contractLB(ChoicePointStack cp, 
                            ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        int p; // number of domains included in the current interval
        int q; // index of the domain corresponding to the max of the interval
        int size; // size of the current interval
        int min; // min of the current interval
        for (int i=0; i<variables.length; i++) {
            min = lbs[i].getMin();
            if (i==0 || min != lbs[i-1].getMin()) {
                p = 0;
                q = -1;
                for (int j=0; j<variables.length; j++) {
                    if (min <= ubs[j].getMin()) {
                        size = ubs[j].getMax() - min + 1;
                        if (++p > size) {
                            fail();
                        }           
                        if (p == size) {
                            q = j;
                        }
                    }
                }
                if (q>=0) {
                    // excludes some variables from the computed interval
                    applyContractor(cp, cs, min, ubs[q].getMax()); 
                }
            }
        } 
    }

    /**
     * Computes and applies the contractors based on 
     * upper bounds of the domains.
     * @param cp a choice point stack
     * @param cs a constraint scheduler
     * @throws ConstraintInconsistencyException when it detects an inconsistency
     */
    private void contractUB(ChoicePointStack cp, 
                            ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        int p; // number of domains included in the current interval
        int q; // index of the domain corresponding to the min of the interval
        int size; // size of the current interval
        int max; // max of the current interval
        for (int i=0; i<variables.length; i++) {
            max = ubs[i].getMax();
            if (i==0 || max != ubs[i-1].getMax()) {
                p = 0;
                q = -1;
                for (int j=variables.length-1; j>=0; j--) {
                    if (lbs[j].getMax() <= max) {
                        size = max - lbs[j].getMin() + 1;
                        if (++p > size) {
                            fail();
                        }           
                        if (p == size) {
                            q = j;
                        }
                    }
                }
                if (q>=0) {
                    // excludes some variables from the computed interval
                    applyContractor(cp, cs, lbs[q].getMin(), max); 
                }
            }
        } 
    }
  
    /**
     * Applies a contractor. 
     *
     * <P>Forces all variables that have a non empty domain intersection 
     * with [min,max] (but which domain do not include [min,max]) 
     * not to intersect [min,max].</P>
     *
     * @param cp a choice point stack
     * @param cs a constraint scheduler
     * @param min the min of the contractor
     * @param max the max of the contractor
     * @throws ConstraintInconsistencyException when it detects an inconsistency
     */
    protected void applyContractor(ChoicePointStack cp,
                                   ConstraintScheduler cs,
                                   int min, 
                                   int max) 
        throws ConstraintInconsistencyException {
        for (int l=variables.length; --l>=0;) {
            final IntegerVariable var = (IntegerVariable) variables[l];
            final IntegerDomain dom = (IntegerDomain) var.getDomain();
            if (max<dom.getMax() && min<=dom.getMin()) { 
                // forces the variable to be greater than [min,max]
                var.adjustMin(cp, cs, this, max+1);
            } else if (dom.getMin()<min && dom.getMax()<=max) {
                // forces the variable to be smaller than [min,max]
                var.adjustMax(cp, cs, this, min-1);
            }
        }
    } 
}
/*
 * $Log$
 * Revision 1.10  2005/07/18 15:33:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.9  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.8  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.7  2004/06/24 18:38:10  yan
 * fixed doc
 *
 * Revision 1.6  2004/06/24 14:38:08  yan
 * small optims
 *
 * Revision 1.5  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.4  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.3  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.2  2003/08/15 10:08:31  yan
 * fixed javadoc
 *
 * Revision 1.1  2003/08/04 17:29:18  yan
 * moved from AllDifferent
 *
 * Revision 1.12  2003/06/17 13:36:19  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.11  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.10  2003/03/27 11:01:47  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.9  2003/02/27 13:27:02  yan
 * made some code static
 *
 * Revision 1.8  2003/02/11 10:26:41  yan
 * fixed style
 *
 * Revision 1.7  2003/02/02 21:14:55  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.6  2002/10/08 17:18:31  yan
 * added complexity property
 *
 * Revision 1.5  2002/09/16 08:04:05  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/06/16 13:47:16  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:21:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
