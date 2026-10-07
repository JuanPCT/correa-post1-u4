package co.edu.ufps.compras.commands;

import java.time.Instant;
import java.util.*;

/** Historial consultable e independiente por solicitud; deshacer se limita al último comando. */
public class CommandHistory {
    private final Map<String, Deque<Entry>> entries = new HashMap<>();
    public synchronized void execute(UndoableCommand command) {
        command.execute();
        entries.computeIfAbsent(command.requestId(), ignored -> new ArrayDeque<>())
                .addLast(new Entry(command, Instant.now()));
    }
    public synchronized boolean undoLast(String requestId) {
        Deque<Entry> history = entries.get(requestId);
        if (history == null || history.isEmpty()) return false;
        history.removeLast().command().undo();
        return true;
    }
    public synchronized List<HistoryItem> history(String requestId) {
        return entries.getOrDefault(requestId, new ArrayDeque<>()).stream()
                .map(e -> new HistoryItem(e.command().description(), e.at())).toList();
    }
    private record Entry(UndoableCommand command, Instant at) { }
    public record HistoryItem(String description, Instant at) { }
}
