package co.edu.ufps.compras.aprobacion;

abstract class ApprovalHandler {
    private ApprovalHandler next;
    public ApprovalHandler then(ApprovalHandler handler) { next = handler; return handler; }
    final void handle(ApprovalRequest request, java.util.List<String> required) {
        apply(request, required);
        if (next != null) next.handle(request, required);
    }
    protected abstract void apply(ApprovalRequest request, java.util.List<String> required);
}
