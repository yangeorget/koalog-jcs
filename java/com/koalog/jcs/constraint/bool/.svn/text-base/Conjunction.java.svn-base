package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;

/**
 * This constraint enforces x=y_0 and ... y_n-1.
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">yes</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">n</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class Conjunction extends BaseConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private BooleanVariable[] y;
    private BooleanVariable x;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x an boolean variable
     * @param y an array of boolean variables
     */    
    public Conjunction(BooleanVariable x, BooleanVariable[] y) {
        super();
        this.name = "x=and(y)";
        this.variables = new BooleanVariable[y.length + 1];
        this.variables[0] = x;
        System.arraycopy(y, 0, variables, 1, y.length); 
        this.x = x;
        this.y = y;
        complexity = y.length; 
        idempotent = true;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((BooleanVariable) variables[i]).addGroundConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (x.isTrue()) {
            for (int i=y.length; --i>=0;) {
                y[i].adjustTrue(cp, cs, this);
            }
            // no need to call entailed 
            // since all the variables are instantiated
            return;
        } else if (x.isFalse()) { // x is false
            int f = -1;
            int i = -1;
            int n = 0;
            while(++i<y.length && n<2) {
                if (!y[i].isTrue()) {
                    f = i;
                    n++;
                }
            }
            switch (n) {
                case 0:
                    fail();
                case 1:
                    y[f].adjustFalse(cp, cs, this);
                    // no need to call entailed 
                    // since all the variables are instantiated
                    return;
            }
        }
        if (BooleanVariable.oneFalse(y)) {
            x.adjustFalse(cp, cs, this);
            entailed(cp);
        } else if (BooleanVariable.allTrue(y)) {
            x.adjustTrue(cp, cs, this);
            // no need to call entailed 
            // since all the variables are instantiated
        } 
    }
}
/*
 * $Log$
 * Revision 1.21  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.20  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.19  2004/11/02 13:54:27  yan
 * optimized for loops
 *
 * Revision 1.18  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.17  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.16  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.15  2003/10/02 23:46:46  yan
 * introduced complexity computation
 *
 * Revision 1.14  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.13  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.12  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.11  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.10  2003/03/21 10:04:14  yan
 * fixed style
 *
 * Revision 1.9  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.8  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.7  2003/02/02 21:09:57  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.6  2002/10/08 17:19:26  yan
 * added complexity property
 *
 * Revision 1.5  2002/10/05 12:45:32  yan
 * cleaned a bit
 *
 * Revision 1.4  2002/10/04 16:23:51  yan
 * various optimizations
 *
 * Revision 1.3  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
