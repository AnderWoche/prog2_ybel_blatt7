package de.david.zoo.command;

import de.david.zoo.animal.Animal;
import de.david.zoo.main.Enclosure;

import java.util.logging.Logger;

public class AddAnimalCommand<T extends Enclosure<Animal>> implements Command<T> {

    private final static Logger LOG = Logger.getLogger(AddAnimalCommand.class.getName());

    private Boolean executed = false;
    private final Animal animal;

    public AddAnimalCommand(Animal animal) {
        this.animal = animal;
    }


    @Override
    public boolean execute(T target) {
        executed = true;

        if(!target.add(animal)) {
            LOG.warning("Animal could not be added to the enclosure");
            return false;
        }

        return true;
    }

    @Override
    public boolean undo(T target) {
        if(!executed) {
            LOG.warning("Execute command was not executed before undo");
            return false;
        }
        if(!target.remove(animal)) {
            LOG.warning("Could not remove animal from enclosure");
            return false;
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
