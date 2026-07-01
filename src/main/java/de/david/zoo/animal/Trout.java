package de.david.zoo.animal;

public record Trout(String name) implements Animal.Fish {
    @Override
    public String getName() {
        return name;
    }
}
