package com.koalog.jcs.examples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;

import com.koalog.jcs.ConstraintInconsistencyException;
import com.koalog.jcs.choicepoint.ChoicePointStack;
import com.koalog.jcs.constraint.BaseConstraint;
import com.koalog.jcs.constraint.BaseProblem;
import com.koalog.jcs.constraint.ConstraintScheduler;
import com.koalog.jcs.constraint.arithmetic.Add;
import com.koalog.jcs.constraint.arithmetic.AllDifferent_SPARSE;
import com.koalog.jcs.constraint.arithmetic.Count;
import com.koalog.jcs.constraint.arithmetic.Element;
import com.koalog.jcs.constraint.arithmetic.Exactly;
import com.koalog.jcs.constraint.arithmetic.Max;
import com.koalog.jcs.constraint.arithmetic.Shift;
import com.koalog.jcs.constraint.arithmetic.Sum;
import com.koalog.jcs.constraint.bool.And;
import com.koalog.jcs.constraint.bool.And0;
import com.koalog.jcs.constraint.bool.IsSmaller;
import com.koalog.jcs.constraint.bool.Not;
import com.koalog.jcs.constraint.bool.Or;
import com.koalog.jcs.domain.SparseDomain;
import com.koalog.jcs.variable.BooleanVariable;
import com.koalog.jcs.variable.IntegerVariable;

/**
 * @author Yan Georget
 */
