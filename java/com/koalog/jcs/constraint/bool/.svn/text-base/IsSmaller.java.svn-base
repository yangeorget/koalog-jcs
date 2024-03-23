package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces b<=>(x&lt;c) where x is an integer variable.
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
 *    <TD align="right">1</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class IsSmaller extends BinaryConstraint {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int c;
    private BooleanVariable b;
    private IntegerVariable x;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param b a boolean variable
     * @param x an integer variable
     * @param c an integer
     */
    public IsSmaller(BooleanVariable b, IntegerVariable x, int c) {
        super(b,x);
        this.c = c;
        this.b = b;
        this.x = x;
        name = "b<=>x<" + c;
        idempotent = true;
        complexity = 1;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        b.addGroundConstraint(this);
        x.addMinMaxConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (b.isFalse()) {
            x.adjustMin(cp, cs, this, c);
            entailed(cp);
        } else if (b.isTrue()) { 
            x.adjustMax(cp, cs, this, c-1);
            entailed(cp);
        } else if (x.getMax()<c) {
            b.adjustTrue(cp, cs, this);
            entailed(cp);
        } else if (x.getMin()>=c) {
            b.adjustFalse(cp, cs, this);
            entailed(cp);
        }
    }
}
/*
 * $Log$
 * Revision 1.19  2005/07/25 09:48:21  yan
 * fixed 0021281
 *
 * Revision 1.18  2005/07/18 15:28:21  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.17  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.16  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.15  2004/05/05 11:48:06  yan
 * fixed style
 *
 * Revision 1.14  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.13  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.12  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.11  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.10  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.9  2003/03/20 21:06:12  yan
 * used entailed statement
 *
 * Revision 1.8  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.7  2003/02/02 21:12:51  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.6  2002/10/04 16:23:51  yan
 * various optimizations
 *
 * Revision 1.5  2002/06/20 12:08:32  yan
 * fixed javadoc typos
 *
 * Revision 1.4  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:45:28  yan
 * optimized computation
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
