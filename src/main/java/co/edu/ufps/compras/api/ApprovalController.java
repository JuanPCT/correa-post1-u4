package co.edu.ufps.compras.api;

import co.edu.ufps.compras.aprobacion.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aprobaciones")
public class ApprovalController {
    private final ApprovalService service;
    public ApprovalController(ApprovalService service) { this.service = service; }
    @PostMapping("/evaluar") public ApprovalResult evaluate(@RequestBody ApprovalRequest request) { return service.evaluate(request); }
}
