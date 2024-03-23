package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.InvalidConstraintException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint is a permutation constraint.
 *
 * @author Yan Georget
 */
public class Permutation extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param perm the permutation 
     * as an array of n variables with sparse domains 
     * containing values between 0 and n-1
     * @param iperm the inverse permutation 
     * as an array of n variables with sparse domains 
     * containing values between 0 and n-1
     */
    public Permutation(IntegerVariable[] perm, IntegerVariable[] iperm) {
        super("permutation");
        checkArguments(perm, iperm);
        int n = perm.length;
        add(new AllDifferent_SPARSE(perm));
        //add(new AllDifferent_SPARSE(iperm));
        /*
        for (int i=0; i<n; i++) {
           for (int j=0; j<n; j++) {
               BooleanVariable b = new BooleanVariable();
               add(new Equals_SPARSE(b, perm[i], j));
               add(new Equals_SPARSE(b, iperm[j], i));
           } 
        } 
        */
        add(new Inverse_AUX(perm, iperm));
        add(new Inverse_AUX(iperm, perm));
        variables = new IntegerVariable[2*n];
        System.arraycopy(perm, 0, variables, 0, n);
        System.arraycopy(iperm, 0, variables, n, n);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    static void checkArguments(IntegerVariable[] perm, 
                               IntegerVariable[] iperm) {
        if (perm.length != iperm.length) {
            throw new InvalidConstraintException("sizes of arrays differ");
        }
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * An auxilliary constraint for ensuring that 
     * a permutation is the inverse of another permutation. 
     *
     * <P>Should be used twice (see above).
     */
    static class Inverse_AUX extends BaseConstraint {
        int n;

        /**
         * Sole constructor.
         * @param perm a permutation (sparse domains)
         * @param iperm the inverse permutation (sparse domains)
         */
        public Inverse_AUX(IntegerVariable[] perm, IntegerVariable[] iperm) {
            super();
            this.name = "inverse";
            n = perm.length;
            variables = new IntegerVariable[2*n];
            System.arraycopy(perm, 0, variables, 0, n); 
            System.arraycopy(iperm, 0, variables, n, n); 
            complexity = n*n;
            idempotent = false;
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            // depends on perm
            for (int i=0; i<n; i++) {
                ((IntegerVariable) variables[i]).addDomConstraint(this);
                ((IntegerVariable) variables[i+n]).addGroundConstraint(this);
            }
        }
        
        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            // we don't want to split this into n constraints
            for (int i=n; --i>=0;) { 
                final IntegerVariable var = (IntegerVariable) variables[i+n];
                final SparseDomain dom = (SparseDomain) var.getDomain();
                if (dom.isNotSingleton()) {
                    final Integer ii = new Integer(i);
                    for (Iterator l= dom.iterator(); l.hasNext();) {
                        final Integer j = (Integer) l.next();
                        if (!((SparseDomain) variables[j.intValue()]
                              .getDomain()).contains(ii)) {
                            var.adjustDifferent(cp, cs, this, j, l);
                        }
                    }
                }
                if (dom.isSingleton()) {
                    ((IntegerVariable) variables[dom.getMin()])
                        .adjustValue(cp, cs, this, i);
                } 
            }
        }
    }
}
