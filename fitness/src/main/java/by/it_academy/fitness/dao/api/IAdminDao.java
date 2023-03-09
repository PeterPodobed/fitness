package by.it_academy.fitness.dao.api;

import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.core.dto.users.UserDto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//@Repository
public interface IAdminDao extends ListCrudRepository<UserEntity, UUID> {

    boolean deleteAllByUuid (UUID uuid);

    List<UserEntity> findByUuid(UUID uuid);

//    List<UserEntity> findAll();


}
