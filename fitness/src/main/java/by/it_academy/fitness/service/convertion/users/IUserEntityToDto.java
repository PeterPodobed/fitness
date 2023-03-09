package by.it_academy.fitness.service.convertion.users;

import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.users.UserEntity;

public interface IUserEntityToDto {

    UserDto convertUserEntityToDto(UserEntity userEntity) throws MultipleErrorResponse;
}
