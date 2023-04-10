package by.it_academy.fitness.service.audit.api;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditData;
import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;


import java.util.UUID;

public interface IAuditService {
    boolean createReport(AuditData auditData) throws MultipleErrorResponse;

    PageDto<AuditDtoOut> getPage(int number, int size);

    AuditDtoOut get(UUID uuid);

    UserDto findUserByMail (String mail) throws MultipleErrorResponse;

}
