package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import com.koalog.util.graph.FlowGraph;
import com.koalog.util.graph.Graph;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;
import com.koalog.jcs.variable.Variable;

/**
 * A Global Cardinality Constraint (GCC).
 *
 * <P>Exemple:
 * <PRE>
 * IntegerVariable[] vars = new IntegerVariable[] {x, y, z, t};
 * add(new GCC(vars, 
 *             new int[] {1, 2, 3},
 *             new int[] {0, 1, 2},
 *             new int[] {2, 3, 3}));
 * </PRE>
 * enforces that:
 * <UL>
 * <LI>the number of variables equal to 1 is between 0 and 2, 
 * <LI>the number of variables equal to 2 is between 1 and 3, 
 * <LI>the number of variables equal to 3 is between 2 and 3. 
 * </UL>
 * </P>
 *
 * <P>Algorithmic informations 
 * (see 
 * {@link com.koalog.jcs.constraint.Constraint Constraint}
 * for more details on complexity and idempotence):
 * <TABLE border="1">
 * <TR>
 *    <TD><B>Idempotent</B></TD>
 *    <TD align="right">true</TD>
 * </TR>
 * <TR>
 *    <TD><B>Complexity</B></TD>
 *    <TD align="right">variables.length^2*max(dom)</TD>
 * </TR>
 * </TABLE>
 *
 * <P>This constraint is based on J.C. Regin's algorithm.</P>
 *
 * @since 1.4
 * @author Yan Georget
 */
