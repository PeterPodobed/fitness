package by.it_academy.fitness.service.users.api;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.dto.users.UserLoginDto;
import by.it_academy.fitness.core.dto.users.UserRegistrationDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.entity.users.UserEntity;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserDto getCart(UUID id) throws MultipleErrorResponse;

    boolean create(UserRegistrationDto userRegistration);

    UserLoginDto login (UserLoginDto userLoginDto);

    boolean verification(int verificationCode, String mail);

    PageDto<UserDto> getPage (int number, int size) throws MultipleErrorResponse;

    void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user) throws MultipleErrorResponse, SingleErrorResponse;

    boolean delete(UUID uuid);

    List<UserEntity> get(UUID uuid);

}
