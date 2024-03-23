package com.koalog.jcs.constraint.set;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.BinaryConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.variable.SetVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces: the intersection of y and z is empty.
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
 *    <TD align="right">max(card(y),card(z))</TD>
 * </TR>
 * </TABLE>
 *
 * @author Yan Georget
 */
public class EmptyIntersection extends BinaryConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(EmptyIntersection.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private SetVariable y;
    private SetVariable z;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param y a set variable
     * @param z a set variable
     */
    public EmptyIntersection(SetVariable y, SetVariable z) {
        super(y, z);
        this.y = y;
        this.z = z;
        name = "empty_intersection(y,z)";
        idempotent = true;
        complexity = Math.max(y.getMax().size(), z.getMax().size());
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        y.addMinConstraint(this);
        z.addMinConstraint(this);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        y.adjustMaxByExcluding(cp, cs, this, z.getMin());
        z.adjustMaxByExcluding(cp, cs, this, y.getMin());
        // can we be sure there won't be any intersection?
        // Iterator i = y.getMax().iterator();
        // while (i.hasNext()) {
        //     if (z.getMax().contains(i.next())) {
        //         return modifiedVariables;
        //     }
        // }
        // entailed(cp);
    }
}
/*
 * $Log$
 * Revision 1.10  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.9  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.8  2004/05/05 11:48:49  yan
 * fixed style
 *
 * Revision 1.7  2004/04/09 14:45:23  yan
 * *** empty log message ***
 *
 * Revision 1.6  2003/10/02 23:47:33  yan
 * introduced complexity computation
 *
 * Revision 1.5  2003/07/31 16:42:13  yan
 * fixed complexity
 *
 * Revision 1.4  2003/07/21 12:29:00  yan
 * fixed style
 *
 * Revision 1.3  2003/07/21 12:21:49  yan
 * various optims
 *
 * Revision 1.2  2003/07/19 18:30:35  yan
 * fixed complexity (1)
 *
 * Revision 1.1  2003/07/12 22:54:16  yan
 * initial revision
 *
 * Revision 1.1  2003/07/12 17:55:28  yan
 * initial revision
 *
 */
