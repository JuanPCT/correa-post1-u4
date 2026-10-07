package co.edu.ufps.compras.events;

public interface OrderStatusObserver { void onStatusChanged(OrderStatusChanged event); }
