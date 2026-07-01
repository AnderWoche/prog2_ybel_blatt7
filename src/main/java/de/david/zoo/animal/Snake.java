package de.david.zoo.animal;

public record Snake(String name) implements Animal.Reptile {
    @Override
    public String getName() {
        return name;
    }
}
