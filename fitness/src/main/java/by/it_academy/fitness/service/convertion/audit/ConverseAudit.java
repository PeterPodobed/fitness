package by.it_academy.fitness.service.convertion.audit;

import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;
import by.it_academy.fitness.core.dto.audit.UserAuditDto;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.audit.AuditEntity;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.service.convertion.audit.api.IConverseAudit;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ConverseAudit implements IConverseAudit {
    private final IUserDao iUserDao;

    public ConverseAudit(IUserDao iUserDao) {
        this.iUserDao = iUserDao;
    }

    @Override
    public AuditEntity convertDtoToAuditEntity(AuditDto auditDto) {

        return new AuditEntity(UUID.randomUUID(),
                LocalDateTime.now(),
                auditDto.getUser(),
                auditDto.getText(),
                auditDto.getType(),
                auditDto.getId());
    }

    @Override
    public AuditDtoOut convertEntityToDto(AuditEntity auditEntity) {
        UserEntity userEntity = iUserDao.findByUuid(auditEntity.getExamUser());
        Map<String, Object> user = new HashMap<>();
        user.put("uuid", userEntity.getUuid());
        user.put("mail", userEntity.getMail());
        user.put("fio", userEntity.getFio());
        user.put("role", userEntity.getRole().getRole());

        return new AuditDtoOut(auditEntity.getUuid(),
                auditEntity.getDt_create(),
                user,
                auditEntity.getText(),
                auditEntity.getType(),
                auditEntity.getId()
        );
    }

}
