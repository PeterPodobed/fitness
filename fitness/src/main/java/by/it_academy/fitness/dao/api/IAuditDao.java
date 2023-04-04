package by.it_academy.fitness.dao.api;

import by.it_academy.fitness.dao.entity.audit.AuditEntity;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface IAuditDao extends ListCrudRepository<AuditEntity, UUID> {

    Page<AuditEntity> findAll(Pageable pageable);


}
