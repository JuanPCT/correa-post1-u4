package co.edu.ufps.compras.commands;

import java.util.HashMap;
import java.util.Map;

public class BudgetLedger {
    private final Map<String, Long> availableByDepartment = new HashMap<>();
    public void setAvailable(String department, long amount) { availableByDepartment.put(department, amount); }
    public long available(String department) { return availableByDepartment.getOrDefault(department, 0L); }
    void adjust(String department, long delta) {
        long next = available(department) + delta;
        if (next < 0) throw new IllegalStateException("Presupuesto insuficiente");
        availableByDepartment.put(department, next);
    }
}
