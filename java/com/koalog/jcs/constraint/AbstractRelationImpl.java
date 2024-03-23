package com.koalog.jcs.constraint;

import org.apache.log4j.Category;
import com.koalog.jcs.BaseNamedObject;
import com.koalog.jcs.variable.Variable;

/** 
 * An common super class for both constraints and problems.
 *
 * @author Yan Georget
 * @since 2.3
 */
public class AbstractRelationImpl 
    extends BaseNamedObject 
    implements AbstractRelation { 
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
       Category.getInstance(AbstractRelationImpl.class);
    private static int counter;

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The variables of the relation. */
    protected Variable[] variables;
    /** The parent relation of the relation or null. */
    protected Problem parent;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     */
    public AbstractRelationImpl() {
        super();
        unid = ++counter;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.AbstractRelation */
    public final Variable[] getVariables() {
        return variables;
    }
 
    /** @see com.koalog.jcs.constraint.Problem */
    public final void setVariables(Variable[] variables) {
        this.variables = variables;
    }

    /** @see com.koalog.jcs.constraint.AbstractRelation */
    public final Problem getParent() {
        return parent;
    }

    /** @see com.koalog.jcs.constraint.AbstractRelation */
    public void setParent(Problem parent) {
        this.parent = parent;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.jcs.constraint.AbstractRelation */
    public boolean constrains(Variable variable) {
        // TODO: optimize
        for (int i=variables.length; --i>=0;) {
            if (variable == variables[i]) {
                return true;
            }
        }
        return false;
    }
}
/*
 * $Log$
 * Revision 1.6  2005/09/23 07:50:00  yan
 * fixed jdoc
 *
 * Revision 1.5  2005/09/20 17:28:49  yan
 * added get/setParent
 *
 * Revision 1.4  2004/11/02 13:54:27  yan
 * optimized for loops
 *
 * Revision 1.3  2004/10/20 18:04:09  yan
 * added comment
 *
 * Revision 1.2  2004/10/08 10:12:52  yan
 * removed getError
 *
 * Revision 1.1  2004/10/07 12:18:47  yan
 * initial revision
 *
 */
