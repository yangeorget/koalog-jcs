package com.koalog.jcs;

/**
 * This exception is thrown when the solver detects an inconsistency.
 * 
 * <P>The solver can detect inconsistencies in various cases, 
 * specific subclasses should then be used.
 * 
 * @see com.koalog.jcs.BacktrackInconsistencyException
 * @see com.koalog.jcs.ConstraintInconsistencyException
 * @see com.koalog.jcs.VariableInconsistencyException
 *
 * @author Yan Georget
 */ 
public class InconsistencyException extends SolverException {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param message a message
     */
    public InconsistencyException(String message) {
        super(message);
    }
    
    /**
     * Auxilliary constructor.
     */
    public InconsistencyException() {
        super();
    }
}
/*
 * $Log$
 * Revision 1.8  2005/07/19 07:38:18  yan
 * now extends SolverException
 *
 * Revision 1.7  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.6  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.5  2004/05/05 11:49:29  yan
 * fixed style and jdoc
 *
 * Revision 1.4  2002/06/16 13:45:04  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:23:47  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
