package com.koalog.jcs.choicepoint;

/**
 * Interface for objects storable in a choice point.
 *
 * @see ChoicePoint 
 * @since 2.1
 * @author Yan Georget
 */
public interface StorableValue {
    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------  
    /**
     * Returns a copy of a storable value.
     * @return a storable value
     */
    StorableValue copy();
}
/*
 * $Log$
 * Revision 1.2  2004/06/16 16:26:30  yan
 * fixed javadoc
 *
 * Revision 1.1  2004/06/14 15:56:23  yan
 * initial revision
 *
 */
