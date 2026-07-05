package de.david.zoo.command;

import de.david.zoo.animal.Animal;
import de.david.zoo.main.Enclosure;

/**
 * PECS: Das Ziel-Gehege ist "Consumer" von {@code A} (wir fügen ein {@code A} hinein),
 * daher {@code Enclosure<? super A>} - so kann z.B. ein {@code AddAnimalCommand<Lion>}
 * auf einem {@code Enclosure<Mammal>} ausgeführt werden.
 */
public class AddAnimalCommand<A extends Animal> implements Command<Enclosure<? super A>> {

    private boolean executed = false;
    private final A animal;

    public AddAnimalCommand(A animal) {
        this.animal = animal;
    }

    @Override
    public Result<ZooError, Animal> execute(Enclosure<? super A> target) {
        executed = true;

        if (!target.add(animal)) {
            return Result.failure(ZooError.ANIMAL_ALREADY_IN_ENCLOSURE);
        }
        return Result.success(animal);
    }

    @Override
    public Result<ZooError, Animal> undo(Enclosure<? super A> target) {
        if (!executed) {
            return Result.failure(ZooError.COMMAND_NOT_YET_EXECUTED);
        }
        if (!target.remove(animal)) {
            return Result.failure(ZooError.ANIMAL_NOT_IN_ENCLOSURE);
        }
        return Result.success(animal);
    }

    @Override
    public String description() {
        return "AddAnimalCommand[" + animal.getName() + "]";
    }
}
