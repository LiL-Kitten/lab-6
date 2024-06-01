package org.example.util;

import org.example.dth.Request;
import org.example.dth.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;


import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class Server {
    private int port;
    private RequestHandler requestHandler;
    private DatagramChannel datagramChannel;
    private final Logger logger = Logger.getLogger(Server.class.getName());

    public Server(int port, RequestHandler handler) {

        this.port = port;
        this.requestHandler = handler;
    }

    public void run() {
        try {
            openServerSocket();
            logger.info("Сервер запущен и ожидает клиентов");
            while (true) {
                processClientRequest();
            }
        } catch (IOException e) {
            logger.severe("Критическая ошибка в сервере: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openServerSocket() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(port));
        logger.info("Открыт UDP канал и привязан к порту " + port);
    }

    private void processClientRequest() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);
        SocketAddress clientAddress = datagramChannel.receive(buffer);


        if (clientAddress != null) {
            buffer.flip();
            logger.info("Получен запрос от клиента по адресу " + clientAddress);

             handleRequest(buffer, clientAddress);
        }
    }

    private void handleRequest(ByteBuffer buffer, SocketAddress clientAddress) {
        try {
            // Десериализация запроса
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                Request request = (Request) objectInputStream.readObject();
                logger.info("Запрос от клиента состоит из команды: " + ConsoleColor.BLUE + request.getCommand() + ConsoleColor.RESET);
                // Обработка запроса
                Response response = requestHandler.handle(request);

                // Сериализация ответа
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

                    objectOutputStream.writeObject(response);
                    objectOutputStream.flush();

                    ByteBuffer responseBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                    datagramChannel.send(responseBuffer, clientAddress);
                    logger.info("Ответ отправлен клиенту по адресу " + clientAddress);
                }
            } catch (ClassNotFoundException e) {
                logger.severe("Не удалось десериализовать запрос: " + e.getMessage());
            }
        } catch (IOException e) {
            logger.severe("Ошибка при обработке запроса: " + e.getMessage());
        }
    }
}