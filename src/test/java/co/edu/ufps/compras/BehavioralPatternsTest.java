package co.edu.ufps.compras;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import co.edu.ufps.compras.aprobacion.*;
import co.edu.ufps.compras.commands.*;
import co.edu.ufps.compras.events.*;
import co.edu.ufps.compras.estado.OrderWorkflow;

class BehavioralPatternsTest {
    @Test void chainAddsConditionalComplianceBeforeAmountLevels() {
        var result = new ApprovalService().evaluate(new ApprovalRequest("R1", 12_000_000,
                ApprovalRequest.SupplierType.INTERNATIONAL));
        assertEquals(List.of("COMPLIANCE", "SUPERVISOR", "DIRECTOR"), result.requiredApprovers());
        assertEquals(List.of(), new ApprovalService().evaluate(new ApprovalRequest("R2", 200_000,
                ApprovalRequest.SupplierType.DOMESTIC)).requiredApprovers());
    }
    @Test void commandsHaveOrderedPerRequestHistoryAndUndoEffects() {
        var ledger = new BudgetLedger(); ledger.setAvailable("IT", 10_000);
        var orders = new PurchaseOrderStore(); var history = new CommandHistory();
        history.execute(new ReserveBudgetCommand("R1", "IT", 4_000, ledger));
        history.execute(new RegisterOrderCommand("R1", "Acme", orders));
        assertEquals(2, history.history("R1").size());
        assertTrue(orders.exists("R1"));
        assertTrue(history.undoLast("R1")); assertFalse(orders.exists("R1"));
        assertTrue(history.undoLast("R1")); assertEquals(10_000, ledger.available("IT"));
    }
    @Test void observerBroadcastsToCurrentSubscribers() {
        var publisher = new OrderEventPublisher(); var events = new ArrayList<OrderStatusChanged>();
        OrderStatusObserver observer = events::add;
        publisher.subscribe(observer); publisher.publish("P1", "PENDING", "PAID");
        publisher.unsubscribe(observer); publisher.publish("P1", "PAID", "SHIPPED");
        assertEquals(1, events.size()); assertEquals("PAID", events.get(0).current());
    }
    @Test void stateControlsTransitionsAndNotifiesOnSuccessfulChange() {
        var publisher = new OrderEventPublisher(); var events = new ArrayList<OrderStatusChanged>();
        publisher.subscribe(events::add);
        var workflow = new OrderWorkflow("P1", publisher);
        workflow.pay(); workflow.ship(); workflow.deliver();
        assertEquals("DELIVERED", workflow.state()); assertEquals(3, events.size());
        assertThrows(IllegalStateException.class, workflow::cancel);
    }
}
