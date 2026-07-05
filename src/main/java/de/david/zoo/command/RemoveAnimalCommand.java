package de.david.zoo.command;

import de.david.zoo.animal.Animal;
import de.david.zoo.main.Enclosure;

/**
 * PECS: Das Ziel-Gehege ist "Consumer" von {@code A} (wir entfernen/fügen ein {@code A}),
 * daher {@code Enclosure<? super A>}, analog zu {@link AddAnimalCommand}.
 */
public class RemoveAnimalCommand<A extends Animal> implements Command<Enclosure<? super A>> {

    private boolean executed = false;
    private final A animal;

    public RemoveAnimalCommand(A animal) {
        this.animal = animal;
    }

    @Override
    public Result<ZooError, Animal> execute(Enclosure<? super A> target) {
        executed = true;

        if (!target.remove(animal)) {
            return Result.failure(ZooError.ANIMAL_NOT_IN_ENCLOSURE);
        }
        return Result.success(animal);
    }

    @Override
    public Result<ZooError, Animal> undo(Enclosure<? super A> target) {
        if (!executed) {
            return Result.failure(ZooError.COMMAND_NOT_YET_EXECUTED);
        }
        if (!target.add(animal)) {
            return Result.failure(ZooError.ANIMAL_ALREADY_IN_ENCLOSURE);
        }
        return Result.success(animal);
    }

    @Override
    public String description() {
        return "RemoveAnimalCommand[" + animal.getName() + "]";
    }
}
