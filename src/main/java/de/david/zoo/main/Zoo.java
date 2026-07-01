package de.david.zoo.main;

import de.david.zoo.animal.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Zoo {

    private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

    public void addEnclosure(Enclosure<? extends Animal> enclosure) {
        enclosures.add(enclosure);
    }

    // Kopie der Liste, damit von außen nichts an ihr bearbeitet werden kann.
    public List<Enclosure<? extends Animal>> getEnclosures() {
        return List.copyOf(enclosures);
    }

    /**
     * @return ein beliebiges Gehege, deswegen {@code ? extends Animal}
     */
    public Enclosure<? extends Animal> findEnclosureByName(String name) {
        return enclosures.stream()
                .filter(enclosure -> enclosure.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Animal> getAllAnimals() {
        return enclosures.stream()
                .flatMap(enclosure -> enclosure.getInhabitants().stream().map(Animal.class::cast))
                .toList();
    }

    public List<Animal.Mammal> getAllMammals() {
        return getAllAnimals().stream()
                .filter(Animal.Mammal.class::isInstance)
                .map(Animal.Mammal.class::cast)
                .toList();
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        return getAllAnimals().stream()
                .filter(predicate)
                .toList();
    }

    /**
     * @return alle Tiere nach Typ/Klasse gemappt auf eine Zahl
     */
    public Map<Class<? extends Animal>, Long> countAnimalsByType() {
        return getAllAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));
    }

    /**
     * @return einfach eine Liste mit allen Gehegen, die einen beliebigen Typ haben könnten.
     * Anders geht es aber auch nicht sauber.
     */
    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int threshold) {
        return enclosures.stream()
                .filter(enclosure -> enclosure.getInhabitants().size() > threshold)
                .toList();
    }

    public String summary() {
        List<Animal> allAnimals = getAllAnimals();

        Map<String, Long> countsByCategory = allAnimals.stream()
                .collect(Collectors.groupingBy(Zoo::categoryOf, Collectors.counting()));

        String breakdown = countsByCategory.entrySet().stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.joining(", "));

        return "Zoo mit %d Gehegen und %d Tieren: %s"
                .formatted(enclosures.size(), allAnimals.size(), breakdown);
    }

    private static String categoryOf(Animal animal) {
        if (animal instanceof Animal.Mammal) {
            return "Mammals";
        }
        if (animal instanceof Animal.Bird) {
            return "Birds";
        }
        if (animal instanceof Animal.Fish) {
            return "Fish";
        }
        if (animal instanceof Animal.Reptile) {
            return "Reptiles";
        }
        throw new IllegalStateException("Unknown animal type: " + animal.getClass());
    }
}
