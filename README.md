# ComprasUDES · Unidad 4

Proyecto Spring Boot que implementa cuatro patrones de comportamiento para el flujo de compras. Java 17 y Spring Boot 3.5.16.

## Ejecución

```bash
mvn spring-boot:run
mvn test
```

`POST /api/aprobaciones/evaluar` recibe `{"requestId":"R-1","amount":12000000,"supplierType":"INTERNATIONAL"}` y devuelve los aprobadores requeridos en orden.

## Decisiones de diseño

| Necesidad | Patrón | Decisión y alternativa descartada |
|---|---|---|
| Aprobación por umbral y control adicional para proveedor internacional | Chain of Responsibility | Compliance se inserta antes de Supervisor y Director solo para compras internacionales; la secuencia incorpora niveles por monto. Command no enruta una solicitud: encapsula acciones ejecutables y reversibles. |
| Reservar presupuesto y registrar un pedido, ambos reversibles | Command | Cada comando implementa `execute` y `undo`; `CommandHistory` conserva la lista cronológica por solicitud. Llamadas directas perderían historial y reversión individual. |
| Notificar cambios a correo, tablero y auditoría | Observer | `OrderEventPublisher` permite suscribir y retirar observadores sin codificar destinatarios en el emisor. |
| Operaciones válidas según ciclo de vida | State | Los objetos Pending, Paid, Shipped, Delivered y Cancelled permiten solo transiciones legales. Strategy cambia un algoritmo; no expresa transiciones persistentes. |

El undo solo revierte el último comando de cada solicitud, respetando el orden causal. Un cambio inválido en State no publica evento. El README y las pruebas describen los límites de cada patrón.

## Pruebas

`BehavioralPatternsTest` cubre cadena condicional, historial y undo, suscripción/desuscripción, transición de estado y rechazo de una transición inválida.
