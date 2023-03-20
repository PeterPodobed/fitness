package by.it_academy.fitness.service.audit;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditDto;
import by.it_academy.fitness.core.dto.audit.AuditDtoOut;
import by.it_academy.fitness.dao.api.IAuditDao;
import by.it_academy.fitness.dao.entity.audit.AuditEntity;
import by.it_academy.fitness.service.audit.api.IAuditService;
import by.it_academy.fitness.service.convertion.audit.api.IConverseAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AuditService implements IAuditService {
    private IAuditDao iAuditDao;
    private IConverseAudit iConverseAudit;

    public AuditService(IAuditDao iAuditDao, IConverseAudit iAuditDtoToAuditEntity) {
        this.iAuditDao = iAuditDao;
        this.iConverseAudit = iAuditDtoToAuditEntity;
    }

    @Override
    public boolean createReport(AuditDto auditDto) {
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
}
