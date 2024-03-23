package com.koalog.jcs.solver;

import com.koalog.jcs.variable.Variable;

/**
 * The <CODE>VariableHeuristic</CODE> interface is the root 
 * of the variable heuristic hierarchy. 
 * All variable heuristics implement the methods of this interface.
 *
 * <P>A variable heuristic is responsible for choosing a variable 
 * among an array of variables. It must return <CODE>null</CODE> when all the
 * variables are instantiated.
 * 
 * <P>Example:
 * <PRE>
 * public class KeepOrderVariableHeuristic implements VariableHeuristic {
 *    public Variable chooseVariable(Variable[] variables) {
 *       for (int i=0; i&lt;variables.length; i++) {
 *           if (variables[i].isNotInstantiated()) {
 *               return variables[i];
 *           }
 *       } 
 *       return null;
 *    }
 * }
 * </PRE>
 *  
 * @author Yan Georget
 */
public interface VariableHeuristic {
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Chooses a variable among an array of variables.
     * @param variables an array of variables
     * @return the chosen variable
     */
    Variable chooseVariable(Variable[] variables);
}
/*
 * $Log$
 * Revision 1.8  2004/06/17 11:21:45  yan
 * added jdoc
 *
 * Revision 1.7  2002/06/16 19:46:46  mphilip
 * Modified jdoc.
 *
 * Revision 1.6  2002/06/16 13:59:15  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
