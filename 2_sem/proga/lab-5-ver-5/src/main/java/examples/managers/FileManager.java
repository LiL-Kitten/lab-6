package examples.managers;

import com.thoughtworks.xstream.XStreamer;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.security.AnyTypePermission;
import examples.command.ConsoleColor;
import examples.command.Printable;
import examples.data.Person;
import examples.exceptions.ExitObliged;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;

import javax.xml.stream.XMLStreamReader;

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
        } catch (StreamException e) {
            console.printError("Ошибка при чтении файла");
        }
    }

    public String findFile() throws ExitObliged {
        File file = new File(path);
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder fileContentBuilder = new StringBuilder();

            while (scanner.hasNext()) {
                fileContentBuilder.append(scanner.nextLine());
            }


            return fileContentBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new ExitObliged("Простите, но файл не найден \n =(");
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
