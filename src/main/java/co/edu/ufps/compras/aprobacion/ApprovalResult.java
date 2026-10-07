package co.edu.ufps.compras.aprobacion;

import java.util.List;

public record ApprovalResult(String requestId, List<String> requiredApprovers) {
    public ApprovalResult { requiredApprovers = List.copyOf(requiredApprovers); }
}
