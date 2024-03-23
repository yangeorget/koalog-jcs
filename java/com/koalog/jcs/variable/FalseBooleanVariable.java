package com.koalog.jcs.variable;

/**
 * This class represents a boolean variable which is false 
 * (its domain is reduced to 0).
 *   
 * @author Yan Georget
 */
public final class FalseBooleanVariable extends BooleanVariable {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Constructs a false boolean variable.
     */
    public FalseBooleanVariable() {
        super(0);
    }

    /**
     * Constructs a false boolean variable with a given name.
     * @param name the name of the boolean variable
     */
    public FalseBooleanVariable(String name) {
        super(name, 0);
    }
}
/*
 * $Log$
 * Revision 1.6  2003/03/07 13:51:36  yan
 * fixed style
 *
 * Revision 1.5  2002/10/04 16:22:49  yan
 * made final
 *
 * Revision 1.4  2002/06/16 14:00:27  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:11:03  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
