package de.david.zoo.command;

public interface Command<T> {

    boolean execute(T target);

    boolean undo(T target);

    String description();

}
