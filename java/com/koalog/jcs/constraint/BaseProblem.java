package com.koalog.jcs.constraint;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Category;
import com.koalog.jcs.variable.Variable;

/**
 * This class is the reification of the concept of problem.
 *
 * <P>A problem is mainly a container for constraints.
 * A problem can be added (as a constraint would be) to the top-level problem:
 * in this case, its constraints are added to the top-level problem.
 *
 * @author Yan Georget
 * @see Constraint
 */
public class BaseProblem extends AbstractRelationImpl implements Problem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(BaseProblem.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The constraints of the problem. */
    protected Map constraints;
    /** The relations of the problem. */
    protected Collection relations;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     */
    public BaseProblem() {
        super();
        variables = new Variable[] {};
        relations = new ArrayList();
    }

    /**
     * Auxilliary constructor.
     * @param name the name of the problem
     */
    public BaseProblem(String name) {
        this();
        this.name = name;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Problem */
    public final Collection getRelations() {
        return relations;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Problem */
    public final Collection getConstraints() {
        return constraints.values();
    }

    /** @see com.koalog.jcs.constraint.Problem */
    public final Constraint getConstraint(int unid) {
        return (Constraint) constraints.get(new Integer(unid));
    }

    /** @see com.koalog.jcs.constraint.Problem */
    public void add(AbstractRelation relation) {
        relations.add(relation);
        relation.setParent(this);
    }

    /** @see com.koalog.jcs.constraint.Problem */
    public void add(Collection relations) {
        for (Iterator i = relations.iterator(); i.hasNext();) {
            add((AbstractRelation) i.next());
        }
    }

    /** @see com.koalog.jcs.constraint.Problem */
    public final void flattenConstraints() {
        constraints = new HashMap(relations.size());
        flattenConstraints(this);
    }

    /** @see com.koalog.jcs.constraint.Problem */
    public final void flattenConstraints(AbstractRelation o) {
        if (o instanceof Constraint) {
            Constraint c = (Constraint) o;
            constraints.put(new Integer(c.getUnid()), c);
        } else { // Problem
            for (Iterator i = ((Problem) o).getRelations().iterator(); 
                 i.hasNext();) {
                flattenConstraints((AbstractRelation) i.next());
            }
        }
    }

    /** @see com.koalog.jcs.constraint.Problem */
    public final void updateDependencies() {
        for (Iterator i = getConstraints().iterator(); i.hasNext();) {
            ((Constraint) i.next()).updateDependencies();
        }
    }

    /** 
     * Converts the problem to a string.
     * @return a string 
     */
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(name);
        b.append("(");
        for(int i=0; i<variables.length; i++) {
            b.append(variables[i]);
            if (i != variables.length-1) {
                b.append(",");
            }
        }
        b.append("):");
        for (Iterator j = getConstraints().iterator(); j.hasNext();) {
            b.append(j.next());
            b.append(" ");
        }
        return b.toString();    
    }
}
/*
 * $Log$
 * Revision 1.52  2005/09/20 17:31:33  yan
 * various fixes
 *
 * Revision 1.51  2005/07/22 14:54:30  yan
 * added method for getting a constraint from its unid
 *
 * Revision 1.50  2004/11/10 15:16:28  yan
 * optimized structures creation
 *
 * Revision 1.49  2004/11/05 15:32:54  yan
 * added a method for adding a collection of relations
 *
 * Revision 1.48  2004/10/08 10:14:09  yan
 * constraints and problems now share ids
 *
 * Revision 1.47  2004/10/01 17:49:40  yan
 * fixed style
 *
 * Revision 1.46  2004/10/01 17:41:40  yan
 * various small refactoring
 *
 * Revision 1.45  2004/10/01 15:25:08  yan
 * using name
 *
 * Revision 1.44  2004/09/30 15:42:52  yan
 * using AbstractRelation
 *
 * Revision 1.43  2004/09/17 13:11:32  yan
 * fixed style
 *
 * Revision 1.42  2004/09/17 12:53:10  yan
 * renamed updateConstraints
 *
 * Revision 1.41  2004/09/17 12:48:38  yan
 * remove deepAdd
 *
 * Revision 1.40  2004/09/17 10:04:59  yan
 * changed internalAdd into add
 *
 * Revision 1.39  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.38  2004/06/27 13:04:47  yan
 * added javadoc
 *
 * Revision 1.37  2004/06/25 14:59:45  yan
 * added new methods
 *
 * Revision 1.36  2004/05/05 22:47:41  yan
 * added a toString method
 *
 * Revision 1.35  2004/05/05 11:56:13  yan
 * fixed again add methods
 *
 * Revision 1.34  2004/05/05 11:44:47  yan
 * fixed style
 *
 * Revision 1.33  2004/04/01 19:15:30  yan
 * problem is not a constraint anymore
 *
 * Revision 1.32  2004/03/14 18:03:22  yan
 * changed filter API
 *
 * Revision 1.31  2004/03/14 16:41:43  yan
 * removed init method
 *
 * Revision 1.30  2004/01/14 10:43:38  yan
 * *** empty log message ***
 *
 * Revision 1.29  2003/10/02 19:33:20  yan
 * changed scheduler update API
 *
 * Revision 1.28  2003/08/02 17:11:45  yan
 * changed filtersNb computation
 *
 * Revision 1.27  2003/07/19 13:20:27  yan
 * replaced ConstraintScheduler's add method in filter method
 *
 * Revision 1.26  2003/07/12 17:56:57  yan
 * added commented debug statements
 *
 * Revision 1.25  2003/06/18 19:33:40  yan
 * various small optimizations
 *
 * Revision 1.24  2003/06/18 19:20:46  yan
 * various small optimizations
 *
 * Revision 1.23  2003/04/01 16:59:39  yan
 * added tests
 *
 * Revision 1.22  2003/04/01 13:53:15  yan
 * various fixes
 *
 * Revision 1.21  2003/03/27 10:52:31  yan
 * added events related methods
 *
 * Revision 1.20  2003/03/20 18:05:57  yan
 * added entailment check
 *
 * Revision 1.19  2003/03/09 20:07:14  yan
 * flat properties
 *
 * Revision 1.18  2003/03/09 18:45:12  yan
 * removed unused debug logs
 *
 * Revision 1.17  2003/03/07 11:18:35  yan
 * fixed style
 *
 * Revision 1.16  2003/02/11 10:24:34  yan
 * fixed style
 *
 * Revision 1.15  2003/02/04 19:21:36  yan
 * reusing constraint scheduler
 *
 * Revision 1.14  2003/02/04 17:03:47  yan
 * improved add of constraint
 *
 * Revision 1.13  2003/02/02 21:14:55  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.12  2002/12/19 00:08:17  yan
 * optimized add
 *
 * Revision 1.11  2002/11/18 11:51:27  yan
 * added test for DefaultConstraintScheduler
 *
 * Revision 1.10  2002/10/25 08:59:27  yan
 * cleaned category
 *
 * Revision 1.9  2002/10/09 13:52:38  yan
 * various cleaning
 *
 * Revision 1.8  2002/10/09 11:05:46  yan
 * using schedulers
 *
 * Revision 1.7  2002/10/08 17:20:25  yan
 * added complexity property
 *
 * Revision 1.6  2002/06/16 16:16:21  mphilip
 * Modified jdoc.
 *
 * Revision 1.5  2002/06/16 13:47:16  yan
 * added javadoc
 *
 * Revision 1.4  2002/05/03 14:26:48  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:24:38  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
