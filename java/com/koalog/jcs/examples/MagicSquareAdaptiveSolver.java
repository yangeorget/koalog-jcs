package com.koalog.jcs.examples;

import org.apache.log4j.Category;
import java.util.Iterator;
import com.koalog.jcs.solver.PermutationAdaptiveSolver;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.constraint.AbstractRelation;
import com.koalog.jcs.constraint.arithmetic.ConstantSum;

/**
 * An adaptive solver for the magic square problem.
 *
 * @author Yan Georget
 */
public class MagicSquareAdaptiveSolver extends PermutationAdaptiveSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(MagicSquareAdaptiveSolver.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.    
     * @param p a magic square problem
     */
    public MagicSquareAdaptiveSolver(MagicSquareProblem p) {
        super(p, 
              50,
              (long) (p.getVariables().length
                      * Math.sqrt(p.getVariables().length)),
              new Object[][] {{ 
                  ConstantSum.class, 
                  new ErrorFct() {
                      public int error(AbstractRelation r) {
                          ConstantSum cs = (ConstantSum) r;
                          int err = -cs.getSum();
                          IntegerVariable[] variables = 
                              (IntegerVariable[]) cs.getVariables();
                          for (int i=variables.length; --i>=0;) {
                              err += variables[i].getMin();
                          }
                          return err; 
                      } 
                  }
              }}, 
              (int) Math.sqrt(p.getVariables().length)-1,
              p.getVariables().length/6,
              p.getVariables().length/10,
              0.06f);
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

    /** 
     * Optimized version of parent method.
     *
     * @see com.koalog.jcs.solver.PermutationAdaptiveSolver 
     */
    protected void updateVariableErrorsWhenSwapping(int vi, int vj) {
        int epsilon = ((IntegerVariable) variables[vj]).getMin() 
            - ((IntegerVariable) variables[vi]).getMin();
        for (Iterator i = relationsByVariables[vi].iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vj)) {
                for (int j=r.getVariables().length; --j>=0;) {
                    errorsByVariables[j] += epsilon;
                }
            }
        }
        for (Iterator i = relationsByVariables[vj].iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vi)) {
                for (int j=r.getVariables().length; --j>=0;) {
                    errorsByVariables[j] -= epsilon;
                }
            }
        }
        swap(vi, vj);
    }

    /** @see com.koalog.jcs.solver.PermutationAdaptiveSolver */
    public int getErrorDiffWhenSwapping(int vi, int vj) {
        int epsilon = ((IntegerVariable) variables[vj]).getMin() 
            - ((IntegerVariable) variables[vi]).getMin();
        int diff = 0;
        for (Iterator i = relationsByVariables[vi].iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vj)) {
                int e = ((Integer) errorsByRelations.get(r)).intValue();
                diff += Math.abs(e+epsilon) - Math.abs(e);
            }
        }
        for (Iterator i = relationsByVariables[vj].iterator(); i.hasNext();) {
            AbstractRelation r = (AbstractRelation) i.next();
            if (!constrains(r, vi)) {
                int e = ((Integer) errorsByRelations.get(r)).intValue();
                diff += Math.abs(e-epsilon) - Math.abs(e);
            }
        }
        return diff;
    }
}


