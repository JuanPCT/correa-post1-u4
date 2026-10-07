package co.edu.ufps.compras.events;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final List<OrderStatusObserver> observers = new CopyOnWriteArrayList<>();
    public void subscribe(OrderStatusObserver observer) { observers.addIfAbsent(observer); }
    public void unsubscribe(OrderStatusObserver observer) { observers.remove(observer); }
    public void publish(String id, String previous, String current) {
        var event = new OrderStatusChanged(id, previous, current, Instant.now());
        observers.forEach(observer -> observer.onStatusChanged(event));
    }
}
