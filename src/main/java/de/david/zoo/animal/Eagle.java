package de.david.zoo.animal;

public record Eagle(String name) implements Animal.Bird {
    @Override
    public String getName() {
        return name;
    }
}
