package by.it_academy.fitness.service.users.api;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.users.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {

    UserDto getCart(UUID id) throws MultipleErrorResponse;

    boolean create(UserRegistrationDto userRegistration) throws MultipleErrorResponse;

    UserDetailsDto login (UserLoginDto userLoginDto);

    void verification(String verificationCode, String mail);

    PageDto<UserDto> getPage (int number, int size) throws MultipleErrorResponse;

    void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user) throws MultipleErrorResponse, SingleErrorResponse;

    UserDto get(UUID uuid) throws MultipleErrorResponse;

    UserDto findUserByMail (String mail) throws MultipleErrorResponse;

}
