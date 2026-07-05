package de.david.zoo.command;

import de.david.zoo.animal.Animal;
import de.david.zoo.main.Enclosure;

import java.util.logging.Logger;

/**
 * PECS: Das Ziel-Gehege ist "Consumer" von {@code A} (wir entfernen/fügen ein {@code A}),
 * daher {@code Enclosure<? super A>}, analog zu {@link AddAnimalCommand}.
 */
public class RemoveAnimalCommand<A extends Animal> implements Command<Enclosure<? super A>> {

    private final static Logger LOG = Logger.getLogger(RemoveAnimalCommand.class.getName());

    private boolean executed = false;
    private final A animal;

    public RemoveAnimalCommand(A animal) {
        this.animal = animal;
    }

    @Override
    public boolean execute(Enclosure<? super A> target) {
        executed = true;
        if (!target.remove(animal)) {
            LOG.warning("Could not remove animal from enclosure");
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
        if (!target.add(animal)) {
            LOG.warning("Could not add animal to enclosure");
            return false;
        }

        return true;
    }

    @Override
    public String description() {
        return "RemoveAnimalCommand[" + animal.getName() + "]";
    }
}
