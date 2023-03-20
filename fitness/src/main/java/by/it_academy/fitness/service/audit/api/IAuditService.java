package by.it_academy.fitness.service.audit.api;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;


import java.util.UUID;

public interface IAuditService {
    boolean createReport(AuditDto auditDto);

    PageDto<AuditDtoOut> getPage(int number, int size);

    AuditDtoOut get(UUID uuid);
}
