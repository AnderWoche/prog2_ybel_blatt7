package de.david.zoo.animal;

public record Parrot(String name) implements Animal.Bird {
    @Override
    public String getName() {
        return name;
    }
}
