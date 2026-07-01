package de.david.zoo.animal;

public record Turtle(String name) implements Animal.Reptile {
    @Override
    public String getName() {
        return name;
    }
}
