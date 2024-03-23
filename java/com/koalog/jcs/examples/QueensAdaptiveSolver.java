package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import java.util.Iterator;
import com.koalog.jcs.solver.PermutationAdaptiveSolver;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.AbstractRelation;

/**
 * An adaptive solver for the queens problem.
 *
 * @author Yan Georget
 */
public class QueensAdaptiveSolver extends PermutationAdaptiveSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(QueensAdaptiveSolver.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.    
     * @param p a queens problem
     */
    public QueensAdaptiveSolver(QueensProblem_ADAPTIVE p) {
        super(p, 
              30,
              p.getVariables().length * p.getVariables().length,
              new Object[][] {{
                  QueensProblem_ADAPTIVE.Neq_SHIFT.class, 
                  new ErrorFct() {
                      public int error(AbstractRelation r) {
                          QueensProblem_ADAPTIVE.Neq_SHIFT ns = 
                              (QueensProblem_ADAPTIVE.Neq_SHIFT) r;
                          Variable[] vars = (Variable[]) ns.getVariables();
                          int diff = ((IntegerVariable) vars[0]).getMin() 
                              - ((IntegerVariable) vars[1]).getMin();
                          return diff==ns.getShift() || diff==-ns.getShift()
                              ? 1 : 0; 
                      } 
                  }
              }},
              2, 
              p.getVariables().length/5,
              p.getVariables().length/10,
              0.0f);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.AdaptiveSolver */
    public void updateVariableError(int i) {
        errorsByVariables[i] = 0;
        for (Iterator j = relationsByVariables[i].iterator(); j.hasNext();) {
            errorsByVariables[i] += 
                ((Integer) errorsByRelations.get(j.next())).intValue();
        }
    }
    
    /** @see com.koalog.jcs.solver.PermutationAdaptiveSolver */
    public int getErrorDiffWhenSwapping(int vi, int vj) {
        int diff = 0;
        swap(vi, vj);
        for (Iterator i = relationsByVariables[vi].iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vj)) {
                diff += getRelationError(r)
                    - ((Integer) errorsByRelations.get(r)).intValue();
            }
        }
        for (Iterator i = relationsByVariables[vj].iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vi)) {
                diff += getRelationError(r) 
                    - ((Integer) errorsByRelations.get(r)).intValue();
            }
        }
        swap(vi, vj);
        return diff;
    }
}
