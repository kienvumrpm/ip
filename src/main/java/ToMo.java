public class ToMo {
    private Parser parser;
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public ToMo(String fileName) {
        parser = new Parser();
        ui = new Ui();
        storage = new Storage(fileName);
        try {
            tasks = storage.load();
            ui.println("Successfully loaded " + tasks.size() + " tasks");
        } catch (ToMoException e) {
            ui.println(e);
            tasks = new TaskList();
            ui.println("Successfully loaded 0 task");
        }
    }

    void close() {
        try {
            storage.store(tasks);
        } catch (ToMoException e) {
            ui.println(e);
        }
        ui.close();
    }

    private boolean processOneCommand() throws ToMoException {
        ui.help();
        String cmd = ui.nextLine();
        String[] args = parser.parse(cmd);

        if (args.length == 0) return false;

        if (args[0].equals("bye")) {
            return true;
        } else if (args[0].equals("list")) {
            ui.println("The tasks list as follow:");
            for (int i = 0; i < tasks.size(); ++i) {
                ui.println((i + 1) + ". " + tasks.get(i));
            }
        } else if (args[0].equals("mark")) {
            int idx = Integer.parseInt(args[1]) - 1;
            Task task = tasks.mark(idx);
            ui.println("A task is marked");
            ui.println(task);
        } else if (args[0].equals("unmark")) {
            int idx = Integer.parseInt(args[1]) - 1;
            Task task = tasks.unmark(idx);
            ui.println("A task is unmarked");
            ui.println(task);
        } else if (args[0].equals("delete")) {
            int idx = Integer.parseInt(args[1]) - 1;
            Task task = tasks.delete(idx);
            ui.println("A task is deleted");
            ui.println(task);
        } else if (args[0].equals("todo")) {
            Task task = new ToDo(args[1]);
            tasks.add(task);
            ui.println("A task is added");
            ui.println(task);
        } else if (args[0].equals("deadline")) {
            Task task = new Deadline(args[1], args[2]);
            tasks.add(task);
            ui.println("A task is added");
            ui.println(task);
        } else {
            Task task = new Event(args[1], args[2], args[3]);
            tasks.add(task);
            ui.println("A task is added");
            ui.println(task);
        }
        return false;
    }

    public void run() {
        while (true) {
            try {
                if (processOneCommand()) {
                    break;
                }
            } catch (ToMoException e) {
                ui.println(e);
            }
        }

        close();
    }

    public static void main(String[] args) {
        new ToMo("../../../data/ToMo.txt").run();
    }
}