package by.it_academy.fitness.service.convertion.audit.api;

import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;
import by.it_academy.fitness.dao.entity.audit.AuditEntity;

public interface IConverseAudit {

    AuditEntity convertDtoToAuditEntity(AuditDto auditDto);

    AuditDtoOut convertEntityToDto (AuditEntity auditEntity);

}
