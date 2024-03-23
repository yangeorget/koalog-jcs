package com.koalog.jcs.solver;

import org.apache.log4j.Category;
import java.util.Locale;
import com.koalog.util.I18N;
import com.koalog.jcs.constraint.Problem;

/**
 * This type of solver finds one solution after the other.
 *
 * <P>The <CODE>BaseSolver</CODE> class has an interesting method
 * <CODE>solutionFound</CODE>, which can be overriden
 * and which is called each time a new solution is found.
 *
 * @author Yan Georget
 */
public abstract class BaseSolver extends ExactSolver {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BaseSolver.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------  
    /** The time for finding the current solution. */
    protected long time;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /**
     * Constructs a solver for a given problem.
     * @param problem the problem to be solved.
     */
    public BaseSolver(Problem problem) {
        super(problem);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.solver.Solver */
    public void resetCounters() {
        super.resetCounters();
        time = System.currentTimeMillis();
    }

    /** @see com.koalog.jcs.solver.Solver */
    public void initCounters() {
        super.initCounters();
        time = System.currentTimeMillis();
    }

    /**
     * Adds the last solution found to the stack of solutions 
     * and displays the informations about this solution.
     * 
     * <P>To do something each time a solution is found:
     * <PRE>
     * public void solutionFound() {
     *     super.solutionFound();
     *     // do something
     * }
     * </PRE>
     */
    public void solutionFound() {
        long elapsedTime = System.currentTimeMillis() - time;
        Locale locale = Locale.getDefault();
        StringBuffer buf = 
            new StringBuffer(I18N.getString(I18N_RESOURCE, 
                                            "SOLUTION_FOUND_IN", 
                                            locale));
        buf.append(" ");
        buf.append(elapsedTime);
        buf.append(" ");
        buf.append(I18N.getString(I18N_RESOURCE, 
                                  "MS", 
                                  locale));
        cat.info(buf); 
        storeSolution();
    }
}
/*
 * $Log$
 * Revision 1.33  2004/10/11 15:06:59  yan
 * now extends ExactSolver
 *
 * Revision 1.32  2004/10/07 12:14:33  yan
 * added back resetCounters in Solver
 *
 * Revision 1.31  2004/09/17 15:38:14  yan
 * more refactoring
 *
 * Revision 1.30  2004/09/17 14:59:06  yan
 * moved stuff to AbstractSolver
 *
 * Revision 1.29  2004/09/17 14:45:00  yan
 * various cleaning
 *
 * Revision 1.28  2004/09/17 14:18:51  yan
 * various cleaning
 *
 * Revision 1.27  2004/09/17 09:36:08  yan
 * using deepAdd instead of add
 *
 * Revision 1.26  2004/09/16 14:41:48  yan
 * small optims
 *
 * Revision 1.25  2004/07/13 23:14:27  yan
 * fixed style
 *
 * Revision 1.24  2004/07/06 13:00:36  yan
 * externalized display
 *
 * Revision 1.23  2004/07/06 12:47:18  yan
 * license statically checked
 *
 * Revision 1.22  2004/07/06 12:21:43  yan
 * added infos display
 *
 * Revision 1.21  2004/06/25 15:45:54  yan
 * added logs
 *
 * Revision 1.20  2004/06/25 14:59:15  yan
 * dependencies are now updated in the solver
 *
 * Revision 1.19  2004/06/17 13:14:48  yan
 * made checkLicense method private
 *
 * Revision 1.18  2004/06/14 15:55:52  yan
 * using storable references and values
 *
 * Revision 1.17  2004/06/10 14:45:54  yan
 * added a new solve method
 *
 * Revision 1.16  2004/06/10 14:37:06  yan
 * added jdoc
 *
 * Revision 1.15  2004/05/06 09:54:06  yan
 * fixed toString method
 *
 * Revision 1.14  2003/08/02 15:21:21  yan
 * using clear method on solutions
 *
 * Revision 1.13  2003/06/05 12:19:18  yan
 * fixed style
 *
 * Revision 1.12  2003/06/04 17:52:57  yan
 * changed modulus
 *
 * Revision 1.11  2003/06/04 16:34:00  yan
 * added license
 *
 * Revision 1.10  2003/03/07 13:52:52  yan
 * fixed style
 *
 * Revision 1.9  2003/02/10 10:58:52  yan
 * new method findSolutions(long time)
 *
 * Revision 1.8  2002/10/25 09:05:21  yan
 * cleaned category
 *
 * Revision 1.7  2002/06/16 13:59:14  yan
 * added javadoc
 *
 * Revision 1.6  2002/05/22 09:27:25  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/02 16:13:18  yan
 * moved
 *
 * Revision 1.3  2002/04/22 12:47:44  yan
 * added setProblem method
 *
 * Revision 1.2  2002/04/19 09:53:44  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
