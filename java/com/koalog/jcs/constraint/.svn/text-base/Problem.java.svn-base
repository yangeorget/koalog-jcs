package com.koalog.jcs.constraint;

import java.util.Collection;

/**
 * This interface describes a problem.
 * 
 * @author Yan Georget
 */
public interface Problem extends AbstractRelation {
    /**
     * Creates the constraints from the relations.
     * 
     * <P>Note: automatically called by <CODE>BacktrackSolver</CODE>.
     * @see com.koalog.jcs.solver.BacktrackSolver
     * @since 2.3
     */
    void flattenConstraints();

    /**
     * Creates the constraints from a relation.
     * @param r an abstract relation
     * @since 3.0
     */
    void flattenConstraints(AbstractRelation r);

    /**
     * Updates the dependencies of the constraints of the problem.
     * 
     * <P>Note: automatically called by <CODE>BacktrackSolver</CODE>.
     * @see com.koalog.jcs.solver.BacktrackSolver
     * @since 2.3
     */
    void updateDependencies();

    /**
     * Returns the constraints of the problem.
     * @return a collection
     */
    Collection getConstraints();

    /**
     * Returns the constraint of the problem of a given unid.
     * @param unid a unid
     * @return a constraint
     * @since 2.6
     */
    Constraint getConstraint(int unid);

    /**
     * Returns the relations of the problem.
     * @return a collection
     * @since 2.3
     */
    Collection getRelations();

    /**
     * Adds a relation to the problem.
     *
     * <P>Should be used in conjunction with method 
     * <CODE>setVariables</CODE>.
     *
     * @since 2.3
     * @param relation a constraint
     */
    void add(AbstractRelation relation);

    /**
     * Adds a collection of relations to the problem.
     *
     * <P>Should be used in conjunction with method 
     * <CODE>setVariables</CODE>.
     *
     * @param relations a collection
     */
    void add(Collection relations);
}
/*
 * $Log$
 * Revision 1.25  2005/07/22 14:54:30  yan
 * added method for getting a constraint from its unid
 *
 * Revision 1.24  2004/11/05 15:32:54  yan
 * added a method for adding a collection of relations
 *
 * Revision 1.23  2004/10/08 10:18:18  yan
 * fixed jdoc
 *
 * Revision 1.22  2004/10/08 10:14:48  yan
 * added getRelations
 *
 * Revision 1.21  2004/10/01 17:41:40  yan
 * various small refactoring
 *
 * Revision 1.20  2004/09/30 15:42:52  yan
 * using AbstractRelation
 *
 * Revision 1.19  2004/09/17 12:48:38  yan
 * remove deepAdd
 *
 * Revision 1.18  2004/09/17 10:04:59  yan
 * changed internalAdd into add
 *
 * Revision 1.17  2004/09/17 09:33:02  yan
 * using deepAdd instead of add
 *
 * Revision 1.16  2004/06/25 14:59:45  yan
 * added new methods
 *
 * Revision 1.15  2004/05/18 08:59:24  yan
 * fixed javadoc
 *
 * Revision 1.14  2004/05/05 11:44:47  yan
 * fixed style
 *
 * Revision 1.13  2004/04/01 19:15:31  yan
 * problem is not a constraint anymore
 *
 * Revision 1.12  2003/03/27 10:53:12  yan
 * added events related methods
 *
 * Revision 1.11  2003/03/09 20:07:14  yan
 * flat properties
 *
 * Revision 1.10  2003/02/04 19:21:36  yan
 * reusing constraint scheduler
 *
 * Revision 1.9  2002/10/09 13:52:38  yan
 * various cleaning
 *
 * Revision 1.8  2002/10/09 11:05:46  yan
 * using schedulers
 *
 * Revision 1.7  2002/06/16 19:02:55  mphilip
 * Modified jdoc.
 *
 * Revision 1.6  2002/06/16 13:47:16  yan
 * added javadoc
 *
 * Revision 1.5  2002/05/06 10:14:38  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/05/03 14:26:49  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:24:38  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
