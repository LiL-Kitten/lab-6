package org.example.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.example.util.ConsoleColor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Objects;


@FunctionalInterface
interface CoordinatesRange<T, S> {
    double getDistanceFromCentre(Double x, long y);
}
@XStreamAlias("person")
public class Person implements Validator, Comparable<Person>, Serializable {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float height; //Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Поле не может быть null
    private Color hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null


    private static long nextID = 1;


    public Person(String name, Coordinates coordinates, Float height, String passportID, Color hairColor, Country nationality, Location location) {
        this.id = incID();
        this.name = name;
        this.coordinates = coordinates;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person(Long id) {
        this.id = id;
    }

    private static long incID() {
        return nextID++;
    }

    public static void updateID(LinkedList<Person> collection) {
        nextID = collection.stream()
                .map(Person::getID)
                .mapToLong(Long::longValue)
                .max().orElse(0) + 1;
    }

    public Long getID() {
        return id;
    }

    public void setID(long newId) {
        this.id = newId;
    }

    public String getName() {
        return name;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + name.hashCode();
        result = 31 * result + coordinates.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + Float.hashCode(height);
        result = 31 * result + passportID.hashCode();
        result = 31 * result + hairColor.hashCode();
        result = 31 * result + nationality.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public boolean validate() {
        if (this.id <= 0) return false;
        if (this.name == null || this.name.isEmpty()) return false;
        if (this.coordinates == null) return false;
        if (this.creationDate == null) return false;
        if (this.height <= 0) return false;
        if (this.passportID == null) return false;
        if (this.hairColor == null) return false;
        if (this.nationality == null) return false;
        return !(this.location == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (this.id != person.id) return false;
        if (this.name != person.name) return false;
        if (this.coordinates != person.coordinates) return false;
        if (this.creationDate != person.creationDate) return false;
        if (this.height != person.height) return false;
        if (this.passportID != person.passportID) return false;
        if (this.hairColor != person.hairColor) return false;
        if (this.nationality != person.nationality) return false;
        return (this.location != person.location);
    }


    public int compareTo(Location o) {
        if (Objects.isNull(o) && o != this.location) return 1;
        CoordinatesRange<Integer, Double> calc = (x, y) -> (double) Math.sqrt(x * x + y * y);
        return Double.compare(
                calc.getDistanceFromCentre(this.getLocation().getX(), this.getLocation().getY()),
                calc.getDistanceFromCentre(o.getX(), o.getY()));
    }

    @Override
    public String toString() {

        return "Person \n" +
                ConsoleColor.toColor("id = ", ConsoleColor.CYAN) + id + '\n' +
                ConsoleColor.toColor("name = ", ConsoleColor.CYAN) + name + '\n' +
                ConsoleColor.toColor("coordinates = ", ConsoleColor.CYAN) + coordinates + '\n' +
                ConsoleColor.toColor("creationDate = ", ConsoleColor.CYAN) + LocalDate.now() + '\n' +
                ConsoleColor.toColor("height = ", ConsoleColor.CYAN) + height + '\n' +
                ConsoleColor.toColor("passportID = ", ConsoleColor.CYAN) + passportID + '\n' +
                ConsoleColor.toColor("hairColor = ", ConsoleColor.CYAN) + hairColor + '\n' +
                ConsoleColor.toColor("nationality = ", ConsoleColor.CYAN) + nationality + '\n' +
                ConsoleColor.toColor("location = ", ConsoleColor.CYAN) + location + '\n';
    }

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
