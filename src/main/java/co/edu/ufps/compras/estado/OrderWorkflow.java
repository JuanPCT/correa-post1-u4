package co.edu.ufps.compras.estado;

import co.edu.ufps.compras.events.OrderEventPublisher;

public class OrderWorkflow {
    private final String id;
    private final OrderEventPublisher events;
    private PedidoState state = new Pending();
    public OrderWorkflow(String id, OrderEventPublisher events) { this.id = id; this.events = events; }
    public String state() { return state.name(); }
    public void pay() { transition(state.pay()); }
    public void ship() { transition(state.ship()); }
    public void deliver() { transition(state.deliver()); }
    public void cancel() { transition(state.cancel()); }
    private void transition(PedidoState next) { String previous = state.name(); state = next; events.publish(id, previous, state.name()); }
    private record Pending() implements PedidoState {
        public String name() { return "PENDING"; }
        public PedidoState pay() { return new Paid(); }
        public PedidoState cancel() { return new Cancelled(); }
    }
    private record Paid() implements PedidoState {
        public String name() { return "PAID"; }
        public PedidoState ship() { return new Shipped(); }
        public PedidoState cancel() { return new Cancelled(); }
    }
    private record Shipped() implements PedidoState {
        public String name() { return "SHIPPED"; }
        public PedidoState deliver() { return new Delivered(); }
    }
    private record Delivered() implements PedidoState { public String name() { return "DELIVERED"; } }
    private record Cancelled() implements PedidoState { public String name() { return "CANCELLED"; } }
}
