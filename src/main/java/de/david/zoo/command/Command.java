package de.david.zoo.command;

import de.david.zoo.animal.Animal;

public interface Command<T> {

    Result<ZooError, Animal> execute(T target);

    Result<ZooError, Animal> undo(T target);

    String description();

}
