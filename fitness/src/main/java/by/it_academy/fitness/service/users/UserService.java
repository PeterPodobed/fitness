package by.it_academy.fitness.service.users;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.dto.users.UserLoginDto;
import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
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
        UserEntity entity = iDtoToUserEntity.convertDtoToEntityByUser(userRegistration);
        iUserDao.save(entity);
//        mailService.sendSimpleMessage(entity.getMail(), "Verification code", String.valueOf(entity.getVerificationCode()));
        return true;
    }

    @Override
    public UserLoginDto login(UserLoginDto userLoginDto) {
        UserEntity userEntity = iUserDao.findByMail(userLoginDto.getMail());
        if (userEntity.getMail().equals(userLoginDto.getMail()) && userEntity.getPassword().equals(userLoginDto.getPassword())) {
            System.out.println("Вход выполнен");
        } else {
            throw new IllegalArgumentException("Пользователь отсутствует или введены некорректные данные");
        }
        return null;
    }

    @Override
    public boolean verification(int verificationCode, String mail) {
        UserEntity userEntity = iUserDao.findByMail(mail);
        if (userEntity.getMail().equals(mail) && userEntity.getVerificationCode() == verificationCode) {
            userEntity.setStatus(new UserStatusEntity(UserStatus.ACTIVATED));
            iUserDao.save(userEntity);
            System.out.println("User is activated");
            return true;
        }
        System.out.println("User is not activated");
        return false;
    }

    @Override
    public PageDto<UserDto> getPage(int number, int size) throws MultipleErrorResponse {
        Pageable page = PageRequest.of(number, size);
        Page<UserEntity> userEntityPage = iUserDao.findAll(page);
        List<UserDto> listDto = new ArrayList<>();
        for (UserEntity entity: userEntityPage) {
            UserDto userDto = iUserEntityToDto.convertUserEntityToDto(entity);
            listDto.add(userDto);
        }
        return new PageDto<>(userEntityPage.getNumber(), userEntityPage.getSize(),
                userEntityPage.getTotalPages(), userEntityPage.getTotalElements(),
                userEntityPage.isFirst(), userEntityPage.getNumberOfElements(),
                userEntityPage.isLast(), listDto );
    }

    @Override
    public void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user) throws MultipleErrorResponse, SingleErrorResponse {
        user.validate();
        UserEntity userEntity = iUserDao.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("NoSuchElement", "unknown uuid"));
        if (dt_update != userEntity.getDtUpdate()) {
            throw new SingleErrorResponse("error", "user has already been updated");
        }
        userEntity.setMail(user.getMail());
        userEntity.setFio(user.getFio());
        userEntity.setRole(new UserRoleEntity(user.getRole()));
        userEntity.setStatus(new UserStatusEntity(user.getStatus()));
        userEntity.setPassword(user.getPassword());
        iUserDao.save(userEntity);
    }

    @Override
    public boolean delete(UUID uuid) {
        return iUserDao.deleteAllByUuid(uuid);
    }

    @Override
    public List<UserEntity> get(UUID uuid) {
        return iUserDao.findByUuid(uuid);
    }


}
