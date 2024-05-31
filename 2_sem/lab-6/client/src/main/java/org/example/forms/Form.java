package org.example.forms;

/**
 * class for working with class which use method build()
 *
 * @param <T> class Person, Coordinates, Location and other
 */

public abstract class Form<T> {
    /**
     * method for creating different objects
     *
     * @return T
     */
    abstract public T build() ;

}

