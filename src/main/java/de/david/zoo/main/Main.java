package de.david.zoo.main;

import de.david.zoo.animal.Salmon;

public class Main {

    public static void main(String[] args) {
        Enclosure.Aquarium aquarium = new Enclosure.Aquarium("Aquarium");

        aquarium.add(new Salmon("Salmon"));

    }

}
