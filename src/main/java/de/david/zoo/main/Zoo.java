package de.david.zoo.main;

import de.david.zoo.animal.Animal;

import java.util.ArrayList;
import java.util.List;

public class Zoo {

    private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

    public void addEnclosure(Enclosure<? extends Animal> enclosure) {
        enclosures.add(enclosure);
    }

}
