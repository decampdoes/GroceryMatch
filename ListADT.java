// DO NOT EDIT THIS INTERFACE

import java.util.Iterator;

public interface ListADT<E> {

    // get the iterator of this List.
    Iterator<E> iterator(); 

    void add(E item); 

    // add item at position pos in the List, moving the items originally in positions 
    // pos through size()-1 one place to the right to make room 
    // (error if pos is less than 0 or greater than size())
    // throw IndexOutOfBoundsException if pos is less than zero or greater than size
    void add(int pos, E item); 

    // return true iff item is in the List (i.e., there is an item x in the List such that x.equals(item))
    boolean contains(E item); 

    // return the number of items in the List.
    int size(); 

    // return if the List is empty.
    boolean isEmpty(); 

    //  return the item at position pos in the List 
    // throw IndexOutOfBoundsException if pos is less than zero or greater than or equal to size
    E get(int pos); 

    // remove and return the item at position pos in the List, 
    // shifting the items originally in positions pos+1 through size()-1 
    // one place to the left to fill in the gap
    // throw IndexOutOfBoundsException if pos is less than zero or greater than or equal to size
    E remove(int pos); 
}
