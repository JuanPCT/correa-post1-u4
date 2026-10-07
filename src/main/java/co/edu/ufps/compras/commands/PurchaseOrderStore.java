package co.edu.ufps.compras.commands;

import java.util.HashMap;
import java.util.Map;

public class PurchaseOrderStore {
    private final Map<String, String> orders = new HashMap<>();
    public boolean exists(String requestId) { return orders.containsKey(requestId); }
    public String get(String requestId) { return orders.get(requestId); }
    void save(String requestId, String supplier) {
        if (orders.putIfAbsent(requestId, supplier) != null) throw new IllegalStateException("Pedido ya existe");
    }
    void remove(String requestId) { orders.remove(requestId); }
}
