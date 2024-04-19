package examples.managers;

import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.data.Person;
import examples.exceptions.ExitObliged;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.StringReader;

/**
 * class for parsing the contents of a file, for reading a file and for saving data to a file
 */

public class FileManager {
    private Printable console;
    private XStream xStream = new XStream();
    private String txt;
    private CollectionManager collectionManager;
    private String path;

    public FileManager(Printable console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        path = System.getenv("path");
    }

    /**
     * method responsible for searching for a file by specifying its path. Implemented via environment variable
     *
     * @throws ExitObliged
     */
    public void findFile() throws ExitObliged {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();

            while (scanner.hasNext()) {
                builder.append(scanner.nextLine());
            }
            scanner.close();
            this.txt = builder.toString();
            console.println(ConsoleColor.GREEN + "файл успешно найден можем работать с ним =)");

        } catch (FileNotFoundException e) {
            console.printError("Простите, но файл не найден \n =(");
            throw new ExitObliged("Простите, но файл не найден \\n =(");
        }
    }

    /**
     * parsing file data for further work with them as collection objects
     */

    public void create() {
        try {
            if (this.txt == null || this.txt.isEmpty()) {
                System.out.println("файлик пуст");
                return;
            }

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(this.txt));

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT && "person".equals(reader.getLocalName())) {
                    Person person = processPerson(reader);
                    if (person != null && isPersonValid(person)) {
                        this.collectionManager.addElement(person);
                    }
                }
            }
        } catch (Exception e) {
            console.println(ConsoleColor.RED + "ошибка в процессе чтения файла: " + ConsoleColor.PURPLE + e.getMessage() + ConsoleColor.RESET + "\n");
        }
    }

    private Person processPerson(XMLStreamReader reader) throws Exception {
        Long id = null;
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLEvent.END_ELEMENT && "person".equals(reader.getLocalName())) {
                break;
            } else if (event == XMLEvent.START_ELEMENT) {
                switch (reader.getLocalName()) {
                    case "id":
                        try {
                            id = Long.parseLong(reader.getElementText());
                        } catch (NumberFormatException e) {
                            console.println(ConsoleColor.YELLOW + "кривой id был удален");
                            return null;
                        }
                        break;
                }
            }
        }
        return new Person(id);
    }

    private boolean isPersonValid(Person person) {
        return person.getID() != null && person.getID() > 0;
    }

    /**
     * method for saving data to a file
     */
    public void save() {
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            out.write(this.xStream.toXML(collectionManager).getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (FileNotFoundException e) {
            console.printError("Файл не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
    }
}

