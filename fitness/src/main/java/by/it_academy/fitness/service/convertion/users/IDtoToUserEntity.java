package by.it_academy.fitness.service.convertion.users;

import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.dto.users.UserRegistrationDto;
import by.it_academy.fitness.dao.entity.users.UserEntity;

public interface IDtoToUserEntity {

    UserEntity convertDtoToEntityByUser(UserRegistrationDto userRegistrationDTO);

    UserEntity convertDtoToEntityByAdmin(UserCreateDto userCreateDTO);

}
