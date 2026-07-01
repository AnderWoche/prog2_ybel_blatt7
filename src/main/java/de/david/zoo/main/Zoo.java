package de.david.zoo.main;

import de.david.zoo.animal.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Zoo {

    private static final Logger LOG = Logger.getLogger(Zoo.class.getName());

    private final List<Enclosure<? extends Animal>> enclosures = new ArrayList<>();

    public void addEnclosure(Enclosure<? extends Animal> enclosure) {
        LOG.info("addEnclosure aufgerufen mit Gehege '" + enclosure.getName() + "'");

        boolean nameAlreadyUsed = enclosures.stream()
                .anyMatch(existing -> existing.getName().equals(enclosure.getName()));
        if (nameAlreadyUsed) {
            LOG.severe("Inkonsistenz: Es existiert bereits ein Gehege mit dem Namen '"
                    + enclosure.getName() + "' - Gehege-Namen sollten eindeutig sein.");
        }

        enclosures.add(enclosure);
        LOG.fine("Zoo enthält jetzt " + enclosures.size() + " Gehege.");
    }

    // Kopie der Liste, damit von außen nichts an ihr bearbeitet werden kann.
    public List<Enclosure<? extends Animal>> getEnclosures() {
        LOG.info("getEnclosures aufgerufen");
        List<Enclosure<? extends Animal>> result = List.copyOf(enclosures);
        LOG.fine("Zoo enthält " + result.size() + " Gehege.");
        return result;
    }

    /**
     * @return ein beliebiges Gehege, deswegen {@code ? extends Animal}
     */
    public Enclosure<? extends Animal> findEnclosureByName(String name) {
        LOG.info("findEnclosureByName aufgerufen mit name='" + name + "'");

        Enclosure<? extends Animal> found = enclosures.stream()
                .filter(enclosure -> enclosure.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (found == null) {
            LOG.warning("Kein Gehege mit dem Namen '" + name + "' gefunden.");
        } else {
            Enclosure<? extends Animal> foundEnclosure = found;
            LOG.fine("Gehege '" + name + "' gefunden mit "
                    + foundEnclosure.getInhabitants().size() + " Tieren.");
        }
        return found;
    }

    public List<Animal> getAllAnimals() {
        LOG.info("getAllAnimals aufgerufen");
        List<Animal> result = enclosures.stream()
                .flatMap(enclosure -> enclosure.getInhabitants().stream().map(Animal.class::cast))
                .toList();
        LOG.fine("Insgesamt " + result.size() + " Tiere im Zoo gefunden.");
        return result;
    }

    public List<Animal.Mammal> getAllMammals() {
        LOG.info("getAllMammals aufgerufen");
        List<Animal.Mammal> result = getAllAnimals().stream()
                .filter(Animal.Mammal.class::isInstance)
                .map(Animal.Mammal.class::cast)
                .toList();
        LOG.fine(result.size() + " Mammals im Zoo gefunden.");
        return result;
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        LOG.info("getAnimalsByPredicate aufgerufen mit einem Predicate<Animal>");
        List<Animal> result = getAllAnimals().stream()
                .filter(predicate)
                .toList();
        LOG.fine(result.size() + " Tiere erfüllen das übergebene Prädikat.");
        return result;
    }

    /**
     * @return alle Tiere nach Typ/Klasse gemappt auf eine Zahl
     */
    public Map<Class<? extends Animal>, Long> countAnimalsByType() {
        LOG.info("countAnimalsByType aufgerufen");
        Map<Class<? extends Animal>, Long> result = getAllAnimals().stream()
                .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));
        LOG.fine(result.size() + " verschiedene Tierarten im Zoo gezählt.");
        return result;
    }

    /**
     * @return einfach eine Liste mit allen Gehegen, die einen beliebigen Typ haben könnten.
     * Anders geht es aber auch nicht sauber.
     */
    public List<Enclosure<? extends Animal>> getOvercrowdedEnclosures(int threshold) {
        LOG.info("getOvercrowdedEnclosures aufgerufen mit threshold=" + threshold);
        List<Enclosure<? extends Animal>> result = enclosures.stream()
                .filter(enclosure -> enclosure.getInhabitants().size() > threshold)
                .toList();
        LOG.fine(result.size() + " überfüllte Gehege gefunden (Schwelle: " + threshold + ").");
        return result;
    }

    public String summary() {
        LOG.info("summary aufgerufen");

        List<Animal> allAnimals = getAllAnimals();

        Map<String, Long> countsByCategory = allAnimals.stream()
                .collect(Collectors.groupingBy(Zoo::categoryOf, Collectors.counting()));

        String breakdown = countsByCategory.entrySet().stream()
                .map(entry -> entry.getValue() + " " + entry.getKey())
                .collect(Collectors.joining(", "));

        String result = "Zoo mit %d Gehegen und %d Tieren: %s"
                .formatted(enclosures.size(), allAnimals.size(), breakdown);

        LOG.fine("Zusammenfassung erstellt: " + result);
        return result;
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

        IllegalStateException inconsistency =
                new IllegalStateException("Unbekannter Tiertyp: " + animal.getClass());
        LOG.log(Level.SEVERE, inconsistency, () -> "Schwerwiegende Inkonsistenz: Tier " + animal + " passt zu keiner bekannten Kategorie.");
        throw inconsistency;
    }
}
