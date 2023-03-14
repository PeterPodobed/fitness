package by.it_academy.fitness.service.users;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.dto.users.UserLoginDto;
import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.core.exception.UserMessage;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.core.dto.users.UserRegistrationDto;

import by.it_academy.fitness.dao.entity.users.UserRoleEntity;
import by.it_academy.fitness.dao.entity.users.UserStatusEntity;
import by.it_academy.fitness.service.convertion.users.IDtoToUserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.mail.MailService;
import by.it_academy.fitness.service.users.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Service
public class UserService implements IUserService {

    private final IUserDao iUserDao;
    private final IUserEntityToDto iUserEntityToDto;

    private final IDtoToUserEntity iDtoToUserEntity;

    public UserService(IUserDao iUserDao, IUserEntityToDto iUserEntityToDto, IDtoToUserEntity iDtoToUserEntity) {
        this.iUserDao = iUserDao;
        this.iUserEntityToDto = iUserEntityToDto;
        this.iDtoToUserEntity = iDtoToUserEntity;
    }

    @Override
    public UserDto getCart(UUID id) throws MultipleErrorResponse {
        Optional<UserEntity> findUserEntity = iUserDao.findById(id);
        UserEntity userEntity = findUserEntity.get();
        return iUserEntityToDto.convertUserEntityToDto(userEntity);
    }

    @Override
    public boolean create(UserRegistrationDto userRegistration) {
        MailService mailService = new MailService();
        Optional<UserEntity> userEntity = iUserDao.findByMail(userRegistration.getMail());
        if (userEntity.isEmpty()) {
            UserEntity entity = iDtoToUserEntity.convertDtoToEntityByUser(userRegistration);
            iUserDao.save(entity);
//        mailService.sendSimpleMessage(entity.getMail(), "Verification code", entity.getVerificationCode());
            return true;
        } else throw new UserMessage("Пользователь с таким email уже зарегистрирован");

    }

    @Override
    public UserLoginDto login(UserLoginDto userLoginDto) {
        UserEntity userEntity = findMail(userLoginDto.getMail());
        if (userEntity.getMail().equals(userLoginDto.getMail()) && userEntity.getPassword().equals(userLoginDto.getPassword())) {
            throw new UserMessage("Вход выполнен");
        } else {
            throw new UserMessage("Пользователь отсутствует или введены некорректные данные");
        }
    }

    @Override
    public void verification(String code, String mail) {
        UserEntity userEntity = findMail(mail);
        if (code.equals(userEntity.getVerificationCode())) {
            userEntity.setStatus(new UserStatusEntity(UserStatus.ACTIVATED));
            iUserDao.save(userEntity);
            throw new UserMessage("Пользователь активирован");
        } else throw new UserMessage("Пользователь не активирован");
    }

    @Override
    public PageDto<UserDto> getPage(int number, int size) throws MultipleErrorResponse {
        Pageable page = PageRequest.of(number, size);
        Page<UserEntity> userEntityPage = iUserDao.findAll(page);
        List<UserDto> listDto = new ArrayList<>();
        for (UserEntity entity : userEntityPage) {
            UserDto userDto = iUserEntityToDto.convertUserEntityToDto(entity);
            listDto.add(userDto);
        }
        return new PageDto<>(userEntityPage.getNumber(), userEntityPage.getSize(),
                userEntityPage.getTotalPages(), userEntityPage.getTotalElements(),
                userEntityPage.isFirst(), userEntityPage.getNumberOfElements(),
                userEntityPage.isLast(), listDto);
    }

    @Override
    public void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user) throws SingleErrorResponse {
        UserEntity userEntity = iUserDao.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("NoSuchElement", "Неизвестный uuid"));
        if (userEntity.getDtUpdate().equals(dt_update)) {
            userEntity.setDtUpdate(LocalDateTime.now());
            userEntity.setMail(user.getMail());
            userEntity.setFio(user.getFio());
            userEntity.setRole(new UserRoleEntity(user.getRole()));
            userEntity.setStatus(new UserStatusEntity(user.getStatus()));
            iUserDao.save(userEntity);
        } else {
            throw new SingleErrorResponse("error", "Пользователь уже обновлен");
        }


    }

    @Override
    public UserDto get(UUID uuid) throws MultipleErrorResponse {
        Optional<UserEntity> findUserEntity = iUserDao.findById(uuid);
        UserEntity userEntity = findUserEntity.get();
        return iUserEntityToDto.convertUserEntityToDto(userEntity);
    }

    private UserEntity findMail(String mail) {
        return iUserDao.findByMail(mail)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким email не зарегистрирован"));
    }

}
