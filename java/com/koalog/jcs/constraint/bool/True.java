package com.koalog.jcs.constraint.bool;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.UnaryConstraint;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This constraint enforces x=1.
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
public class True extends UnaryConstraint {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param x a boolean variable
     */
    public True(IntegerVariable x) {
        super(x);
        name = "x=1";
        idempotent = true;
        complexity = 1;
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Sets the variable to true.
     * @param cp the choice point stack
     * @param cs a constraint scheduler
     * @throws ConstraintInconsistencyException when there is no solution
     */
    public void init(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        // we want to be able to do this on any IntegerVariable
        ((IntegerVariable) variables[0]).adjustValue(cp, cs, this, 1); 
        // no need to call entailed(cp)
    }

    /**
     * Does nothing.
     * @param cp the choice point stack
     * @param cs a constraint scheduler
     * @throws ConstraintInconsistencyException when there is no solution
     */
    public void filter(ChoicePointStack cp,
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
    }
}
/*
 * $Log$
 * Revision 1.17  2005/07/18 15:26:25  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.16  2004/06/27 13:01:35  yan
 * fixed complexity
 *
 * Revision 1.15  2004/06/23 18:41:53  yan
 * now accepts IntegerVariable in constructor
 *
 * Revision 1.14  2004/06/10 16:28:07  yan
 * various cleaning
 *
 * Revision 1.13  2004/05/05 11:48:07  yan
 * fixed style
 *
 * Revision 1.12  2004/04/09 14:44:58  yan
 * *** empty log message ***
 *
 * Revision 1.11  2004/03/14 16:43:26  yan
 * various simplifications
 *
 * Revision 1.10  2003/08/03 17:56:36  yan
 * optimized boolean adjust methods
 *
 * Revision 1.9  2003/06/17 13:58:37  yan
 * added jdoc about algorithmic infos
 *
 * Revision 1.8  2003/04/01 14:00:15  yan
 * fixed complexity
 *
 * Revision 1.7  2003/03/27 11:04:36  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.6  2003/03/07 11:41:18  yan
 * fixed style
 *
 * Revision 1.5  2003/02/02 21:12:51  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.4  2002/06/16 13:48:23  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:19:06  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
