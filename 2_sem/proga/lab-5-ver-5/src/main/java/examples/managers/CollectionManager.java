package examples.managers;


import examples.data.Person;

import java.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * class for working with the Person collection
 */


public class CollectionManager {

    /**
     * @param idCounter counter id
     */
    private static long idCounter;
    /**
     * @param lastInitDate date of last collection initialization
     */
    private LocalDate lastInitDate;
    /**
     * @param lastSaveDate date the collection was last saved
     */
    private LocalDate lastSaveDate;
    /**
     * @param collection variable required to work with LinkedList collection methods
     */
    private LinkedList<Person> collection;

    public CollectionManager() {
        collection = new LinkedList<>();
        lastInitDate = LocalDate.now();
    }

    /**
     * adding a person object to the collection (also indicating its creation date)
     *
     * @param person
     */
    public void addElement(Person person) {
           person.setID(getCurrentId());
           person.setCreationDate(LocalDate.now());
           this.collection.add(person);
    }

    /**
     * method to set the current id of the person object in the collection
     *
     * @param idCounter
     */
    public static void setIdCounter(long idCounter) {
        CollectionManager.idCounter = idCounter;
    }

    /**
     * method for checking the presence of an element with a given id
     *
     * @param id
     * @return boolean variable
     */
    public boolean checkID(long id) {
        for (Person person : collection) {
            if (person.getID() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return current id in the file
     */
    public long getCurrentId() {
        return collection.stream()
                .mapToLong(Person::getID)
                .max()
                .orElse(0L) + 1;
    }

    /**
     * removing items from a collection
     *
     * @param id
     */
    public void removeByID(long id) {
        collection = collection.stream()
                .filter(person -> person.getID() != id)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * removes all Person objects from the collection
     *
     * @param elements a variable through which a stream of Person objects is passed
     */
    public void removeElements(Collection<Person> elements) {
        collection.removeAll(elements);
    }

    /**
     * returns the first element of the collection
     *
     * @return Person information
     */
    public Person getFirst() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("Collection is empty");
        }
        return collection.stream().findFirst().get();
    }

    /**
     * method to remove the first element from a collection
     *
     * @return displays data about a remote object
     */
    public Person removeFirst() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("Collection is empty");
        }
        collection.removeFirst();
        return collection.getFirst();
    }

    /**
     * iterates through the collection, finds the id of the element and replaces it with a new one
     *
     * @param id    old id (long)
     * @param newId new id (long)
     */
    public void updateId(long id, long newId) {
        collection.stream()
                .filter(person -> person.getID() == id)
                .findFirst()
                .ifPresent(person -> person.setID(newId));
    }

    /**
     * method for returning a collection with a Person generic for further interaction with the collection
     *
     * @return Collection/<Person>
     */
    public Collection<Person> getCollection() {
        return collection;
    }

    /**
     * method returns collection size
     *
     * @return size, type collection Integer
     */
    public Integer collectionSize() {
        return collection.size();
    }

    /**
     * method to return the collection type
     *
     * @return String
     */
    public String collectionType() {
        return collection.getClass().getSimpleName();
    }

    /**
     * method to get the date the collection was last initialized
     *
     * @return LocalDate
     */
    public LocalDate getLastInitDate() {
        return lastInitDate;
    }

    /**
     * method to return the last saved date
     *
     * @return LocalDate
     */
    public LocalDate getLastSaveDate() {
        return lastSaveDate;
    }

    public void setLastSaveDate() {
        this.lastSaveDate = LocalDate.now();
    }

    /**
     * method for clearing the collection completely
     */
    public void clear() {
        collection.clear();
    }

}
