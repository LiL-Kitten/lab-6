package examples.managers;

import examples.command.Console;
import examples.command.ConsoleColor;
import examples.data.Person;
import examples.exceptions.ExitObliged;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;

/**
 * class for parsing the contents of a file, for reading a file and for saving data to a file
 */

public class FileManager
{
    private Console console;
    private XStream xStream = new XStream();
    private String txt;
    private CollectionManager collectionManager;
    private String path;

    public FileManager(Console console, CollectionManager collectionManager)
    {
        this.console = console ;
        this.collectionManager = collectionManager;
        path = System.getenv("path");
    }

    /**
     * method responsible for searching for a file by specifying its path. Implemented via environment variable
     * @throws ExitObliged
     */
    public void findFile() throws ExitObliged    
    {
        File file = new File(path);
        try
        {
            Scanner scanner = new Scanner(file);
            StringBuilder builder = new StringBuilder();

            while (scanner.hasNext())
            {
               builder.append(scanner.nextLine());
            }
            scanner.close();
            this.txt = builder.toString();
            console.println(ConsoleColor.GREEN+"файл успешно найден можем работать с ним =)");

        }
        catch (FileNotFoundException e) {console.printError("Простите, но файл не найден \n =("); throw new ExitObliged();}
    }

    /**
     * parsing file data for further work with them as collection objects
     * @throws StreamException
     */
    public void create() throws StreamException {
        try {
            if (this.txt == null || this.txt.isEmpty()) {
                console.printError("Простите но файл пуст, выводить так то нечего и проверять тоже\n =(");
                console.println(ConsoleColor.GREEN + "Но ничего страшного, давайте его заполним, посмотрите командочку" + ConsoleColor.CYAN+  " help =)");
                return;
            }

            xStream.alias("person", Person.class);
            xStream.addPermission(AnyTypePermission.ANY);

            CollectionManager collectionManagerDeserial = (CollectionManager) xStream.fromXML(this.txt);
            Collection<Person> persons = collectionManagerDeserial.getCollection();

            for (Person person : persons) {
                this.collectionManager.addElement(person);
            }

            long currentId = this.collectionManager.getCurrentId();

            CollectionManager.setIdCounter(currentId);
            console.println(ConsoleColor.GREEN+"объекты файла валидны, все ОКЭЙ, работаем");

        } catch (StreamException e) {
            console.printError("Объекты в файле не валидны");
        }
    }

    /**
     * method for saving data to a file
     */
    public void save()
    {
        try{
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            out.write(this.xStream.toXML(collectionManager)
                    .getBytes(StandardCharsets.UTF_8));
            out.close();
        } catch (FileNotFoundException e) {
            console.printError("Файл не существует");
        }catch (IOException e){
            console.printError("Ошибка ввода вывода");
        }
    }
}

