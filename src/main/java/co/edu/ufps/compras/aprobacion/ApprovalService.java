package co.edu.ufps.compras.aprobacion;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {
    public ApprovalResult evaluate(ApprovalRequest request) {
        var required = new ArrayList<String>();
        ApprovalHandler chain = new SupervisorHandler();
        chain.then(new DirectorHandler());
        if (request.supplierType() == ApprovalRequest.SupplierType.INTERNATIONAL) {
            ApprovalHandler compliance = new ComplianceHandler();
            compliance.then(chain);
            chain = compliance;
        }
        chain.handle(request, required);
        return new ApprovalResult(request.requestId(), required);
    }
    private static final class ComplianceHandler extends ApprovalHandler {
        @Override protected void apply(ApprovalRequest r, List<String> required) {
            if (r.supplierType() == ApprovalRequest.SupplierType.INTERNATIONAL) required.add("COMPLIANCE");
        }
    }
    private static final class SupervisorHandler extends ApprovalHandler {
        @Override protected void apply(ApprovalRequest r, List<String> required) {
            if (r.amount() >= 1_000_000) required.add("SUPERVISOR");
        }
    }
    private static final class DirectorHandler extends ApprovalHandler {
        @Override protected void apply(ApprovalRequest r, List<String> required) {
            if (r.amount() >= 10_000_000) required.add("DIRECTOR");
        }
    }
}
