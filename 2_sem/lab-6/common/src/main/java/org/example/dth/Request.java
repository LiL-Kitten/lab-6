package org.example.dth;

import org.example.data.Person;

import java.io.Serializable;

public class Request implements Serializable
{
    private static final long serialVersionUID = 8877794645528610544L;
    private String command;
    private String arg;

    private Person person;

    public Request(Person person) {
        this.person = person;
    }

    public Request(String command, String arg) {
        this.command = command.trim();
        this.arg = arg.trim();

    }

    public Request(String command, Person person) {
        this.command = command.trim();
        this.person = person;
    }

    public Request(String command) {
        this.command = command.trim();
    }

    public  String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public Person getObj() {
        return person;
    }

    public boolean isEmpty() {
        return command.isEmpty() && arg.isEmpty() && person == null;
    }
}



//принцип следующий мы должны отправлять команды, с какими то аргументами и эти команды должны быть читаемы
// для сервера, то есть по факту идет разбиение ввода данных на массив строк, который будет проходить определенные
// проверки. Должны быть проверки самой команды на соответствие командам сервера, правильно ли переданы аргументы
// команды. Нужен статус отправки команды
//Могут быть слабые клиенты: все ресурсоемкие задачи можно перенести на сервер.
//Независимое развитие кода клиентов и кода сервера.
