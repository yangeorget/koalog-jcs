package com.koalog.jcs.constraint;

import org.apache.log4j.Category;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.variable.Variable;
import com.koalog.jcs.choicepoint.ChoicePointStack;

/**
 * This class is the reification of the concept of constraint.
 *
 * <P>Subclasses of <CODE>BaseConstraint</CODE> implement the abstract 
 * <CODE>filter</CODE> and <CODE>updateConstraints</CODE>methods. 
 * The following example is an implementation of constraint <CODE>Leq</CODE>
 * on integer variables:
 * <PRE>
 * public class Leq extends BinaryConstraint {
 *    private IntegerVariable x;
 *    private IntegerVariable y;
 * 
 *    public Leq(IntegerVariable x, IntegerVariable y) {
 *        super(x, y);
 *        this.x = x;
 *        this.y = y;
 *        name = "x<=y";
 *        idempotent = true;
 *        complexity = 1;
 *    }
 *    
 *    public void updateDependencies() {
 *        x.addMinConstraint(this);
 *        y.addMaxConstraint(this);
 *    }
 * 
 *    public void filter(ChoicePointStack cp,
 *                       ConstraintScheduler cs) 
 *        throws ConstraintInconsistencyException {
 *        x.adjustMax(cp, cs, this, y.getMax());
 *        y.adjustMin(cp, cs, this, x.getMin());
 *        if (x.getMax() <= y.getMin()) {
 *            entailed(cp);
 *        }
 *    }
 * }
 * </PRE>
 *
 * <P>Note that a constraint must not have any side effect on a variable 
 * without recording the changes to the solver 
 * (more precisely, to the choice point stack, 
 * in the case of a backtrack solver). 
 *
 * @see com.koalog.jcs.choicepoint.ChoicePointStack
 * @see com.koalog.jcs.constraint.arithmetic.Leq
 * @author Yan Georget
 */
public abstract class BaseConstraint 
    extends AbstractRelationImpl 
    implements Constraint {

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BaseConstraint.class);
    private static ConstraintInconsistencyException CIE = 
       new ConstraintInconsistencyException();

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** A boolean indicating whether the constraint is idempotent. */
    protected boolean idempotent;
    /** The complexity of the filtering of the constraint. */
    protected int complexity; 
    /** The date of entailment. */
    int dateEntailed;
    /** The date when scheduled. */
    int dateScheduled;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Constructs a constraint.
     * @param variables the array of the constraint variables
     */
    public BaseConstraint(Variable[] variables) {
        this();
        this.variables =  variables;
    }

    /** 
     * Internal constructor.
     */
    protected BaseConstraint() {
        super();
        idempotent = false; // unless specified 
        complexity = 1; // complexity has to be defined for each constraint 
        dateEntailed = dateScheduled = -1;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public final boolean isIdempotent() {
        return idempotent;
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public final int getComplexity() {
        return complexity;
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public final int getDateScheduled() {
        return dateScheduled;
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public final int getDateEntailed() {
        return dateEntailed;
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public final void setDateScheduled(int dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public final void fail() throws ConstraintInconsistencyException {
        CIE.setConstraint(this);
        throw CIE;
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public final boolean hasConstantComplexity() {
        return complexity <= 1; 
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp, ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        // default implementation does nothing
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public final void entailed(ChoicePointStack cp) {
        dateEntailed = cp.getDate(); 
    }

    /** 
     * Converts the constraint to a string.
     * @return a string 
     */
    public String toString() {
        StringBuffer b = new StringBuffer(getFullName());
        b.append("(");
        for(int i=0; i<variables.length; i++) {
            b.append(variables[i]);
            if (i != variables.length-1) {
                b.append(",");
            }
        }
        b.append(")");
        return b.toString();    
    }
}
/*
 * $Log$
 * Revision 1.35  2005/09/20 17:29:20  yan
 * getComplexity is not final anymore
 *
 * Revision 1.34  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.33  2005/07/18 09:47:15  yan
 * fixed jdoc
 *
 * Revision 1.32  2004/10/08 10:14:09  yan
 * constraints and problems now share ids
 *
 * Revision 1.31  2004/10/01 17:49:40  yan
 * fixed style
 *
 * Revision 1.30  2004/10/01 17:41:40  yan
 * various small refactoring
 *
 * Revision 1.29  2004/09/30 15:42:52  yan
 * using AbstractRelation
 *
 * Revision 1.28  2004/09/16 12:27:42  yan
 * fixed style
 *
 * Revision 1.27  2004/09/15 16:02:31  yan
 * using new counter mechanism
 *
 * Revision 1.26  2004/06/24 14:19:25  yan
 * set of scheduled constraints encoded in the constraints
 *
 * Revision 1.25  2004/06/21 16:03:40  yan
 * various fixes
 *
 * Revision 1.24  2004/06/15 19:22:37  yan
 * fixed jdoc
 *
 * Revision 1.23  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.22  2004/05/06 09:54:05  yan
 * fixed toString method
 *
 * Revision 1.21  2004/05/05 11:44:47  yan
 * fixed style
 *
 * Revision 1.20  2004/04/09 14:42:15  yan
 * removal of events
 *
 * Revision 1.19  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.18  2004/03/14 16:41:43  yan
 * removed init method
 *
 * Revision 1.17  2003/10/02 20:25:14  yan
 * made some methods final
 *
 * Revision 1.16  2003/06/05 14:40:30  yan
 * added explanations on updateConstraints
 *
 * Revision 1.15  2003/04/01 13:53:15  yan
 * various fixes
 *
 * Revision 1.14  2003/03/27 10:52:30  yan
 * added events related methods
 *
 * Revision 1.13  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.12  2003/03/09 17:00:32  yan
 * typos
 *
 * Revision 1.11  2003/02/11 10:24:33  yan
 * fixed style
 *
 * Revision 1.10  2003/02/02 21:14:54  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.9  2003/01/02 09:48:36  yan
 * added setComplexity
 *
 * Revision 1.8  2002/10/09 11:04:09  yan
 * a constraint is now a comparable
 *
 * Revision 1.7  2002/10/08 17:20:25  yan
 * added complexity property
 *
 * Revision 1.6  2002/10/07 10:17:39  yan
 * fixed style
 *
 * Revision 1.5  2002/06/16 16:02:36  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:47:16  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:24:38  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
