package org.example.managers;


import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.example.exceptions.InvalidForm;
import org.example.managers.CollectionManager;
import org.example.util.ConsoleColor;
import org.example.util.Printable;
import org.example.data.Person;
import org.example.exceptions.ExitObliged;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;



/**
 * class for parsing the contents of a file, for reading a file and for saving data to a file
 */
public class FileManager {
    public static final String FILE_PATH = "path";

    private Printable console;
    private XStream xStream = new XStream();
    private CollectionManager collectionManager;
    private String path;


    public FileManager(Printable console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
        this.path = System.getenv(FILE_PATH);

        try {
            findFile();
            console.println(ConsoleColor.GREEN + "файл успешно найден можем работать с ним =)");
            create();
        } catch (ExitObliged e) {
            console.printError(e.getMessage());
            System.exit(1);
        } catch (StreamException e) {
            console.printError("Ошибка при чтении файла");
        }
    }

    public String findFile() throws ExitObliged {
        File file = new File(path);
        if (path == null || path.isEmpty() || !file.exists()) {
            throw new ExitObliged("бикоз путя нима");
        }
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder fileContentBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                fileContentBuilder.append(scanner.nextLine());
            }
            return fileContentBuilder.toString();
        } catch (FileNotFoundException e) {
            // Этот блок кода не будет выполняться, так как мы проверили существование файла ранее
            // Но он обязателен для компиляции, так как Scanner может выбросить FileNotFoundException
            throw new RuntimeException("Это сообщение никогда не будет выведено", e);
        }
    }

    public void create() throws StreamException, ExitObliged {
        String fileContent = findFile();
        if (fileContent == null || fileContent.isEmpty()) {
            console.printError("Простите но файл пуст, выводить так то нечего и проверять тоже\n =(");
            console.println(ConsoleColor.GREEN + "Но ничего страшного, давайте его заполним, посмотрите командочк" + "у" + ConsoleColor.CYAN + " help =)");
            return;
        }

        xStream.alias("person", Person.class);
        xStream.addPermission(AnyTypePermission.ANY);


        try {

            CollectionManager deserializedCollectionManager = (CollectionManager) xStream.fromXML(fileContent);
            Collection<Person> persons = deserializedCollectionManager.getCollection();

            for (Person person : persons) {
                this.collectionManager.addElement(person);
            }
        } catch (ConversionException e) {
            console.printError("Ошибка при десериализации XML файла: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path))) {
            out.write(this.xStream.toXML(collectionManager).getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            console.printError("Файл не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
    }
}
