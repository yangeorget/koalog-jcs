package com.koalog.jcs.constraint.arithmetic;

import java.util.List;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.domain.SparseDomain;

/**
 * This constraint enforces any relation 
 * described by its set of allowed tuples.
 *
 * <P>Exemple:
 * <PRE>
 * IntegerVariable[] vars = new IntegerVariable[] {x, y, z};
 * add(new Relation(vars,
 *                  new int[][] {
 *                      {0,0,0},
 *                      {1,1,1},
 *                      {2,2,2}
 *                  }));
 * </PRE>
 * enforces that:
 * <UL>
 * <LI>all the variables are equal to 0 or,
 * <LI>all the variables are equal to 1 or,
 * <LI>all the variables are equal to 2.
 * </UL>
 * </P>
 *
 * @since 1.5
 * @author Yan Georget
 */
public class Relation extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param variables an array of integer variables
     * @param values a set (as an array) of allowed tuples (as int arrays)
     */
    public Relation(IntegerVariable[] variables, int[][] values) {
        super("relation"); // do we want to pretty-print this problem?
        IntegerVariable index =
            new IntegerVariable(new SparseDomain(0, values.length-1));
        for(int i=0; i<variables.length; i++) {
            int[] vals = new int[values.length];
            for (int j=0; j<values.length; j++) {
                vals[j] = values[j][i];
            }
            add(new Element(variables[i], index, vals));
        }
        setVariables(variables);
    }

    /**
     * Auxilliary constructor.
     * @param variables an array of integer variables
     * @param values a set (as a list) of allowed tuples (as int arrays)
     * @since 3.1
     */
    public Relation(IntegerVariable[] variables, List values) {
        super("relation"); // do we want to pretty-print this problem?
        IntegerVariable index =
            new IntegerVariable(new SparseDomain(0, values.size()-1));
        for(int i=0; i<variables.length; i++) {
            int[] vals = new int[values.size()];
            for (int j=0; j<values.size(); j++) {
                vals[j] = ((int[]) values.get(j))[i];
            }
            add(new Element(variables[i], index, vals));
        }
        setVariables(variables);
    }
}
