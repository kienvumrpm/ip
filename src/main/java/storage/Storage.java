package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import exception.StorageException;
import task.Converter;
import task.Deadline;
import task.Event;
import task.Task;
import task.ToDo;
import tasklist.TaskList;

/**
 * Handles loading tasks from and storing tasks to from file
*/
public class Storage {
    private File file;
    /**
     * Constructor of the storage
     * @param fileName The to load and store tasks
     */
    public Storage(String fileName) {
        this.file = new File(fileName);
    }

    /**
     * Loads the task list from file
     * @return The task list loaded from the file
     * @throws StorageException If the storage file is not found
     */
    public TaskList load() throws StorageException {
        TaskList tasks = new TaskList();
        Scanner scanner;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new StorageException(e.getMessage());
        }

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] args = line.split("\\[|\\]\\[|\\] ");
            if (args[1].equals("T")) {
                Task task = new ToDo(args[3]);
                if (args[2].equals("X")) {
                    task.mark();
                }
                tasks.add(task);
            } else if (args[1].equals("D")) {
                String regex = "\\[|\\]\\[|\\] | \\(by: |\\)";
                args = line.split(regex);
                Task task = new Deadline(args[3], Converter.outputToDateTime(args[4]));
                if (args[2].equals("X")) {
                    task.mark();
                }
                tasks.add(task);
            } else {
                String regex = "\\[|\\]\\[|\\] | \\(from: | to: |\\)";
                args = line.split(regex);
                Task task = new Event(args[3],
                                        Converter.outputToDateTime(args[4]),
                                        Converter.outputToDateTime(args[5]));
                if (args[2].equals("X")) {
                    task.mark();
                }
                tasks.add(task);
            }
        }
        scanner.close();
        return tasks;
    }
    /**
     * Stores the task into file
     * @param tasks The task list to be stored
     * @throws StorageException When encounters problem when trying writing to file
     */
    public void store(TaskList tasks) throws StorageException {
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            for (int i = 0; i < tasks.size(); ++i) {
                writer.write(tasks.get(i) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
