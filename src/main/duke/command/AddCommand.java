package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.DukeException;
import duke.task.Task;
import duke.task.TodoTask;
import duke.task.DeadlineTask;
import duke.task.EventTask;

public class AddCommand extends Command {

    private Task taskToAdd;
    
    public AddCommand(String fullCommand) throws DukeException{
        parseTask(fullCommand);
    }

    private void parseTask(String fullCommand) throws DukeException{
        switch (fullCommand.split(" ")[0]) {
        case "todo":
            String todoDescriptions = fullCommand.replace("todo", "");
            if (todoDescriptions.trim().isEmpty()) {
                throw new DukeException("Empty Todo Command!");
            }
            this.taskToAdd = new TodoTask(todoDescriptions.trim());
            break;
        case "deadline":
            String[] deadlineDescriptions = fullCommand.replace("deadline", "").split("/by");
            if (deadlineDescriptions[0].trim().isEmpty() || deadlineDescriptions[1].trim().isEmpty()) {
                throw new DukeException("Empty Deadline Command!");
            }
            this.taskToAdd = new DeadlineTask(deadlineDescriptions[0].trim(), deadlineDescriptions[1].trim());
            break;
        case "event":
            String[] eventDescriptions = fullCommand.replace("event", "").split("/at");
            if (eventDescriptions[0].trim().isEmpty() || eventDescriptions[1].trim().isEmpty()) {
                throw new DukeException("Empty Event Command!");
            }
            this.taskToAdd = new EventTask(eventDescriptions[0].trim(), eventDescriptions[1].trim());
            break;
        default:
            throw new DukeException("Invalid Command!");
        }
    }

    /**
     * Execute user commands.
     * @param tasks List of tasks.
     * @param ui Ui of Duke chatbot.
     * @param storage Storage of Duke chatbot.
     * @throws DukeException If execution fails.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.add(taskToAdd);
        System.out.println(String.format("Task Added!\n %s", taskToAdd));
        storage.save(tasks);
    }

    /**
     * Check if user is ending the chatbot.
     * @return True if user is ending the chatbot.
     */
    public boolean isExit() {
        return false;
    }

}
