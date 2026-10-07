package co.edu.ufps.compras.commands;

public class ReserveBudgetCommand implements UndoableCommand {
    private final String requestId, department;
    private final long amount;
    private final BudgetLedger ledger;
    private boolean executed;
    public ReserveBudgetCommand(String requestId, String department, long amount, BudgetLedger ledger) {
        this.requestId = requestId; this.department = department; this.amount = amount; this.ledger = ledger;
        if (amount <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
    }
    public String requestId() { return requestId; }
    public String description() { return "Reservar " + amount + " para " + department; }
    public void execute() { if (executed) throw new IllegalStateException("Comando ya ejecutado"); ledger.adjust(department, -amount); executed = true; }
    public void undo() { if (!executed) throw new IllegalStateException("Comando no ejecutado"); ledger.adjust(department, amount); executed = false; }
}
