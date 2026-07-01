package de.david.zoo.main;

import de.david.zoo.animal.Lion;
import de.david.zoo.animal.Trout;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ZooDemo {

    public static void main(String[] args) {
        Logger zooLogger = Logger.getLogger(Zoo.class.getName());

        // Der Standard-Handler der Root-Konfiguration lässt nur Level INFO und höher durch,
        // egal welches Level man dem Logger selbst gibt. Deshalb eigener Handler mit ALL,
        // der die eigentliche Filterung dem Logger-Level überlässt.
        zooLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        zooLogger.addHandler(handler);

        Zoo zoo = new Zoo();
        Enclosure.Aquarium aquarium = new Enclosure.Aquarium("Aqua1");
        aquarium.add(new Trout("Nemo"));

        Enclosure.MammalHouse mammalHouse = new Enclosure.MammalHouse("Saeuger1");
        mammalHouse.add(new Lion("Leo"));

        System.out.println("=== Log-Level: INFO (Standard) ===");
        zooLogger.setLevel(Level.INFO);
        zoo.addEnclosure(aquarium);            // INFO sichtbar, FINE (noch) nicht
        zoo.addEnclosure(mammalHouse);
        zoo.findEnclosureByName("Unbekannt");  // zusätzlich WARNING sichtbar

        System.out.println("\n=== Log-Level: FINE ===");
        zooLogger.setLevel(Level.FINE);
        zoo.getAllAnimals();                   // jetzt auch die FINE-Zustandszusammenfassung sichtbar

        System.out.println("\n=== Log-Level: OFF ===");
        zooLogger.setLevel(Level.OFF);
        zoo.summary();                         // kein Log-Output mehr
    }
}
