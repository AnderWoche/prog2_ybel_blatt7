package de.david.zoo.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandManager<T> {

    private final Deque<Command<T>> stack = new ArrayDeque<>();
    private final Deque<Command<T>> undoStack = new ArrayDeque<>();
    private final Deque<Command<T>> redoStack = new ArrayDeque<>();

    public void executeCommand(Command<T> command, T target) {
        if(command.execute(target)) {
            stack.push(command);
            redoStack.clear();
        }
    }

    public void undo(T target) {
        if(!undoStack.isEmpty()) {
            Command<T> command = undoStack.pop();
            command.undo(target);
            redoStack.push(command);
        }
    }

    public void redo(T target) {
        if(!redoStack.isEmpty()) {
            Command<T> command = redoStack.pop();
            command.execute(target);
            undoStack.push(command);
        }
    }

}
