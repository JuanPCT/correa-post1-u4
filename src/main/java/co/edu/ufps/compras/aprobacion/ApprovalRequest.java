package co.edu.ufps.compras.aprobacion;

public record ApprovalRequest(String requestId, double amount, SupplierType supplierType) {
    public enum SupplierType { DOMESTIC, INTERNATIONAL }
    public ApprovalRequest {
        if (requestId == null || requestId.isBlank()) throw new IllegalArgumentException("requestId requerido");
        if (amount < 0) throw new IllegalArgumentException("El valor no puede ser negativo");
        if (supplierType == null) throw new IllegalArgumentException("Tipo de proveedor requerido");
    }
}
