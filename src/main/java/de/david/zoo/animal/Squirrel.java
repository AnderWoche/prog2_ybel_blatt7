package de.david.zoo.animal;

public record Squirrel(String name) implements Animal.Mammal.Rodent {
    @Override
    public String getName() {
        return name;
    }
}
