package co.edu.ufps.compras.estado;

public interface PedidoState {
    String name();
    default PedidoState pay() { throw new IllegalStateException("Pago no permitido desde " + name()); }
    default PedidoState ship() { throw new IllegalStateException("Envío no permitido desde " + name()); }
    default PedidoState deliver() { throw new IllegalStateException("Entrega no permitida desde " + name()); }
    default PedidoState cancel() { throw new IllegalStateException("Cancelación no permitida desde " + name()); }
}
