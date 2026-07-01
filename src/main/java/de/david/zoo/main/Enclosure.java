package de.david.zoo.main;

import de.david.zoo.animal.Animal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Enclosure<T extends Animal> {

    private final String name;

    /**
     * Begründung: Sett ist wie eine liste aber es fürfen keine doppelten Werte forkommen.
     * tiere von der kleichen klasse werden ersetzt.
     */
    private final Set<T> animals = new HashSet<>();

    public Enclosure(String name) {
        this.name = name;
    }

    public boolean add(T animal) {
        return animals.add(animal);
    }

    public boolean remove(T animal) {
        return animals.remove(animal);
    }

    public List<T> getInhabitants() {
        return List.copyOf(animals);
    }

    public String getName() {
        return this.name;
    }


    // impl
    public static class Aquarium extends Enclosure<Animal.Fish> {
        public Aquarium(String name) {
            super(name);
        }
    }

    public static class Terrarium extends Enclosure<Animal.Reptile> {
        public Terrarium(String name) {
            super(name);
        }
    }

    public static class MammalHouse extends Enclosure<Animal.Mammal> {
        public MammalHouse(String name) {
            super(name);
        }
    }

    public static class CatHouse<T extends Animal.Mammal.Cat> extends Enclosure<T> {
        public CatHouse(String name) {
            super(name);
        }
    }
}
