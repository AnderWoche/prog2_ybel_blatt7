package de.david.zoo.animal;

public record Gorilla(String name) implements Animal.Mammal.Primate {
    @Override
    public String getName() {
        return name;
    }
}
