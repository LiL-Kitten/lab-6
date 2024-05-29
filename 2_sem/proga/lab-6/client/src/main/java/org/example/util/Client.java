package org.example.util;

import org.example.dth.Request;
import org.example.dth.Response;
import org.example.dth.ResponseStatus;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private String host;
    private int port;
    private Console console;
    private ScannerManager scanner;
    private int maxReconnectionAttempts = 5;
    private int reconnectionTimeout = 5000; // 5 секунд
    private int reconnectionAttempts = 0;
    private static final int BUFFER_SIZE = 1024 * 8;

    public Client(Console console, ScannerManager scanner) {
        this.console = console;
        this.scanner = scanner;
    }

    public void createSocket() {
        boolean condition = false;
        while (!condition) {
            console.println("Введите хост и порт " + ConsoleColor.BLUE + "(в формате: host port)" + ConsoleColor.RESET);
            String text = scanner.getInput().nextLine().trim();
            String[] tokens = text.split("\\s+", 2);

            if (tokens.length < 2) {
                console.printError("Неправильный формат ввода! Введите хост и порт в формате: host port");
                continue;
            }

            this.host = tokens[0];

            try {
                this.port = Integer.parseInt(tokens[1].trim());
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
            if (request.isEmpty()) return new Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой!");

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
            console.printError("Ошибка при десериализации ответа.");
            e.printStackTrace();
        }
        return null;
    }

    private boolean sendDataWithRetries(byte[] data) {
        reconnectionAttempts = 0;
        while (reconnectionAttempts < maxReconnectionAttempts) {
            try {
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverAddress, port);
                socket.send(sendPacket);
                return true;
            } catch (IOException e) {
                console.printError("Ошибка при отправке данных: " + e.getMessage());
                handleReconnection();
            }
        }
        return false;
    }

    private void handleReconnection() {
        if (reconnectionAttempts == 0) {
            console.println("Попытка отправки данных");
        } else {
            console.printError("Не удалось отправить данные на сервер");
            if (reconnectionAttempts >= maxReconnectionAttempts) {
                console.printError("Превышено максимальное количество попыток отправки данных на сервер");
                throw new RuntimeException("Превышено максимальное количество попыток отправки данных на сервер");
            }
            console.println(ConsoleColor.GREEN + "Повторная попытка через " + reconnectionTimeout / 1000 + " секунд" + ConsoleColor.RESET);
        }
        reconnectionAttempts++;

        try {
            Thread.sleep(reconnectionTimeout);

        } catch (InterruptedException e) {
            console.printError("Попытка отправки данных неуспешна");
            e.printStackTrace();
        }
    }
    public void closeConnection()
    {
        socket.close();
    }
}