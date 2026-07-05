package de.david.zoo.command;

import de.david.zoo.animal.Animal;
import de.david.zoo.main.Enclosure;

import java.util.logging.Logger;

/**
 * PECS: Das Ziel-Gehege ist "Consumer" von {@code A} (wir fügen ein {@code A} hinein),
 * daher {@code Enclosure<? super A>} - so kann z.B. ein {@code AddAnimalCommand<Lion>}
 * auf einem {@code Enclosure<Mammal>} ausgeführt werden.
 */
public class AddAnimalCommand<A extends Animal> implements Command<Enclosure<? super A>> {

    private final static Logger LOG = Logger.getLogger(AddAnimalCommand.class.getName());

    private boolean executed = false;
    private final A animal;

    public AddAnimalCommand(A animal) {
        this.animal = animal;
    }

    @Override
    public boolean execute(Enclosure<? super A> target) {
        executed = true;

        if (!target.add(animal)) {
            LOG.warning("Animal could not be added to the enclosure");
            return false;
        }

        return true;
    }

    @Override
    public boolean undo(Enclosure<? super A> target) {
        if (!executed) {
            LOG.warning("Execute command was not executed before undo");
            return false;
        }
        if (!target.remove(animal)) {
            LOG.warning("Could not remove animal from enclosure");
            return false;
        }

        return true;
    }

    @Override
    public String description() {
        return "AddAnimalCommand[" + animal.getName() + "]";
    }
}
