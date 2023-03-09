package by.it_academy.fitness.service.convertion.users;

import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.dto.users.UserRegistrationDto;
import by.it_academy.fitness.core.dto.users.enums.UserRole;
import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.dao.entity.users.UserRoleEntity;
import by.it_academy.fitness.dao.entity.users.UserStatusEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DtoToUserEntity implements IDtoToUserEntity {
    public UserEntity convertDtoToEntityByUser(UserRegistrationDto userRegistrationDTO) {
        return new UserEntity(UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                userRegistrationDTO.getMail(),
                userRegistrationDTO.getFio(),
                new UserRoleEntity(UserRole.USER),
                new UserStatusEntity(UserStatus.WAITING_ACTIVATION),
                (int)(Math.random() * 10000),
                userRegistrationDTO.getPassword()
        );
    }

    public UserEntity convertDtoToEntityByAdmin(UserCreateDto userCreateDTO) {
        return new UserEntity(UUID.randomUUID(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                userCreateDTO.getMail(),
                userCreateDTO.getFio(),
                new UserRoleEntity(userCreateDTO.getRole()),
                new UserStatusEntity(userCreateDTO.getStatus()),
                userCreateDTO.getPassword()
        );
    }
}
