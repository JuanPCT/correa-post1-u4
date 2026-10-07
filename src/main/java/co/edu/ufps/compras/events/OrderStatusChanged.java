package co.edu.ufps.compras.events;

import java.time.Instant;

public record OrderStatusChanged(String orderId, String previous, String current, Instant at) { }
