package org.example.util;

import org.example.dth.Request;
import org.example.dth.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

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
        openServerSocket();
        logger.info("Сервер запущен и ожидает клиентов");
        while (true) {
            try {
                processClientRequest();
            } catch (IOException e) {
                logger.severe("Произошла ошибка при обработке запроса клиента!");
                e.printStackTrace();
            }
        }
    }

    private void openServerSocket() {
        try {
            datagramChannel = DatagramChannel.open();
            datagramChannel.bind(new InetSocketAddress(port));
            logger.info("Открыт UDP канал и привязан к порту " + port);
        } catch (IOException e) {
            logger.severe("Произошла ошибка при попытке использовать порт");
        }
    }

    private void processClientRequest() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024*8);
        SocketAddress clientAddress = datagramChannel.receive(buffer);
        if (clientAddress == null) return; // Нет данных для чтения
        buffer.flip();

        // Десериализация запроса
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

            Request request;
            try {
                request = (Request) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                logger.severe("Не удалось десериализовать запрос");
                return;
            }

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
        }
    }
}