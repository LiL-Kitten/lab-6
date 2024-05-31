package org.example.dth;

import org.example.data.Person;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

public class Response implements Serializable {
    private String answer;
    private String args;
    private Collection<Person> collection;

    private ResponseStatus status;
    public Response(ResponseStatus status,String answer) {
        this.answer = answer;
        this.status = status;
    }

    public Response(ResponseStatus status, String answer, String args) {
        this.answer = answer;
        this.args = args;
        this.status = status;
    }

    public Response(ResponseStatus status, String answer, String args, Collection<Person> collection) {
        this.answer = answer;
        this.args = args;
        this.collection = collection.stream()
                .sorted(Comparator.comparing(Person::getID))
                .toList();;
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public String getArgs() {
        return args;
    }

    public String getAll() {
        return answer + " " + args;
    }

    public Collection<Person> getCollection() {
        return collection;
    }

    public boolean requiresObject() {
        if (collection == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getResponse() {
        return answer;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return answer;
    }
}
