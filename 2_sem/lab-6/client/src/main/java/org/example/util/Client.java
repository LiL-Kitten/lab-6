package org.example.util;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;

import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private String host;
    private int port;
    private Console console;
    private InputManager userInput;
    private Scanner scanner;
    private int maxReconnectionAttempts = 2;
    private int reconnectionTimeout = 5000; // 5 секунд
    private int reconnectionAttempts = 0;
    private static final int BUFFER_SIZE = 1024 * 8;

    public Client(Console console, InputManager input) {
        this.console = console;
        this.userInput = input;
    }

    public void createSocket() {
        boolean condition = false;
        while (!condition) {
            console.println("Введите хост и порт " + ConsoleColor.BLUE + "(в формате: host port)" + ConsoleColor.RESET);
            userInput.commandInput();
            this.host = userInput.getCommand();

            try {
                this.port = Integer.parseInt(userInput.getArgument().trim());
            } catch (NumberFormatException e) {
                console.printError("Неправильный формат порта! Порт должен быть числом.");
                continue;
            }

            try {
                socket = new DatagramSocket();
                socket.connect(InetAddress.getByName(host), port);
                condition = true;
            } catch (UnknownHostException e) {
                console.printError("Проблемы с хостом. Пожалуйста, попробуйте снова.");
            } catch (SocketException e) {
                console.printError("Ошибка создания сокета: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                console.printError("Вы ввели неверный порт. Пожалуйста, попробуйте снова.");
            }
        }
        console.println(ConsoleColor.GREEN + "сокет сформирован" + ConsoleColor.RESET);
    }


    public Response sendAndAskResponse(Request request) {
        try {
            if (Objects.isNull(socket) || socket.isClosed()) throw new IOException();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            byte[] sendData = byteArrayOutputStream.toByteArray();

            if (sendDataWithRetries(sendData)) {
                byte[] receiveData = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivePacket.getData(), 0, receivePacket.getLength());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Response response = (Response) objectInputStream.readObject();

                reconnectionAttempts = 0;
                return response;
            } else {
                throw new RuntimeException("Все попытки отправки данных исчерпаны.");
            }
        } catch (IOException e) {
            handleReconnection();
        } catch (ClassNotFoundException e) {
            console.println("Ошибка при десериализации ответа.%n");
            e.printStackTrace();
        }
        return null;
    }

    private boolean sendDataWithRetries(byte[] data) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverAddress, port);
            socket.send(sendPacket);
            return true;
        } catch (IOException e) {
            console.println("Ошибка при отправке данных:");
            handleReconnection();
            return false;
        }
    }

    private void handleReconnection() {
        if (reconnectionAttempts >= maxReconnectionAttempts) {
            throw new RuntimeException("Превышено максимальное количество попыток отправки данных на сервер");
        }
        console.println("Повторная попытка через "+ reconnectionTimeout / 1000 +" секунд\n");

        reconnectionAttempts++;
        try {
            Thread.sleep(reconnectionTimeout);
        } catch (InterruptedException e) {
            console.println("Попытка отправки данных неуспешна\n");
            e.printStackTrace();
        }
    }
    public void closeConnection()
    {
        socket.close();
    }
}