package com.koalog.jcs.optimizer;

import java.util.Locale;
import org.apache.log4j.Category;
import com.koalog.util.I18N;
import com.koalog.jcs.InconsistencyException;
import com.koalog.jcs.TimeOutException;
import com.koalog.jcs.solver.Solver;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This abstract class describes optimizers.
 *
 * <P>The <CODE>BaseOptimizer</CODE> class has two interesting methods, 
 * that can both be overriden:
 * <UL>
 * <LI><CODE>noBetterSolution</CODE>, which is called when 
 * it is not possible to find a better solution;</LI>
 * <LI><CODE>localOptimumFound</CODE>, 
 * which is called each time a local optimum is found.</LI>
 * </UL>
 * </P>
 *
 * <P>Note: Optimizers, starting from <CODE>BaseOptimizer</CODE>, 
 * may generate internationalized log messages.</P>
 *
 * @author Yan Georget
 */
public abstract class BaseOptimizer implements Optimizer { 
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BaseOptimizer.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The solver used to optimize.*/
    protected Solver solver;
    /** The variable to optimize.*/
    protected IntegerVariable variable;
    /** The current optimum.*/
    protected int optimum;
    /** The mode (RESTART or CONTINUE) for exploring the search tree. */
    protected int mode;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /**
     * Sole constructor.
     * @param variable the variable to optimize
     * @param solver the solver used to optimize
     * @param mode an integer indicating the type of exploration 
     * of the search tree
     */ 
    public BaseOptimizer(IntegerVariable variable,
                         Solver solver,
                         int mode) {
        this.solver = solver;
        this.variable = variable;
        this.mode = mode;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.optimizer.Optimizer */
    public final int getOptimum() {
        return optimum;
    }

    /** @see com.koalog.jcs.optimizer.Optimizer */
    public final IntegerVariable getVariable() {
        return variable;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /** @see com.koalog.jcs.optimizer.Optimizer */
    public void optimize() {
        optimize(Long.MAX_VALUE/2);
    }
 
    /** @see com.koalog.jcs.optimizer.Optimizer */
    public void optimize(long time) {
        solver.initCounters();
        try {
            findBetterSolutions(System.currentTimeMillis() + time);
        } catch (InconsistencyException ie) {
            solver.resetCounters();
            noBetterSolution();
        } catch (TimeOutException toe) {
            solver.resetCounters();
            solver.timeOut();
        }
        optimized();
    }

    /**
     * Does something when a local optimum is found.
     */
    public void localOptimumFound() {
        StringBuffer buf = new StringBuffer();
        buf.append(optimum);
        buf.append(" "); 
        buf.append(I18N.getString(Solver.I18N_RESOURCE, 
                                  "IS_A_NEW_OPTIMUM_FOR", 
                                  Locale.getDefault()));
        buf.append(" "); 
        buf.append(variable.getName());
        cat.info(buf);
    }

    /**
     * Does something when it is not possible to optimize anymore.
     */
    public void noBetterSolution() {
        solver.noMoreSolution();
    }

    /** @see com.koalog.jcs.optimizer.Optimizer */
    public void optimized() {
        solver.solved();
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Finds solutions that are better and better during a given time.
     * @param timeOut the time-out in milliseconds
     * @throws InconsistencyException when no solution can be found
     * @throws TimeOutException when a time-out occurs
     */
    public abstract void findBetterSolutions(long timeOut) 
        throws InconsistencyException, TimeOutException;
}
/*
 * $Log$
 * Revision 1.17  2005/07/19 07:40:38  yan
 * real anytime constraint solving
 *
 * Revision 1.16  2005/07/18 17:48:05  yan
 * fixed jdoc
 *
 * Revision 1.15  2005/07/18 17:36:50  yan
 * fixed style
 *
 * Revision 1.14  2005/07/18 17:26:08  yan
 * introduced TimeOutException + various simplifications
 *
 * Revision 1.13  2004/09/17 14:45:00  yan
 * various cleaning
 *
 * Revision 1.12  2004/09/17 14:18:51  yan
 * various cleaning
 *
 * Revision 1.11  2003/03/20 15:25:25  yan
 * added top-level anytime method
 *
 * Revision 1.10  2003/03/20 15:15:00  yan
 * added anytime optimizer
 *
 * Revision 1.9  2003/03/07 13:53:29  yan
 * fixed style
 *
 * Revision 1.8  2003/01/31 12:19:02  yan
 * changed exception handling
 *
 * Revision 1.7  2002/10/25 09:05:21  yan
 * cleaned category
 *
 * Revision 1.6  2002/10/04 13:17:29  yan
 * cleaned
 *
 * Revision 1.5  2002/06/16 16:09:44  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:57:24  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:14:13  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
