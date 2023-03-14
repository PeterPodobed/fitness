package by.it_academy.fitness.dao.api;


import by.it_academy.fitness.dao.entity.products.ProductEntity;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Repository
public interface IUserDao extends ListCrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByMail(String mail);

    Page<UserEntity> findAll(Pageable pageable);

    boolean deleteAllByUuid (UUID uuid);

    List<UserEntity> findByUuid(UUID uuid);
}
