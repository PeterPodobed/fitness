package by.it_academy.fitness.service.audit;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditData;
import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.api.IAuditDao;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.audit.AuditEntity;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.service.audit.api.IAuditService;
import by.it_academy.fitness.service.convertion.audit.api.IConverseAudit;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.users.api.IUserService;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuditService implements IAuditService {
    private IAuditDao iAuditDao;
    private IConverseAudit iConverseAudit;
    private final IUserEntityToDto iUserEntityToDto;
    private final IUserDao iUserDao;

    public AuditService(IAuditDao iAuditDao, IConverseAudit iConverseAudit,
                        IUserEntityToDto iUserEntityToDto, IUserDao iUserDao) {
        this.iAuditDao = iAuditDao;
        this.iConverseAudit = iConverseAudit;
        this.iUserEntityToDto = iUserEntityToDto;
        this.iUserDao = iUserDao;
    }

    @Override
    public boolean createReport(AuditData auditData) throws MultipleErrorResponse {
        UUID uuid = findUserByMail(auditData.getMail()).getUuid();
        AuditDto auditDto = new AuditDto(
                UUID.randomUUID(),
                LocalDateTime.now(),
                uuid,
                auditData.getText(),
                auditData.getType(),
                auditData.getId()
        );
        AuditEntity entity = iConverseAudit.convertDtoToAuditEntity(auditDto);
        iAuditDao.save(entity);
        return true;
    }

    @Override
    public PageDto<AuditDtoOut> getPage(int number, int size) {
        Pageable page = PageRequest.of(number, size);
        Page<AuditEntity> auditEntityPage = iAuditDao.findAll(page);
        List<AuditDtoOut> listDto = new ArrayList<>();
        for (AuditEntity entity : auditEntityPage) {
            AuditDtoOut auditDto = iConverseAudit.convertEntityToDto(entity);
            listDto.add(auditDto);
        }
        return new PageDto<>(auditEntityPage.getNumber(), auditEntityPage.getSize(),
                auditEntityPage.getTotalPages(), auditEntityPage.getTotalElements(),
                auditEntityPage.isFirst(), auditEntityPage.getNumberOfElements(),
                auditEntityPage.isLast(), listDto);
    }

    @Override
    public AuditDtoOut get(UUID uuid) {
        Optional<AuditEntity> findAuditEntity = iAuditDao.findById(uuid);
        AuditEntity auditEntity = findAuditEntity.get();
        return iConverseAudit.convertEntityToDto(auditEntity);
    }

    @Override
    public UserDto findUserByMail(String mail) throws MultipleErrorResponse {
        Optional<UserEntity> findUserEntity = iUserDao.findByMail(mail);
        UserEntity user = findUserEntity.get();
        return iUserEntityToDto.convertUserEntityToDto(user);
    }
}
