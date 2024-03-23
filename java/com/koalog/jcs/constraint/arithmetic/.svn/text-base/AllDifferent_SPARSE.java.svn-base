package com.koalog.jcs.constraint.arithmetic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Category;
import com.koalog.util.graph.FastGraph;
import com.koalog.util.graph.Graph;
import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.domain.IntegerDomain;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * This constraint enforces that all of its variables are different.
 *
 * <P>Note: the integer variables must have sparse domains.</P>
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
 *    <TD align="right">max(dom)*variables.length^1.5</TD>
 * </TR>
 * </TABLE>
 *
 * <P>This constraint is based on J.C. Regin's algorithm.</P>
 *
 * @since 2.6
 * @author Yan Georget
 */
public class AllDifferent_SPARSE extends BaseConstraint {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(AllDifferent_SPARSE.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The value network. */
    Graph vn;
    /** The ordered graph obtained from the value network and its mapping. */ 
    Graph o;
    /** Maps indices to values. */
    int[] values;
    /** Maps values to indices. */
    private Map indices;
    /** The cp date. */
    private int cpDate;
    /** An internal date used to reset used and vital. */
    private int date;
    /** The matching. */
    Map matching;
    /** Vital edges. */
    int[][] vital;
    /** Used edges. */
    int[][] used;
    /** Free vertices. */
    LinkedList free;
    boolean[] visited;
    int nbVariables;
    int nbValues;
    int nbVertices;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Main constructor.
     * @param variables an array of integer variables with sparse domains
     */
    public AllDifferent_SPARSE(IntegerVariable[] variables) {
        super(variables);
        this.name = "alldifferent";
        nbVariables = variables.length;
        complexity = (int) (IntegerVariable.getMaxDomainSize(variables)
                            * nbVariables 
                            * Math.sqrt(nbVariables));
        idempotent = true;
        cpDate = -1;
        date = 1;
        Set valuesSet = new TreeSet();
        for (int i=nbVariables; --i>=0;) {
            valuesSet.addAll(((IntegerDomain) variables[i].getDomain())
                             .toSet());
        }
        nbValues = valuesSet.size();
        values = new int[nbValues];
        indices = new HashMap(nbValues);
        int idx = 0;
        for (Iterator l = valuesSet.iterator(); l.hasNext();) {
            Integer val = (Integer) l.next();
            indices.put(val, new Integer(idx+nbVariables));
            values[idx++] = val.intValue();
        }
        nbVertices = nbVariables + nbValues;
        vn = new FastGraph(nbVertices);
        o = new FastGraph(nbVertices);
        vital = new int[nbVariables][nbValues];
        used =  new int[nbVariables][nbValues];
        visited = new boolean[nbVertices];
        free = new LinkedList();
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.Constraint */
    public void updateDependencies() {
        for (int i=0; i<nbVariables; i++) {
            ((IntegerVariable) variables[i]).addDomConstraint(this);
        }
    }

    /** 
     * Inits the value network.
     */
    protected void initValueNetwork() {
        // values nodes
        for (int j=nbVariables; --j>=0;) {
            vn.clearEdges(j);
            for (Iterator l = ((SparseDomain) variables[j].getDomain())
                     .iterator(); 
                 l.hasNext();) {
                vn.addEdge(j, (Integer) indices.get(l.next()));
            }
        }
    }

   /** @see com.koalog.jcs.constraint.Constraint */
    public void init(ChoicePointStack cp, 
                     ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (nbValues < nbVariables) {
            fail();
        }
    }

    /** @see com.koalog.jcs.constraint.Constraint */
    public void filter(ChoicePointStack cp, 
                       ConstraintScheduler cs) 
        throws ConstraintInconsistencyException {
        if (cpDate == cp.getDate()) { 
            // the following optimization is not efficient
            boolean computeMatching = false;
            for (int iu=nbVariables; --iu>=0;) {
                final Integer u = new Integer(iu);
                final SparseDomain dom = 
                    (SparseDomain) variables[iu].getDomain();
                for (Iterator l = vn.getEdgesIterator(iu); l.hasNext();) {
                    final Integer v = (Integer) l.next();
                    final int iv = v.intValue() - nbVariables;
                    if (!dom.contains(values[iv])) {
                        // edge u-v has disappeared
                        if (u.equals(matching.get(v))) {
                            if (vital[iu][iv] == date) {
                                fail();
                            } 
                            matching.remove(v);
                            computeMatching = true;
                        }
                        l.remove();                    
                    }
                }
            }
            if (computeMatching) {
                matching = vn.maximumMatching(nbVariables, matching);
            }
        } else {
            cpDate = cp.getDate();
            initValueNetwork(); 
            matching = vn.maximumMatching(nbVariables);
        }
        if (matching.size() < nbVariables) {
            fail();
        }
        updateUsedEdges();
        for (int ia = nbVertices; --ia>=0;) {
            for (Iterator l = o.getEdgesIterator(ia); l.hasNext();) {
                final int ib = ((Integer) l.next()).intValue();
                final int iu;
                final int iv;
                if (ia < ib) {
                    iu = ia;
                    iv = ib;
                } else {
                    iu = ib;
                    iv = ia;
                }
                final int siv = iv - nbVariables;
                if (used[iu][siv] < date) {
                    if (((Integer) matching.get(new Integer(iv)))
                        .intValue() == iu) {
                        vital[iu][siv] = date;
                    } else {                
                        ((IntegerVariable) variables[iu])
                            .adjustDifferent(cp, cs, this, values[siv]);
                    }
                }
            }
        }
    }
    
    /** 
     * Updates the sets of used edges.
     */
    void updateUsedEdges() {
        // inits vital and used edges
        date++;
        // inits free vertices
        free.clear();
        for (int iv = nbVertices; --iv >= nbVariables;) {
            final Integer v = new Integer(iv);
            if (!matching.containsKey(v)) {
                free.add(v);
            }
        }
        // computes directed graph o
        o.clear();
        for (int iu=nbVariables; --iu>=0;) {
            final Integer u = new Integer(iu);
            for (Iterator l = vn.getEdgesIterator(iu); l.hasNext();) {
                final Integer v = (Integer) l.next();
                if (u.equals(matching.get(v))) {
                    o.addEdge(iu, v);
                } else {
                    o.addEdge(v.intValue(), u);
                }
            }
        }
        // BFS
        for (int i = nbVertices; --i>=0;) {
            visited[i] = false;
        }
        while (!free.isEmpty()) {
            final int ia = ((Integer) free.removeFirst()).intValue();
            visited[ia] = true;
            for (Iterator l = o.getEdgesIterator(ia); l.hasNext();) {
                final Integer b = (Integer) l.next();
                final int ib = b.intValue();
                if (ia < ib) {
                    used[ia][ib-nbVariables] = date;
                } else {
                    used[ib][ia-nbVariables] = date;
                }
                if (!visited[ib]) {
                    free.addLast(b); 
                }
            }
        }
        o.computeTremaux();
        o.computeSCC(); 
        for (int ia=nbVertices; --ia>=0;) {
            for (Iterator l = o.getEdgesIterator(ia); l.hasNext();) {
                final int ib = ((Integer) l.next()).intValue();
                if (o.sameSCC(ia, ib)) {
                    if (ia < ib) {
                        used[ia][ib-nbVariables] = date;
                    } else {
                        used[ib][ia-nbVariables] = date;
                    }
                }
            }
        }
    }
}
