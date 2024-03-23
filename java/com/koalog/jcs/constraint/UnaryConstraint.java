package com.koalog.jcs.constraint;

import com.koalog.jcs.variable.Variable;

/**
 * This class represents an unary constraint 
 * (constraint over 1 variable).
 * @author Yan Georget
 */
public abstract class UnaryConstraint extends BaseConstraint {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Constructs an unary constraint.
     * @param var a Variable
     */
    public UnaryConstraint(Variable var) {
        super(new Variable[] {var});
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
    }
}
/*
 * $Log$
 * Revision 1.9  2004/09/17 12:53:10  yan
 * renamed updateConstraints
 *
 * Revision 1.8  2004/06/24 14:20:27  yan
 * fixed complexity
 *
 * Revision 1.7  2004/06/21 16:03:40  yan
 * various fixes
 *
 * Revision 1.6  2004/03/14 16:44:33  yan
 * various cleaning
 *
 * Revision 1.5  2003/06/17 09:57:21  yan
 * added jdoc
 *
 * Revision 1.4  2002/10/08 17:20:25  yan
 * added complexity property
 *
 * Revision 1.3  2002/05/02 16:24:38  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
