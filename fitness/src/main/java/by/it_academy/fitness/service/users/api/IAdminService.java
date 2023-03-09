package by.it_academy.fitness.service.users.api;

import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.core.dto.users.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAdminService {
    List<UserEntity> get(UUID uuid);

    List<UserEntity> findAll();

    void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user) throws MultipleErrorResponse, SingleErrorResponse;

    boolean delete(UUID uuid);
}