public class CMC05Problem extends BaseProblem {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    int[][] s;
    int n; // nb of lines/orders
    int m; // nb of columns/products
    int[] lbWhenOrderFirstOrLast;
    IntegerVariable max; // to be minimized
    IntegerVariable[] p; // products to be sequenced
    //IntegerVariable[] pos; // positions of the products
    BooleanVariable[][] c;
    BooleanVariable[][] io;
    BooleanVariable[][] o; // open orders (o = c xor io)
    IntegerVariable[] vo; // number of open orders
    IntegerVariable[] ho; // size of open order
    IntegerVariable[] so; // start of open order
    IntegerVariable[] eo; // end of open order


    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public CMC05Problem(int[][] s) {
        super();
        // let us sort the columns 
        // by their decreasing vcosts 
        sortByVCost(s);
        // let us remove the empty columns
        s = removeEmptyColumns(s);
        // let us remove the identical columns
        s = removeIdenticalColumns(s);
        // let us remove the empty lines
        s = removeEmptyLines(s);
        dump(s);
        this.s = s;
        n = s.length;
        m = s[0].length;
        lbWhenOrderFirstOrLast = getLBWhenOrderFirstOrLast(s);
        p = new IntegerVariable[m];
        // for breaking some symetries
        p[0] = new IntegerVariable("p"+0, new SparseDomain(0, (m+1)/2-1));
        for (int j=1; j<m; j++) {
            p[j] = new IntegerVariable("p"+j, new SparseDomain(0, m-1));
        }
        add(new AllDifferent_SPARSE(p));
        /* 
        pos = new IntegerVariable[m];
        for (int j=0; j<m; j++) {
            pos[j] = new IntegerVariable("pos"+j, new SparseDomain(0, m-1));
        }
        add(new AllDifferent_SPARSE(pos));
        add(new Inverse(pos, p)); // use permutation
        */
        /* break more symetries with identical columns
        int j1=0, j2=1;
        while (j2 < m) {
            while (identicalColumns(s, j1, j2)) {
                j2++;
            }
            for (int j=j1; j<j2-1; j++) {
                add(new Shift(p[j+1], p[j], 1));
            }
            j1 = j2;
            j2 = j1+1;
        } 
        */
        c = new BooleanVariable[n][m];
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                c[i][j] = new BooleanVariable("c"+i+"_"+j);
                add(new Element(c[i][j], p[j], s[i]));
            }
            add(new Exactly(getHCost(s, i), c[i], 1));
        }
        o = new BooleanVariable[n][m];
        BooleanVariable[][] l = new BooleanVariable[n][m];
        BooleanVariable[][] r = new BooleanVariable[n][m];
        for (int i=0; i<n; i++) {
            for (int k=0; k<m; k++) {
                o[i][k] = new BooleanVariable("o"+i+"_"+k);
            }
        }
        for (int i=0; i<n; i++) {
            l[i][0] = c[i][0];
            for (int k=1; k<m; k++) {
                l[i][k] = new BooleanVariable("l"+i+"_"+k);
                add(new Or(l[i][k], l[i][k-1], c[i][k]));
            }
        }
        for (int i=0; i<n; i++) {
            r[i][m-1] = c[i][m-1];
            for (int k=m-2; k>=0; k--) {
                r[i][k] = new BooleanVariable("r"+i+"_"+k);
                add(new Or(r[i][k], r[i][k+1], c[i][k]));
            }
        }
        io = new BooleanVariable[n][m];
        for (int i=0; i<n; i++) {
            for (int k=0; k<m; k++) {
                io[i][k] = new BooleanVariable("io"+i+"_"+k);
                // add(new Leq(io[i][k], o[i][k]));
                add(new Or(o[i][k], c[i][k], io[i][k]));
                add(new And0(c[i][k], io[i][k]));
            }
        }
        for (int i=0; i<n; i++) {
            for (int k=0; k<m; k++) {
                add(new And(o[i][k], l[i][k], r[i][k]));
            }
        }
        ho = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            ho[i] = new IntegerVariable("ho"+i, getHCost(s, i), m);
            IntegerVariable tmp = new IntegerVariable(0, m-getHCost(s, i));
            add(new Count(tmp, io[i], 1));
            add(new Shift(ho[i], tmp, getHCost(s, i)));
            if (getHCost(s, i) > 1) {
                add(new Count_AUX(ho[i], c[i], getHCost(s, i), m));
            }
        }
        so = new IntegerVariable[n];
        eo = new IntegerVariable[n];
        for (int i=0; i<n; i++) {
            so[i] = new IntegerVariable("so"+i, 
                                        0,
                                        m-1);
            eo[i] = new IntegerVariable("eo"+i, 
                                        1,
                                        m);
            add(new Add(eo[i], so[i], ho[i]));
            for (int j=0; j<m; j++) {
                add(new IsSmaller(l[i][j], so[i], j+1));
                BooleanVariable tmp = new BooleanVariable();
                add(new Not(tmp, r[i][j]));
                add(new IsSmaller(tmp, eo[i], j+1));
            }
        }
        vo = new IntegerVariable[m];
        for (int k=0; k<m; k++) {
            vo[k] = new IntegerVariable("vo"+k, 
                                        getVCost(s, m-1),
                                        n);
            IntegerVariable[] tmp = new IntegerVariable[n];
            for (int i=0; i<n; i++) {
                tmp[i] = o[i][k];
            }
            add(new Count(vo[k], tmp, 1));
        }
        IntegerVariable sum = new IntegerVariable("sum", 
                                                  getTCost(s), 
                                                  n*m);
        add(new Sum(sum, ho));
        add(new Sum(sum, vo));
        max = new IntegerVariable("max", 
                                  getLB(s,lbWhenOrderFirstOrLast),
                                  n);
        add(new Max(max, vo));
        add(new FirstOrder(max, eo, s)); // to end
        add(new LastOrder(max, so, s)); // to start
        setVariables(p);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    private class Count_AUX extends BaseConstraint {
        int m;
        BooleanVariable[] ci;
        IntegerVariable hoi;
        int hcosti;

        public Count_AUX(IntegerVariable hoi, 
                         BooleanVariable[] ci, 
                         int hcosti,
                         int m) {
            super();
            this.variables = new IntegerVariable[ci.length + 1];
            this.variables[0] = hoi;
            System.arraycopy(ci, 0, variables, 1, ci.length);
            this.ci = ci;
            this.hoi = hoi;
            this.m = m;
            this.hcosti = hcosti;
            idempotent = true;
            complexity = variables.length;
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            for (int i=1; i<variables.length; i++) {
                ((BooleanVariable) variables[i]).addGroundConstraint(this);
            }
        }

        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            int nb = hcosti;
            for(int j=0; j<m; j++) {
                if (ci[j].isTrue()) {
                    min = Math.min(min, j);
                    max = Math.max(max, j);
                    if (--nb == 1) {
                        break;
                    }
                }
            }
            if (nb == 1) {
                hoi.adjustMax(cp, cs, this, Math.max(max+1, m-min));
                entailed(cp);
            }
        }
    }

    private class FirstOrder extends BaseConstraint {
        int[][] s;
        int n;
        int m;
        IntegerVariable[] eo;
        IntegerVariable max;

        public FirstOrder(IntegerVariable max, 
                          IntegerVariable[] eo, 
                          int[][] s) {
            super();
            this.s = s;
            n = s.length;
            m = s[0].length;
            this.variables = new IntegerVariable[eo.length + 1];
            this.variables[0] = max;
            System.arraycopy(eo, 0, variables, 1, eo.length);
            this.eo = eo;
            this.max = max;
            idempotent = true;
            complexity = variables.length*variables.length/2;
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            ((IntegerVariable) variables[0]).addMaxConstraint(this);
            for (int i=1; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
            }
        }

        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            boolean[] firsts = new boolean[n];
            for (int i=0; i<n; i++) {
                if (lbWhenOrderFirstOrLast[i] <= max.getMax()) {
                    int min = eo[i].getMin();
                    int max = eo[i].getMax();
                    boolean intersectsAll = true;
                    for (int k=0; k<i; k++) {
                        if (min > eo[k].getMax()) {
                            intersectsAll = false;
                            break;
                        }
                    }
                    if (intersectsAll) {
                        boolean beforeAll = true;
                        for (int k=0; k<i; k++) {
                            if (max >= eo[k].getMin()) {
                                beforeAll = false;
                                break;
                            }
                        }
                        firsts[i] = true;
                        if (beforeAll) {
                            for (int k=0; k<i; k++) {
                                firsts[k] = false;
                            }
                        } 
                    }
                }
            }
            int nb = 0;
            int iFirst = -1;
            int hmin = Integer.MAX_VALUE;
            int eomin = Integer.MAX_VALUE;
            for (int i=0; i<n; i++) {
                if (firsts[i]) {
                    nb++;
                    iFirst = i;
                    hmin = Math.min(hmin, lbWhenOrderFirstOrLast[i]);
                    eomin = Math.min(eomin, eo[i].getMin());
                }
            }
            max.adjustMin(cp, cs, this, hmin);
            for (int i=0; i<n; i++) {
                if (!firsts[i]) {
                    if (nb == 1) {
                        eo[iFirst].adjustMax(cp,cs, this, eo[i].getMax()-1);
                    }
                    eo[i].adjustMin(cp,cs, this, eomin+1);
                }
            }
        }
    }

    private class LastOrder extends BaseConstraint {
        int[][] s;
        int n;
        int m;
        IntegerVariable[] so;
        IntegerVariable max;

        public LastOrder(IntegerVariable max, 
                         IntegerVariable[] so, 
                         int[][] s) {
            super();
            this.s = s;
            n = s.length;
            m = s[0].length;
            this.variables = new IntegerVariable[so.length + 1];
            this.variables[0] = max;
            System.arraycopy(eo, 0, variables, 1, so.length);
            this.so = so;
            this.max = max;
            idempotent = true;
            complexity = variables.length;
        }

        /** @see com.koalog.jcs.constraint.Constraint */
        public void updateDependencies() {
            ((IntegerVariable) variables[0]).addMaxConstraint(this);
            for (int i=1; i<variables.length; i++) {
                ((IntegerVariable) variables[i]).addMinMaxConstraint(this);
            }
        }

        public void filter(ChoicePointStack cp, ConstraintScheduler cs) 
            throws ConstraintInconsistencyException {
            boolean[] lasts = new boolean[n];
            for (int i=0; i<n; i++) {
                if (lbWhenOrderFirstOrLast[i] <= max.getMax()) {
                    int min = so[i].getMin();
                    int max = so[i].getMax();
                    boolean intersectsAll = true;
                    for (int k=0; k<i; k++) {
                        if (max < so[k].getMin()) {
                            intersectsAll = false;
                            break;
                        }
                    }
                    if (intersectsAll) {
                        boolean afterAll = true;
                        for (int k=0; k<i; k++) {
                            if (min <= so[k].getMax()) {
                                afterAll = false;
                                break;
                            }
                        }
                        lasts[i] = true;
                        if (afterAll) {
                            for (int k=0; k<i; k++) {
                                lasts[k] = false;
                            }
                        } 
                    }
                }
            }
            int nb = 0;
            int iLast = -1;
            int hmin = Integer.MAX_VALUE;
            int somax = Integer.MIN_VALUE;
            for (int i=0; i<n; i++) {
                if (lasts[i]) {
                    nb++;
                    iLast = i;
                    hmin = Math.min(hmin, lbWhenOrderFirstOrLast[i]);
                    somax = Math.max(somax, so[i].getMax());
                }
            }
            max.adjustMin(cp, cs, this, hmin);
            for (int i=0; i<n; i++) {
                if (!lasts[i]) {
                    if (nb ==1) {
                        so[iLast].adjustMin(cp,cs, this, so[i].getMin()+1);
                    }
                    so[i].adjustMax(cp,cs, this, somax-1);
                }
            }
        }
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    public void dump() {
        StringBuffer b1 = new StringBuffer();
        for (int j=0; j<m; j++) {
            b1.append(" ");
            if (p[j].isInstantiated()) {
                b1.append(p[j].getMin());
            } else {
                b1.append(p[j]);
            }
            b1.append(" ");
        }
        cat.info(b1);   
        for (int i=0; i<n; i++) {
            StringBuffer buf = new StringBuffer();
            for (int j=0; j<m; j++) {
                if (c[i][j].isTrue()) {
                    if (o[i][j].isTrue()) {
                        buf.append("|1|");
                    } else {
                        throw new RuntimeException();
                    }
                } else {
                    if (o[i][j].isTrue()) {
                        buf.append(" 1 ");
                    } else if (o[i][j].isFalse()) {
                        buf.append(" 0 ");
                    } else {
                        buf.append(" ? ");
                    }
                }
            }
            buf.append(" ");
            buf.append(" ");
            if (ho[i].isInstantiated()) {
                buf.append(ho[i].getMin());
            } else {
                buf.append(ho[i]);
            }
            cat.info(buf);
        }
        StringBuffer b2 = new StringBuffer();
        for (int j=0; j<m; j++) {
            b2.append(" ");
            if (vo[j].isInstantiated()) {
                b2.append(vo[j].getMin());
            } else {
                b2.append(vo[j]);
            }
            b2.append(" ");
        }
        cat.info(b2); 
    }


    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(CMC05Problem.class);

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    public static int getLB(int[][] a, int[] lbWhenOrderFirstOrLast) {
        int lb = getLB(a);
        int min = Integer.MAX_VALUE;
        for (int i=0; i<a.length; i++) {
            int tmp = lbWhenOrderFirstOrLast[i];
            if (tmp < min) {
                min = tmp;
            }
        }
        cat.info("lb=" + lb);
        cat.info("lbWhenOrderFirstOrLast=" +  min);
        return Math.max(lb, min);
    }

    public static int getLB(int[][] a) {
        return getVCost(a, 0);
    }

    public static int[] getLBWhenOrderFirstOrLast(int[][] a) {
        int[] lb = new int[a.length];
        for (int i=0; i<a.length; i++) {
            lb[i] = getLBWhenOrderFirstOrLast(a, i);
            cat.info("lbWhenOrderFirstOrLast_" +i+"="+lb[i]);
        }
        return lb;
    }

    private static int getLBWhenOrderFirstOrLast(int[][] a, int k) {
        int n = a.length;
        int m = a[0].length;
        boolean[] b = new boolean[n];
        b[k] = true;
        for (int j=0; j<m; j++) {
            if (a[k][j] == 1) {
                for (int i=0; i<n; i++) {
                    if (a[i][j] == 1) {
                        b[i] = true;
                    }
                }  
            }
        }
        int nb = 0;
        for (int i=0; i<a.length; i++) {
            if (b[i]) {
                nb++;
            }
        }
        return nb;
    }

    public static boolean identicalColumns(int[][] a, int j1, int j2) {
        for (int i=0; i<a.length; i++) {
            if (a[i][j1] != a[i][j2]) {
                return false;
            }
        }
        return true;
    }

    public static void dump(int[][] s) {
        for (int i=0; i<s.length; i++) {
            StringBuffer buf = new StringBuffer();
            for (int j=0; j<s[0].length; j++) {
                if (s[i][j] == 1) {
                    buf.append(" 1 ");
                } else {
                    buf.append(" 0 ");
                }
            }
            cat.info(buf);
        }
    }

    public static int[][] removeEmptyColumns(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        // let us count the nb of empty columns
        int nbEmpty = 0;
        while (getVCost(a, m-1-nbEmpty) <= 1) { // we also remove column with a single 1
            nbEmpty++;
        }
        cat.debug("nb of empty columns: " + nbEmpty);
        int[][] s = new int[n][m-nbEmpty];
        for (int i=0; i<n; i++) {
            for (int j=0; j<m-nbEmpty; j++) {
                s[i][j] = a[i][j];
            }
        }
        return s;
    }

    public static int[][] removeEmptyLines(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        // let us count the nb of empty lines
        int nbEmpty = 0;
        for (int i=0; i<n; i++) {
            if (getHCost(a, i) == 0) {
                nbEmpty++;
            }
        }
        cat.debug("nb of empty lines: " + nbEmpty);
        int[][] s = new int[n-nbEmpty][m];
        int k=0;
        for (int i=0; i<n; i++) {
            if (getHCost(a, i) > 0) {
                for (int j=0; j<m; j++) {
                    s[k][j] = a[i][j];
                }
                k++;
            }
        }
        return s;
    }

    public static int[][] removeIdenticalColumns(int[][] a) {
        int n = a.length;
        int m = a[0].length;
        List columns = new ArrayList();
        int j1=0, j2=1;
        while (j1 < m) {
            while (j2<m && identicalColumns(a, j1, j2)) {
                j2++;
            }
            int[] tmp = new int[n];
            for (int i=0; i<n; i++) {
                tmp[i] = a[i][j1];
            }
            columns.add(tmp);
            j1 = j2;
            j2 = j1+1;
        } 
        int[][] t = new int[n][columns.size()];
        int j=0;
        for (Iterator l = columns.iterator(); l.hasNext();) {
            int[] tmp = (int[]) l.next();
            for (int i=0; i<n; i++) {
                t[i][j] = tmp[i];
            }
            j++;
        }
        return t;
    }

    public static int getVCost(int[][] a, int j) {
        int sum = 0;
        for (int i=0; i<a.length; i++) {
            sum += a[i][j];
        }
        return sum;
    }

    public static int getHCost(int[][] a, int i) {
        int sum = 0;
        for (int j=0; j<a[0].length; j++) {
            sum += a[i][j];
        }
        return sum;
    }

    public static int getTCost(int[][] a) {
        int sum = 0;
        for (int i=0; i<a.length; i++) {
            sum += getHCost(a, i);
        }
        return sum;
    }

    public static void sortByVCost(int[][] a) {
        int m = a[0].length;
        for (int j1=0; j1<m-1; j1++) {
            for (int j2=j1+1; j2<m; j2++) {
                int vCost1 = getVCost(a, j1);
                int vCost2 = getVCost(a, j2);
                if (vCost2 > vCost1 
                    || (vCost1 == vCost2 && lgGreater(a, j2, j1))){
                    swap(a, j1, j2);
                }
            }
        }
    }

    private static boolean lgGreater(int[][] a, int j1, int j2) {
        for (int i=0; i<a.length; i++) {
            if (a[i][j1] > a[i][j2]) {
                return true;
            }
            if (a[i][j1] < a[i][j2]) {
                return false;
            }
        }
        return false;
    }

    private static void swap(int[][] a, int j1, int j2) {
        int n = a.length;
        int[] tmp = new int[n];
        for (int i=0; i<n; i++) {
            tmp[i] = a[i][j1];
        }
        for (int i=0; i<n; i++) {
            a[i][j1] = a[i][j2];
        }
        for (int i=0; i<n; i++) {
            a[i][j2] = tmp[i];
        }
    }

    /**
     * Runs the problem.
     * @param args the command line arguments
     * args[0] must contain a log4j properties file location
     */
    public static void main(String[] args) {
        PropertyConfigurator.configure(args[0]);
        CMC05Problem p = new CMC05Problem(CMC05Problem.BMS_10_10_35_1);
        new CMC05Minimizer(p).optimize();
    }

    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    // 8, 1248 ms, 278 bts
    public static final int[][] BMS_10_10_35_1 = {
        {1,0,1,0,0,1,0,1,0,0},
        {1,0,0,0,1,1,0,0,1,0},
        {0,0,1,0,0,0,0,0,1,0},
        {0,1,0,0,0,1,0,0,0,1},
        {0,0,0,1,0,1,0,1,0,0},
        {1,0,0,0,0,0,1,0,0,1},
        {0,1,0,0,0,1,0,1,1,0},
        {1,0,1,1,1,0,0,0,0,0},
        {1,1,1,1,0,0,1,1,0,1},
        {1,0,0,0,0,1,0,1,0,1}
    };

    // 7, 413 ms, 16 bts
    public static final int[][] BMS_10_10_35_2 = {
        {0,0,1,1,0,0,0,0,0,0}, 
        {1,0,0,0,0,0,0,0,0,0}, 
        {0,0,1,0,0,0,0,0,1,0}, 
        {1,1,1,1,0,1,0,1,1,0}, 
        {0,1,0,0,1,1,1,0,1,0}, 
        {1,1,0,0,0,0,0,0,0,1}, 
        {0,0,0,0,1,0,0,0,1,1}, 
        {0,0,0,1,1,0,0,0,1,0}, 
        {1,0,0,0,0,0,0,0,1,0}, 
        {1,1,1,0,1,0,0,1,0,1} 
    };

    // 8, 1062 ms, 30 bts
    public static final int[][] BMS_10_10_35_3 = {
        {0,1,0,1,1,1,0,0,0,0}, 
        {1,0,1,1,0,0,0,1,0,1}, 
        {1,0,0,1,1,0,0,0,0,1}, 
        {0,1,0,0,1,1,0,0,0,0}, 
        {0,1,0,1,0,0,0,0,0,0}, 
        {0,1,0,1,0,0,1,1,1,0}, 
        {1,0,0,1,0,1,0,1,1,0}, 
        {1,0,0,1,1,1,1,0,0,1}, 
        {1,0,1,0,0,1,1,0,0,0}, 
        {0,0,0,0,0,0,1,0,0,0}
    };

    // 7, 252 ms, 6 bts
    public static final int[][] BMS_10_10_35_4 = {
        {0,0,0,0,1,0,0,1,0,0}, 
        {0,0,0,1,0,0,0,1,1,0}, 
        {1,1,0,0,0,1,0,0,0,0}, 
        {1,0,0,0,0,0,0,0,1,0}, 
        {0,1,0,0,0,0,0,1,1,0}, 
        {0,0,0,0,0,0,0,0,0,0}, 
        {1,1,0,0,0,0,0,0,1,0}, 
        {0,0,1,1,0,0,0,0,1,1}, 
        {1,1,0,1,0,0,0,1,1,0}, 
        {0,0,1,0,1,1,1,0,1,0}
    };

    // 6, 154 ms, 0 bts
    public static final int[][] BMS_10_10_35_5 = {
        {0,1,0,0,0,0,0,0,1,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,0},
        {0,0,1,1,1,0,1,1,0,0},
        {0,1,0,1,1,0,1,0,0,0},
        {0,0,0,0,0,0,1,0,0,0},
        {0,0,0,0,0,1,1,1,1,1},
        {0,0,0,1,0,0,0,0,0,0},
        {1,0,1,0,0,0,1,1,0,1},
        {0,1,0,0,0,1,1,0,1,0}
    };

    // 9, 2 s, 137 bts
    public static final int[][] BMS_10_20_35_6 = {
        {1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
        {0,0,0,0,0,1,1,0,0,1,1,0,1,0,0,0,1,1,1,0},
        {0,1,0,0,0,0,1,0,1,1,0,1,0,1,0,0,0,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,0},
        {1,0,0,0,0,1,0,0,0,1,1,0,1,0,0,0,1,0,1,0},
        {1,1,1,1,1,1,0,0,1,0,1,1,0,0,0,0,0,1,0,1},
        {0,1,0,0,0,0,0,0,1,0,1,1,0,0,0,0,1,1,1,0},
        {0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0},
        {1,0,1,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,1,0},
        {0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,1,0,1,1} 
    };

    // 8, 3 s, 416 bts
    public static final int[][] BMS_10_20_35_7 = {
        {0,0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,0},
        {0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,0,1,1,0,0,0,1,0,0,1,0,0,0,1,0,0,1},
        {1,1,0,1,1,0,1,1,0,1,0,0,0,0,0,0,0,1,0,0},
        {1,0,1,1,0,0,0,0,1,1,1,0,0,0,1,0,0,1,0,1},
        {0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
        {1,0,0,0,0,0,1,1,0,0,1,0,0,0,0,0,1,0,0,0},
        {0,0,0,1,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,1,0,0,0},
        {0,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,1,1,0,1}
    };

    // 9, 7 s, 708 bts
    public static final int[][] BMS_10_20_35_8 = {
        {1,0,0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0}, 
        {1,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,1,0}, 
        {0,1,1,1,0,1,0,1,0,1,1,0,0,0,0,0,0,0,1,1}, 
        {0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1,1,1,0}, 
        {1,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,1,0,1}, 
        {0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,0,0,0,1,0}, 
        {0,0,0,0,0,0,1,0,0,1,1,0,0,0,1,0,1,0,0,1}, 
        {0,0,0,0,0,0,0,1,0,1,0,1,1,0,0,1,0,0,0,1}, 
        {0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,1,0,0,0,0}, 
        {1,1,1,0,0,0,0,0,1,0,0,1,1,1,0,1,0,1,0,1}, 
    };

    // 9, 15 s, 1586 bts
    public static final int[][] BMS_10_20_35_9 = {
        {0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,0,0,1}, 
        {1,0,0,0,0,0,1,0,1,1,0,1,1,0,0,1,0,1,0,0}, 
        {0,0,1,0,0,0,1,0,0,0,1,1,1,1,0,0,1,1,0,1}, 
        {0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1}, 
        {1,1,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0}, 
        {1,0,1,0,1,1,0,1,0,0,0,0,0,0,0,0,0,1,1,0}, 
        {0,0,0,1,0,1,1,0,0,1,0,1,0,0,0,1,1,1,0,0}, 
        {0,0,0,0,1,1,0,0,1,0,0,1,0,1,0,1,0,0,0,0}, 
        {0,0,1,0,1,0,0,1,1,0,0,1,0,1,1,0,0,0,1,0}, 
        {0,0,0,1,0,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0} 
    };

    // 7, 124 s, 17176 bts
    public static final int[][] BMS_10_20_35_10 = {
        {0,1,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0,1,0}, 
        {0,1,0,0,0,0,0,0,1,0,1,1,0,0,0,1,0,0,1,0}, 
        {0,0,1,1,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0}, 
        {0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1}, 
        {1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0}, 
        {0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,1,0}, 
        {0,0,1,0,0,0,1,0,1,0,0,1,0,0,1,1,0,0,0,0}, 
        {0,0,1,0,1,1,0,0,0,1,1,0,0,1,0,1,0,1,0,1}, 
        {0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,1,0,0,1}, 
        {1,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0}
    };

    public static final int[][] BMS_15_30_35_11 = {
        {1,0,1,0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,1,0,0,0,0,0}, 
        {0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,1,0}, 
        {1,0,0,1,1,0,0,1,0,0,1,0,1,0,0,0,1,1,0,0,1,0,0,1,0,0,0,1,1,1}, 
        {1,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,1,0,1,1,1,1,1,0,1,1}, 
        {0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,0,0,1,1,0,1,1,0,0,1,1,0,0,0}, 
        {0,0,0,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1}, 
        {0,1,0,1,0,0,0,0,1,1,1,0,1,1,0,1,0,0,0,0,0,0,0,0,1,0,1,1,0,1}, 
        {1,0,1,0,0,1,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,1,0}, 
        {0,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1}, 
        {0,0,1,1,1,1,0,0,1,1,0,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,1,0,0,1}, 
        {0,0,1,0,0,0,1,0,0,0,0,0,0,1,1,1,0,1,0,0,1,0,0,1,0,1,1,1,0,1}, 
        {1,1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,1,0,1,1}, 
        {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,1,1,0,1,0,0,0,0,0,0,0,0}, 
        {1,0,0,0,1,0,1,1,0,1,1,0,1,0,0,1,1,0,0,0,0,1,1,0,0,0,1,1,0,0}, 
        {1,0,1,1,0,0,0,1,1,0,0,1,0,1,1,0,0,0,1,0,1,1,0,0,0,0,0,1,0,0} 
    };
}
