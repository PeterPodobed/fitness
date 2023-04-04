package by.it_academy.fitness.service.convertion.users;

import by.it_academy.fitness.core.dto.users.UserDetailsDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToDto implements IUserEntityToDto {

    @Override
    public UserDto convertUserEntityToDto(UserEntity userEntity) throws MultipleErrorResponse {
        return new UserDto(userEntity.getUuid(),
                userEntity.getDtCreate(),
                userEntity.getDtUpdate(),
                userEntity.getMail(),
                userEntity.getFio(),
                userEntity.getRole().getRole(),
                userEntity.getStatus().getStatus()
        );
    }

    @Override
    public UserDetailsDto convertUserEntityToDtoDetails(UserEntity userEntity) {
        return new UserDetailsDto(userEntity.getUuid(),
                userEntity.getDtCreate(),
                userEntity.getDtUpdate(),
                userEntity.getMail(),
                userEntity.getFio(),
                userEntity.getRole().getRole(),
                userEntity.getStatus().getStatus(),
                userEntity.getPassword()
        );
    }

    @Override
    public UserDto convertUserEntityToDtoToken(UserEntity userEntity) throws MultipleErrorResponse {
        return new UserDto(userEntity.getUuid(),
                userEntity.getDtCreate(),
                userEntity.getDtUpdate(),
                userEntity.getFio(),
                userEntity.getRole().getRole(),
                userEntity.getStatus().getStatus()
        );    }
}