public class GCC_SPARSE extends BaseConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static final Integer ZERO = new Integer(0);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private Set valuesSet;
    private int[] values;
    private int[] low;
    private int[] cap;
    /** The value network. */
    FlowGraph vn;
    /** The number of nodes on the value network. */
    private int n;
    private int n1;
    /** A map that maps values to value network's indices. */
    private Map indices;
    private int date;
   
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param variables an array of integer variables with sparse domains
     * @param values an array of values
     * @param low an array of lower bounds, 
     * each of them is a positive integer
     * @param cap an array of capacities, 
     * each of them is an integer smaller than the number of variables
     */
    public GCC_SPARSE(IntegerVariable[] variables, 
                      int[] values, 
                      int[] low, 
                      int[] cap) {
        super(variables);
        GCC.checkArguments(variables, values.length, low, cap);
        this.name = "gcc";
        this.values = values;
        this.low = low;
        this.cap = cap;
        complexity = IntegerVariable.getMaxDomainSize(variables)
            * variables.length
            * variables.length;
        idempotent = true;
        n = variables.length + values.length + 2;
        n1 = n-1;
        indices = new HashMap(values.length);
        createValueNetwork();
        date = -1;
        valuesSet = new HashSet(values.length);
        for (int i=0; i<values.length; i++) {
            valuesSet.add(new Integer(values[i]));
        }

    }

    /**
     * Auxilliary constructor.
     * @param variables an array of integer variables with sparse domains
     * @param values an array of values
     * @param low a common lower bound, 
     * a positive integer
     * @param cap a common capacity, 
     * an integer smaller than the number of variables
     */
    public GCC_SPARSE(IntegerVariable[] variables, 
                      int[] values, 
                      int low,
                      int cap) {
        this(variables, 
             values, 
             GCC.boundsArray(low, values.length), 
             GCC.boundsArray(cap, values.length));
    }

    /**
     * Auxilliary constructor.
     * @param variables an array of integer variables with sparse domains
     * @param values an array of values
     * @param occ an array of occurences
     */
    public GCC_SPARSE(IntegerVariable[] variables, 
                      int[] values, 
                      int[] occ) {
        this(variables, values, occ, occ);
    }

    /**
     * Auxilliary constructor.
     * @param variables an array of integer variables with sparse domains
     * @param values an array of values
     * @param occ a common occurence
     */
    public GCC_SPARSE(IntegerVariable[] variables, 
                      int[] values, 
                      int occ) {
        this(variables, values, occ, occ);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Creates the value network.
     * A call to updateValueNetwork is needed to complete the value network.
     */
    void createValueNetwork() {
        vn = new FlowGraph(n);
        // node t
        vn.addEdge(0, n1, 0, Integer.MAX_VALUE);
        // variable nodes
        for (int i=0; i<variables.length; i++) {
            vn.addEdge(i+1, 0, 0, 1);
        }
        // values nodes
        for (int i=0; i<values.length; i++) {
            indices.put(new Integer(values[i]), 
                        new Integer(i+variables.length+1));
        }
        // node s
        for (int i=0; i<values.length; i++) {
            vn.addEdge(n1, i+variables.length+1, low[i], cap[i]);
        }
    }

    /** 
     * Updates the value network w.r.t. edges deletion (values removal).
     */
    protected void updateValueNetwork() {
        for (int i=variables.length+1; i<n1; i++) {
            vn.clearEdges(i);
        }
        // values nodes
        for (int j=1; j<=variables.length; j++) {
            for (Iterator l = 
                     ((SparseDomain) variables[j-1].getDomain()).iterator(); 
                 l.hasNext();) {
                vn.addEdge(((Integer)indices.get(l.next())).intValue(),j,0,1);
            }
        }
    }

    /**
     * Updates the flow.
     */
    protected void updateFlow() {
        // use the following inside a CP
        int[][] nf = new int[n][n];
        for (int i=1; i<=variables.length; i++) {
            final Variable var = variables[i-1];
            for (Iterator j = ((SparseDomain) var.getDomain()).iterator(); 
                 j.hasNext();) {
                final int idxk = ((Integer)indices.get(j.next())).intValue();
                final int f = vn.getFlow(idxk, i);
                nf[idxk][i] = f;
                nf[i][0] += f;
                nf[n1][idxk] += f;
            }
        }
        for (int i=1+variables.length; i<n1; i++) {
            // a bug in Regin?
            nf[0][n1] += nf[n1][i];
        }
        vn.setFlow(nf);
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<variables.length; i++) {
            ((IntegerVariable) variables[i]).addDomConstraint(this);
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp, 
                     ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        for (int j=0; j<variables.length; j++) {
            for (Iterator k = 
                     ((SparseDomain) variables[j].getDomain()).iterator(); 
                 k.hasNext();) {
                final Integer v = (Integer) k.next(); 
                if (!valuesSet.contains(v)) {
                    ((IntegerVariable) variables[j])
                        .adjustDifferent(cp, cs, this, v, k);
                }
            }
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp, 
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        updateValueNetwork(); // because of values removal
        if (date == cp.getDate()) { 
            updateFlow();
        } else {
            vn.initFlow();
            date = cp.getDate();
        }
        if (!vn.feasibleFlow()) {
            // no feasible flow
            fail();
        }
        vn.maximalFlow();
        if (vn.getFlowValue() != variables.length) {
            // flow too low
            fail();
        }
        // get the residual graph
        Graph r = vn.getResidualGraph();
        // remove (s,t) in the residual graph
        r.removeEdge(n1, ZERO);
        r.computeTremaux();
        r.computeSCC(); 
        for (int i=1; i<=variables.length; i++) {
            final IntegerVariable var = (IntegerVariable) variables[i-1];
            for (Iterator z = ((SparseDomain) var.getDomain()).iterator();
                 z.hasNext();) {
                final Integer val = (Integer) z.next();
                final Integer index = (Integer) indices.get(val);
                if (index != null) {
                    final int idx = index.intValue();
                    if (vn.getFlow(idx, i)==0 && !r.sameSCC(idx, i)) {
                        var.adjustDifferent(cp, cs, this, val, z);
                    }
                } 
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.6  2005/07/21 07:22:14  yan
 * using new Graph API
 *
 * Revision 1.5  2005/07/20 07:04:35  yan
 * fixed checks
 *
 * Revision 1.4  2005/07/19 20:58:48  yan
 * added checks
 *
 * Revision 1.3  2005/07/19 20:23:46  yan
 * added check
 *
 * Revision 1.2  2005/07/18 15:30:17  yan
 * implememented 0021253: exceptions hierarchy
 *
 * Revision 1.1  2005/03/30 10:20:57  yan
 * old GCC
 *
 * Revision 1.26  2004/11/25 14:13:04  yan
 * using new Graph API
 *
 * Revision 1.25  2004/11/25 09:38:00  yan
 * using new Graph API
 *
 * Revision 1.24  2004/11/10 10:19:27  yan
 * various optims
 *
 * Revision 1.23  2004/11/09 17:07:15  yan
 * throwing static exceptions
 *
 * Revision 1.22  2004/09/17 12:53:38  yan
 * renamed updateConstraints
 *
 * Revision 1.21  2004/07/01 08:59:56  yan
 * using new adjustDifferent method
 *
 * Revision 1.20  2004/06/29 19:10:58  yan
 * using new adjustDifferent method
 *
 * Revision 1.19  2004/06/27 16:02:33  yan
 * small optims
 *
 * Revision 1.18  2004/06/27 15:18:15  yan
 * small optims
 *
 * Revision 1.17  2004/06/24 12:02:47  yan
 * initializing maps and sets with capacities
 *
 * Revision 1.16  2004/06/15 19:35:42  yan
 * added doc
 *
 * Revision 1.15  2004/06/15 19:20:38  yan
 * added back init method
 *
 * Revision 1.14  2004/06/03 16:50:21  yan
 * added @since tag
 *
 * Revision 1.13  2004/04/09 14:42:50  yan
 * removal of events
 *
 * Revision 1.12  2003/12/28 15:23:29  yan
 * fixed javadoc
 *
 * Revision 1.11  2003/10/12 15:47:54  yan
 * cleaned code
 *
 * Revision 1.10  2003/10/05 17:49:55  yan
 * optimized update of value network
 *
 * Revision 1.9  2003/10/05 16:38:04  yan
 * various optimizations
 *
 * Revision 1.8  2003/10/03 13:33:03  yan
 * made some methods protected
 *
 * Revision 1.7  2003/10/03 11:25:36  yan
 * various small optims
 *
 * Revision 1.6  2003/10/03 00:01:58  yan
 * fixed javadoc
 *
 * Revision 1.5  2003/10/02 23:50:09  yan
 * introduced complexity computation
 *
 * Revision 1.4  2003/10/01 18:35:23  yan
 * fixed bug
 *
 * Revision 1.3  2003/08/15 10:08:31  yan
 * fixed javadoc
 *
 * Revision 1.2  2003/08/08 11:06:22  yan
 * various optims
 *
 * Revision 1.1  2003/08/07 20:06:45  yan
 * initial revision
 *
 */
