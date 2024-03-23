package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * This constraint enforces that its variables are strictly increasing.
 *
 * @author Yan Georget
 */
public class StrictlyIncreasing extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param variables an array of integer variables
     */    
    public StrictlyIncreasing(IntegerVariable[] variables) {
        super("x_0<...<x_n-1");
        for(int i=1; i<variables.length; i++) {
            add(new Less(variables[i-1],variables[i]));
        }
        setVariables(variables);
    }
}
/*
 * $Log$
 * Revision 1.12  2004/09/17 10:05:24  yan
 * changed internalAdd into add
 *
 * Revision 1.11  2004/06/25 15:00:37  yan
 * using internalAdd
 *
 * Revision 1.10  2004/05/17 13:50:06  yan
 * add constraints through a collection
 *
 * Revision 1.9  2004/04/01 19:17:03  yan
 * problem is not a constraint anymore
 *
 * Revision 1.8  2003/10/02 23:50:10  yan
 * introduced complexity computation
 *
 * Revision 1.7  2003/06/17 13:47:11  yan
 * fixed jdoc
 *
 * Revision 1.6  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.5  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.4  2003/03/09 20:05:10  yan
 * name fix
 *
 * Revision 1.3  2002/05/02 16:21:23  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
