package by.it_academy.fitness.web.controllers;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;
import by.it_academy.fitness.service.audit.api.IAuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private IAuditService iAuditService;

    public AuditController(IAuditService iAuditService) {
        this.iAuditService = iAuditService;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<PageDto<AuditDtoOut>> getPage(
            @RequestParam(name = "number", required = false, defaultValue = "0") int number,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(iAuditService.getPage(number, size));
    }

    @GetMapping(path = "/{uuid}")
    protected ResponseEntity<?> auditUuid(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(iAuditService.get(uuid));
    }
}
