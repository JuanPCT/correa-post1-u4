package co.edu.ufps.compras.commands;

public class RegisterOrderCommand implements UndoableCommand {
    private final String requestId, supplier;
    private final PurchaseOrderStore store;
    private boolean executed;
    public RegisterOrderCommand(String requestId, String supplier, PurchaseOrderStore store) {
        this.requestId = requestId; this.supplier = supplier; this.store = store;
    }
    public String requestId() { return requestId; }
    public String description() { return "Registrar pedido de " + supplier; }
    public void execute() { if (executed) throw new IllegalStateException("Comando ya ejecutado"); store.save(requestId, supplier); executed = true; }
    public void undo() { if (!executed) throw new IllegalStateException("Comando no ejecutado"); store.remove(requestId); executed = false; }
}
