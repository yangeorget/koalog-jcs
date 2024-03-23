package com.koalog.jcs.constraint;

import com.koalog.jcs.NamedObject;
import com.koalog.jcs.variable.Variable;

/** 
 * An common interface for both constraints and problems.
 * 
 * <P>A relation, as a named object, is identified by a unid 
 * (which is thus shared by constraints and problems). 
 *
 * @author Yan Georget
 * @since 2.3
 */
public interface AbstractRelation extends NamedObject { 
    /**
     * Gets the variables of the relation.
     * @return an array of variables
     */
    Variable[] getVariables();

    /** 
     * Sets the variables of the relation.
     * @param v an array of variables
     */
    void setVariables(Variable[] v);

    /**
     * Returns a boolean indicating if the relation 
     * constrains the variable. 
     * @param variable a variable
     * @return a boolean
     */
    boolean constrains(Variable variable);

    /**
     * Gets the parent problem of the relation.
     * @return a problem or <CODE>null</CODE>
     * @since 3.0
     */
    Problem getParent();

    /**
     * Sets the parent problem of the relation.
     * @param parent a problem 
     * @since 3.0
     */
    void setParent(Problem parent);
}
/*
 * $Log$
 * Revision 1.5  2005/09/20 17:30:06  yan
 * fixed jdoc
 *
 * Revision 1.4  2005/09/20 17:28:24  yan
 * added get/setParent
 *
 * Revision 1.3  2004/10/08 10:12:45  yan
 * removed getError
 *
 * Revision 1.2  2004/10/01 17:41:40  yan
 * various small refactoring
 *
 * Revision 1.1  2004/09/30 15:40:53  yan
 * initial revision
 *
 */
