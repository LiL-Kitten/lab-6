package examples.data.forms;

import examples.exceptions.InvalidForm;

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
     * @throws InvalidForm
     */
    abstract public T build() throws InvalidForm;

}

