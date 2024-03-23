package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;

/**
 * An abstract constraint used as a superclass for AbstractCheck, Count.
 * 
 * @author Yan Georget
 */
public abstract class AbstractCount extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The value. */
    protected int c;
    /** The value + 1. */
    private int cp1;
    /** The value - 1. */
    private int cm1;
    /** A list of non instantiated variables. */
    List vars;
    /** Two counters. */
    int nbEqual, nbDifferent;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param c an integer
     */    
    public AbstractCount(int c) {
        super();
        this.c = c;
        cp1 = c+1;
        cm1 = c-1;
        vars = new LinkedList();
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
    
    void enforceDifferent(ChoicePointStack cp,
                          ConstraintScheduler cs,
                          List vars,
                          int j) throws ConstraintInconsistencyException {
        boolean entailed = true;
        for (int i=j; --i>=0;) {
            final IntegerDomain dom = (IntegerDomain) variables[i].getDomain();
            if (c == dom.getMin()) {
                ((IntegerVariable) variables[i]).adjustMin(cp, cs, this, cp1);
            } else if (c == dom.getMax()) {
                ((IntegerVariable) variables[i]).adjustMax(cp, cs, this, cm1);
            } else {
                entailed = false;
            }
        }
        for (Iterator l = vars.iterator(); l.hasNext();) {
            final IntegerVariable var = (IntegerVariable) l.next();
            final IntegerDomain dom = (IntegerDomain) var.getDomain();
            if (c == dom.getMin()) {
                var.adjustMin(cp, cs, this, cp1);
            } else if (c == dom.getMax()) {
                var.adjustMax(cp, cs, this, cm1);
            } else {
                entailed = false;
            }
        }
        if (entailed) {
            entailed(cp);
        }
    }

    void enforceEqual(ChoicePointStack cp,
                      ConstraintScheduler cs,
                      List vars, 
                      int j) throws ConstraintInconsistencyException {
        for (int i=j; --i>=0;) {
            ((IntegerVariable) variables[i]).adjustValue(cp, cs, this, c);
        }
        for (Iterator l = vars.iterator(); l.hasNext();) {
            ((IntegerVariable) l.next()).adjustValue(cp, cs, this, c);
        }
        entailed(cp);
    }
}



