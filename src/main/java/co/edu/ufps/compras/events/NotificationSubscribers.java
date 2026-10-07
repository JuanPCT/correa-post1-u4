package co.edu.ufps.compras.events;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscribers {
    @Component public static class EmailSubscriber implements OrderStatusObserver {
        private final List<OrderStatusChanged> received = new ArrayList<>();
        @Override public void onStatusChanged(OrderStatusChanged event) { received.add(event); }
        public List<OrderStatusChanged> received() { return List.copyOf(received); }
    }
    @Component public static class DashboardSubscriber implements OrderStatusObserver {
        private final List<OrderStatusChanged> received = new ArrayList<>();
        @Override public void onStatusChanged(OrderStatusChanged event) { received.add(event); }
        public List<OrderStatusChanged> received() { return List.copyOf(received); }
    }
    @Component public static class AuditSubscriber implements OrderStatusObserver {
        private final List<OrderStatusChanged> received = new ArrayList<>();
        @Override public void onStatusChanged(OrderStatusChanged event) { received.add(event); }
        public List<OrderStatusChanged> received() { return List.copyOf(received); }
    }
}
