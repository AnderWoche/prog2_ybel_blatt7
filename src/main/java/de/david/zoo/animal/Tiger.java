package de.david.zoo.animal;

public record Tiger(String name) implements Animal.Mammal.Cat {
    @Override
    public String getName() {
        return name;
    }
}
