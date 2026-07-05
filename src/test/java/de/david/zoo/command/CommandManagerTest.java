package de.david.zoo.command;

import de.david.zoo.animal.Animal;
import de.david.zoo.animal.Gorilla;
import de.david.zoo.animal.Lion;
import de.david.zoo.main.Enclosure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandManagerTest {

    private Enclosure.MammalHouse mammalHouse;
    private CommandManager<Enclosure<Animal.Mammal>> mammalManager;

    @BeforeEach
    void setUp() {
        mammalHouse = new Enclosure.MammalHouse("Säugetier-Haus");
        mammalManager = new CommandManager<>();
    }

    @Test
    void executeCommand_addsAnimalToEnclosure() {
        AddAnimalCommand<Lion> mieze = new AddAnimalCommand<>(new Lion("Mieze"));

        mammalManager.executeCommand(mieze, mammalHouse);

        assertTrue(mammalHouse.getInhabitants().contains(new Lion("Mieze")));
    }

    @Test
    void undo_removesPreviouslyAddedAnimalAgain() {
        AddAnimalCommand<Lion> mieze = new AddAnimalCommand<>(new Lion("Mieze"));
        mammalManager.executeCommand(mieze, mammalHouse);

        mammalManager.undo(mammalHouse);

        assertTrue(mammalHouse.getInhabitants().isEmpty());
    }

    @Test
    void redo_reappliesAnUndoneCommand() {
        AddAnimalCommand<Lion> mieze = new AddAnimalCommand<>(new Lion("Mieze"));
        mammalManager.executeCommand(mieze, mammalHouse);
        mammalManager.undo(mammalHouse);

        mammalManager.redo(mammalHouse);

        assertTrue(mammalHouse.getInhabitants().contains(new Lion("Mieze")));
    }

    @Test
    void executeCommand_clearsRedoStackSoStaleRedoIsANoop() {
        AddAnimalCommand<Lion> mieze = new AddAnimalCommand<>(new Lion("Mieze"));
        AddAnimalCommand<Gorilla> kiki = new AddAnimalCommand<>(new Gorilla("Kiki"));
        mammalManager.executeCommand(mieze, mammalHouse);
        mammalManager.undo(mammalHouse);

        // Neues Kommando nach einem undo() muss den redoStack leeren.
        mammalManager.executeCommand(kiki, mammalHouse);
        mammalManager.redo(mammalHouse);

        assertEquals(1, mammalHouse.getInhabitants().size());
        assertTrue(mammalHouse.getInhabitants().contains(new Gorilla("Kiki")));
    }

    @Test
    void removeCommand_removesAnimalAndUndoAddsItBack() {
        Lion leon = new Lion("Leon");
        mammalHouse.add(leon);
        RemoveAnimalCommand<Lion> removeLeon = new RemoveAnimalCommand<>(leon);

        mammalManager.executeCommand(removeLeon, mammalHouse);
        assertTrue(mammalHouse.getInhabitants().isEmpty());

        mammalManager.undo(mammalHouse);
        assertTrue(mammalHouse.getInhabitants().contains(leon));
    }

    @Test
    void undo_withEmptyUndoStack_doesNothingAndDoesNotThrow() {
        assertDoesNotThrow(() -> mammalManager.undo(mammalHouse));
        assertTrue(mammalHouse.getInhabitants().isEmpty());
    }

    @Test
    void addAnimalCommand_worksAcrossDifferentButCompatibleEnclosureTypes() {
        // Demonstriert die PECS-Typsicherheit aus Aufgabe 2.2/2.3: Ein AddAnimalCommand<Lion>
        // ist ein Command<Enclosure<? super Lion>> und kann daher sowohl auf einem
        // Enclosure<Mammal> (mammalHouse) als auch auf einem Enclosure<Lion> (catHouse)
        // ausgeführt werden.
        Enclosure.CatHouse<Lion> catHouse = new Enclosure.CatHouse<>("Katzen-Haus");
        CommandManager<Enclosure<Lion>> catManager = new CommandManager<>();
        AddAnimalCommand<Lion> felix = new AddAnimalCommand<>(new Lion("Felix"));

        catManager.executeCommand(felix, catHouse);

        assertTrue(catHouse.getInhabitants().contains(new Lion("Felix")));

        // Der Compiler lehnt dagegen tierfremde Kombinationen ab, z.B.:
        // AddAnimalCommand<Salmon> nemo = new AddAnimalCommand<>(new Salmon("Nemo"));
        // mammalManager.executeCommand(nemo, mammalHouse); // <-- Compile-Error, Salmon ist kein Mammal
    }
}
