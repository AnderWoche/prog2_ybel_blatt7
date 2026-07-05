package de.david.zoo.command;

import de.david.zoo.main.Enclosure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Logger;

/**
 * Der Typ-Parameter {@code T} beschreibt den Gehege-Typ. Die Commands selbst tragen
 * bereits die passende Wildcard ({@code Enclosure<? super A>}), deswegen genügt hier
 * {@code Command<? super T>} - ein Command, das für ein Gehege oberhalb (oder gleich) T
 * geeignet ist, kann sicher auf einem T ausgeführt werden.
 */
public class CommandManager<T extends Enclosure<?>> {

    private final static Logger LOG = Logger.getLogger(CommandManager.class.getName());

    private final Deque<Command<? super T>> undoStack = new ArrayDeque<>();
    private final Deque<Command<? super T>> redoStack = new ArrayDeque<>();

    public void executeCommand(Command<? super T> command, T target) {
        LOG.info("executeCommand aufgerufen mit '" + command.description() + "'");

        if (command.execute(target)) {
            undoStack.push(command);
            redoStack.clear();
        } else {
            LOG.warning("Kommando '" + command.description() + "' konnte nicht ausgeführt werden.");
        }

        LOG.fine(() -> "Zustand von '" + target.getName() + "': " + target.getInhabitants());
    }

    public void undo(T target) {
        LOG.info("undo aufgerufen");

        if (undoStack.isEmpty()) {
            LOG.warning("undo nicht ausgeführt: Es liegt kein Kommando auf dem undoStack.");
        } else {
            Command<? super T> command = undoStack.pop();
            if (!command.undo(target)) {
                LOG.warning("undo für Kommando '" + command.description() + "' ist fehlgeschlagen.");
            }
            redoStack.push(command);
        }

        LOG.fine(() -> "Zustand von '" + target.getName() + "': " + target.getInhabitants());
    }

    public void redo(T target) {
        LOG.info("redo aufgerufen");

        if (redoStack.isEmpty()) {
            LOG.warning("redo nicht ausgeführt: Es liegt kein Kommando auf dem redoStack.");
        } else {
            Command<? super T> command = redoStack.pop();
            if (!command.execute(target)) {
                LOG.warning("redo für Kommando '" + command.description() + "' ist fehlgeschlagen.");
            }
            undoStack.push(command);
        }

        LOG.fine(() -> "Zustand von '" + target.getName() + "': " + target.getInhabitants());
    }
}
