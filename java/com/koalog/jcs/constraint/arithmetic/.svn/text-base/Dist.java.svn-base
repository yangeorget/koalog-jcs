package com.koalog.jcs.constraint.arithmetic;

import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.BaseProblem;

/**
 * This constraint enforces d=|x-y|.
 * @author Yan Georget
 */
public class Dist extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param d an integer variable
     * @param x an integer variable
     * @param y an integer variable
     */    
    public Dist(IntegerVariable d, IntegerVariable x, IntegerVariable y) {
        super("d=|x-y|");
        IntegerVariable z = new IntegerVariable();
        add(new Add(x,y,z));
        add(new Abs(d,z));
        setVariables(new Variable[] {d,x,y}); 
    }
}
/*
 * $Log$
 * Revision 1.9  2004/11/18 17:44:33  yan
 * *** empty log message ***
 *
 * Revision 1.7  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.6  2003/06/17 13:47:11  yan
 * fixed jdoc
 *
 * Revision 1.5  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.4  2003/03/09 20:05:10  yan
 * name fix
 *
 * Revision 1.3  2002/05/02 16:21:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
