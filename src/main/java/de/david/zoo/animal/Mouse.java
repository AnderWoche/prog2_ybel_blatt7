package de.david.zoo.animal;

public record Mouse(String name) implements Animal.Mammal.Rodent {
    @Override
    public String getName() {
        return name;
    }
}
