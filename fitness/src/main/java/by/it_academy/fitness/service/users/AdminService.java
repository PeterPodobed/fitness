package by.it_academy.fitness.service.users;

import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.api.IAdminDao;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.dao.entity.users.UserRoleEntity;
import by.it_academy.fitness.dao.entity.users.UserStatusEntity;
import by.it_academy.fitness.service.users.api.IAdminService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@Service
public class AdminService implements IAdminService {
    private final IAdminDao dao;

    public AdminService(IAdminDao dao) {
        this.dao = dao;
    }

    @Override
    public List<UserEntity> get(UUID uuid) {
        return dao.findByUuid(uuid);
    }

    @Override
    public List<UserEntity> findAll() {
        return dao.findAll();
    }


    @Override
    public void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user) throws MultipleErrorResponse, SingleErrorResponse {
        UserEntity userEntity = dao.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("NoSuchElement", "unknown uuid"));
        if (dt_update != userEntity.getDtUpdate()) {
            throw new SingleErrorResponse("error", "user has already been updated");
        }
        userEntity.setMail(user.getMail());
        userEntity.setFio(user.getFio());
        userEntity.setRole(new UserRoleEntity(user.getRole()));
        userEntity.setStatus(new UserStatusEntity(user.getStatus()));
        userEntity.setPassword(user.getPassword());
        dao.save(userEntity);

    }

    @Override
    public boolean delete(UUID uuid) {
        return dao.deleteAllByUuid(uuid);
    }
}
