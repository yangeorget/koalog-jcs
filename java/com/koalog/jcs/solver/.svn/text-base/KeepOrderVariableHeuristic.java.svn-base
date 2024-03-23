package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;

/**
 * This class represents the variable heuristic that 
 * chooses the first non-instantiated variable that it finds.
 *
 * @see Variable
 * @author Yan Georget
 */
public class KeepOrderVariableHeuristic implements VariableHeuristic {
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses the first variable which is not instantiated.
     * @param variables an array of variables
     * @return a variable
     */    
    public Variable chooseVariable(Variable[] variables) {
        for (int i=0; i<variables.length; i++) {
            if (variables[i].isNotInstantiated()) {
                return variables[i];
            }
        } 
        return null;
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(KeepOrderVariableHeuristic.class);
}
/*
 * $Log$
 * Revision 1.7  2004/11/29 09:32:24  yan
 * using isNotInstantiated
 *
 * Revision 1.6  2004/09/15 12:48:35  yan
 * added log4j category
 *
 * Revision 1.5  2002/06/16 17:49:54  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:59:15  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
