package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.BaseStorableReference;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.choicepoint.StorableValue;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.Variable;
import com.koalog.util.graph.FastGraph;
import com.koalog.util.graph.Graph;

/**
 * This relation enforces that a permutation is a cycle.
 *
 * <P>Graph explanation:
 * Consider the domain of the variable <CODE>i</CODE>, 
 * say <CODE>D_i={i_1, ... i_k}</CODE>. 
 * The implicit edges 
 *<CODE>i->i_1</CODE>, ..., <CODE>i->i_k</CODE> 
 * define an dynamic graph of variables.
 *
 * During the domains reduction, 
 * the graph becomes a sub-graph of the initial graph.
 * When all variables are instantiated, 
 * we obtain, in general, a set of disconnected paths.
 * 
 * The <CODE>Cycle</CODE> problem will enforce that, 
 * when all variables are instantiated, 
 * the corresponding edges form a cycle with no sub-cycle (Hamiltonian path).
 *
 * @since 1.0
 * @author Yan Georget
 */
public class Cycle extends BaseProblem {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param variables an array of integer variable with sparse domains
     */
    public Cycle(IntegerVariable[] variables) {
        super("cycle");
        add(new NoSubCycle(variables));
        // add(new ConnectivityCheck(variables)); 
        setVariables(variables);
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * This constraint enforces that there is no sub cycle in the the graph.
     */
    static class NoSubCycle extends BaseConstraint { 
        BaseStorableReference ref;

        /**
         * Sole constructor.
         * @param vars an array of integer variables with sparse domains
         */
        public NoSubCycle(IntegerVariable[] variables) {
            super(variables);
            idempotent = true;
            complexity = variables.length;
            name = "nosubcycle";
            Paths val = new Paths(variables.length);
            for (int i=0; i<variables.length; i++) {
                val.start[i] = val.end[i] = i;
                // val.length[i] = 0;
            }
            ref = new BaseStorableReference(val);   
        }
        
        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addGroundConstraint(this);
            }
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void init(ChoicePointStack cp,
                         ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            for (int i=0; i<variables.length; i++) {
                ((IntegerVariable) variables[i])
                    .adjustDifferent(cp, cs, this, i);
            }
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void filter(ChoicePointStack cp,
                           ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            final Paths val = (Paths) ref.getStorableValue();
            for (int i=0; i<variables.length; i++) {
                final Variable var = variables[i];
                final IntegerDomain dom = (IntegerDomain) var.getDomain(); 
                if (dom.isSingleton() && val.end[i] == i) {
                    // the variable was not instantiated before
                    cp.memorize(ref);
                    final int j = dom.getMin();
                    val.end[i] = val.end[j];
                    val.start[j] = val.start[i];
                    final int length = val.length[i] + 1 + val.length[j];
                    val.length[i] = val.length[j] = length;
                    if (val.start[i] != i) {
                        val.end[val.start[i]] = val.end[j];
                        val.length[val.start[i]] = length;
                    }
                    if (val.end[j] != j) {
                        val.start[val.end[j]] = val.start[i];
                        val.length[val.end[j]] = length;
                    }
                    if (length < variables.length-1) {
                        ((IntegerVariable) variables[val.end[i]])
                            .adjustDifferent(cp, cs, this, val.start[i]);
                    }
                }
            }
        }

        /**
         * A class for storing paths (as a storable value).
         */
        static class Paths implements StorableValue {
            int n;
            int[] start;
            int[] end;
            int[] length;
            
            public Paths(int n) {
                this.n = n;
                start = new int[n];
                end = new int[n];
                length = new int[n];
            }
            
            public StorableValue copy() {
                Paths c = new Paths(n);
                // faster than 3 calls to System.arraycopy! 
                for (int i=n; --i>=0;) {
                    c.start[i] = start[i];
                    c.end[i] = end[i];
                    c.length[i] = length[i];
                }
                return c;
            }
        }
    }

    /**
     * This constraint enforces that the graph is strongly connected.
     */
    static class ConnectivityCheck extends BaseConstraint {
        Graph valueGraph;

        /**
         * Sole constructor.
         * @param vars an array of integer variables with sparse domains
         */
        public ConnectivityCheck(IntegerVariable[] vars) {
            super(vars);
            idempotent = true;
            complexity = variables.length*variables.length;
            name = "connectivitycheck";
            valueGraph = new FastGraph(variables.length);
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=0; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addDomConstraint(this);
            }
        }

        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            valueGraph.clear();
            // TODO: optimize graph update
            for (int j=0; j<variables.length; j++) {
                for (Iterator l = 
                         ((SparseDomain)variables[j].getDomain()).iterator(); 
                     l.hasNext();) {
                    valueGraph.addEdge(j, (Integer) l.next());
                }
            }
            valueGraph.computeTremaux();
            valueGraph.computeSCC();
            if (valueGraph.getSCCNb() > 1) {                
                fail();
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.40  2005/07/21 07:22:14  yan
 * using new Graph API
 *
 * Revision 1.39  2005/07/18 15:32:55  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.38  2004/11/25 14:13:04  yan
 * using new Graph API
 *
 * Revision 1.37  2004/11/25 09:38:00  yan
 * using new Graph API
 *
 * Revision 1.36  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.35  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.34  2004/09/17 10:05:24  yan
 * changed internalAdd into add
 *
 * Revision 1.33  2004/06/27 14:08:22  yan
 * small fixes
 *
 * Revision 1.32  2004/06/25 15:00:37  yan
 * using internalAdd
 *
 * Revision 1.31  2004/06/16 13:17:36  yan
 * added jdoc
 *
 * Revision 1.30  2004/06/16 13:14:13  yan
 * fixed jdoc
 *
 * Revision 1.29  2004/06/03 16:50:21  yan
 * added @since tag
 *
 * Revision 1.28  2004/05/05 11:46:48  yan
 * fixed style
 *
 * Revision 1.27  2004/04/09 17:16:57  yan
 * using Permutation
 *
 * Revision 1.26  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.25  2004/04/01 19:17:02  yan
 * problem is not a constraint anymore
 *
 * Revision 1.24  2004/03/14 16:42:59  yan
 * various simplifications
 *
 * Revision 1.23  2003/10/05 16:37:46  yan
 * *** empty log message ***
 *
 * Revision 1.22  2003/10/02 23:49:00  yan
 * introduced complexity computation
 *
 * Revision 1.21  2003/08/15 10:08:31  yan
 * fixed javadoc
 *
 * Revision 1.20  2003/06/17 13:47:11  yan
 * fixed jdoc
 *
 * Revision 1.19  2003/06/17 13:36:20  yan
 * added javadoc about algorithmic infos
 *
 * Revision 1.18  2003/04/27 17:46:06  yan
 * fixed style
 *
 * Revision 1.17  2003/04/21 10:02:55  yan
 * increased coverage
 *
 * Revision 1.16  2003/04/01 13:58:01  yan
 * fixed complexity
 *
 * Revision 1.15  2003/03/27 11:01:48  yan
 * added events related method (updateConstraints)
 *
 * Revision 1.14  2003/03/09 20:05:10  yan
 * name fix
 *
 * Revision 1.13  2003/03/09 18:46:31  yan
 * opimized set usage
 *
 * Revision 1.12  2003/03/07 10:58:17  yan
 * fixed complexity
 *
 * Revision 1.11  2003/02/11 10:30:42  yan
 * fixed style
 *
 * Revision 1.10  2003/02/05 09:35:33  yan
 * using adjustMinMax
 *
 * Revision 1.9  2003/02/02 21:14:55  yan
 * modifiedVariables is now an instance variable
 *
 * Revision 1.8  2002/11/25 12:40:29  yan
 * added names for inner constraints
 *
 * Revision 1.7  2002/11/18 10:41:13  yan
 * Cycle is now a problem
 *
 * Revision 1.6  2002/10/25 09:03:14  yan
 * cleaned category
 *
 * Revision 1.5  2002/06/16 17:11:50  mphilip
 * Modified jdoc.
 *
 * Revision 1.4  2002/06/16 13:47:17  yan
 * added javadoc
 *
 * Revision 1.3  2002/05/02 16:21:22  yan
 * moved
 *
 * Revision 1.2  2002/04/19 09:53:43  mphilip
 * Fixed EOL and added CVS log tag.
 *
 */
