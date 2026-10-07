package co.edu.ufps.compras.commands;

public interface UndoableCommand {
    String requestId();
    String description();
    void execute();
    void undo();
}
