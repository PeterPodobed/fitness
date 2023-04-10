package by.it_academy.fitness.service.users;


import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.audit.AuditData;
import by.it_academy.fitness.core.dto.audit.enums.EssenceType;
import by.it_academy.fitness.core.dto.users.*;
import by.it_academy.fitness.core.dto.users.enums.UserStatus;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.core.exception.UserMessage;
import by.it_academy.fitness.dao.api.IAuditDao;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.users.UserEntity;

import by.it_academy.fitness.dao.entity.users.UserRoleEntity;
import by.it_academy.fitness.dao.entity.users.UserStatusEntity;
import by.it_academy.fitness.service.audit.api.IAuditService;
import by.it_academy.fitness.service.convertion.users.IDtoToUserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.mail.MailService;
import by.it_academy.fitness.service.users.api.IUserService;
import by.it_academy.fitness.web.controllers.filter.JwtFilter;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.time.LocalDateTime;
import java.util.*;

//@Service
public class UserService implements IUserService {

    private final IUserDao iUserDao;
    private final IUserEntityToDto iUserEntityToDto;
    private final PasswordEncoder encoder;
    private final IDtoToUserEntity iDtoToUserEntity;
    private final JwtFilter jwtFilter;
    private final IAuditService iAuditService;

    private String type = String.valueOf(EssenceType.USER);

    public UserService(IUserDao iUserDao, IUserEntityToDto iUserEntityToDto, PasswordEncoder encoder,
                       IDtoToUserEntity iDtoToUserEntity,
                       JwtFilter jwtFilter, IAuditService iAuditService) {
        this.iUserDao = iUserDao;
        this.iUserEntityToDto = iUserEntityToDto;
        this.encoder = encoder;
        this.iDtoToUserEntity = iDtoToUserEntity;
        this.jwtFilter = jwtFilter;
        this.iAuditService = iAuditService;
    }

    @Override
    public UserDto getCart(UUID id) throws MultipleErrorResponse {
        Optional<UserEntity> findUserEntity = iUserDao.findById(id);
        UserEntity userEntity = findUserEntity.get();
        return iUserEntityToDto.convertUserEntityToDto(userEntity);
    }

    @Override
    public boolean create(UserRegistrationDto userRegistration) throws MultipleErrorResponse {
        MailService mailService = new MailService();
        Optional<UserEntity> userEntity = iUserDao.findByMail(userRegistration.getMail());
        if (userEntity.isEmpty()) {
            UserEntity entity = iDtoToUserEntity.convertDtoToEntityByUser(userRegistration);
            mailService.sendSimpleMessage(entity.getMail(), "Verification code", entity.getVerificationCode());
//            String mail = JwtTokenUtil.getUsername(jwtFilter.getToken());
            iUserDao.save(entity);

            AuditData auditData = new AuditData(
                    userRegistration.getMail(),
                    "Создана запись в журнале регистрации",
                    type,
                    null
            );
            iAuditService.createReport(auditData);
            return true;
        } else throw new UserMessage("Пользователь с таким email уже зарегистрирован");

    }

    @Override
    public UserDetailsDto login(UserLoginDto userLoginDto) {
        UserEntity userEntity = findMail(userLoginDto.getMail());
        if (userEntity == null) {
            throw new UserMessage("Пользователь отсутствует");
        }
        if (!encoder.matches(userLoginDto.getPassword(), userEntity.getPassword())) {
            throw new UserMessage("Введите правильный пароль");
        }
        return iUserEntityToDto.convertUserEntityToDtoDetails(userEntity);
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
    public void update(UUID uuid, LocalDateTime dt_update, UserCreateDto user)
            throws SingleErrorResponse, MultipleErrorResponse {
        UserEntity userEntity = iUserDao.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("NoSuchElement", "Неизвестный uuid"));
        if (userEntity.getDtUpdate().equals(dt_update)) {
            userEntity.setDtUpdate(LocalDateTime.now());
            userEntity.setMail(user.getMail());
            userEntity.setFio(user.getFio());
            userEntity.setRole(new UserRoleEntity(user.getRole()));
            userEntity.setStatus(new UserStatusEntity(user.getStatus()));
            String mail = JwtTokenUtil.getUsername(jwtFilter.getToken());
            AuditData auditData = new AuditData(
                    mail,
                    "Обновлена запись в журнале пользователей",
                    type,
                    uuid
            );
            iUserDao.save(userEntity);
            iAuditService.createReport(auditData);
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

    @Override
    public UserDto findUserByMail(String mail) throws MultipleErrorResponse {
        Optional<UserEntity> findUserEntity = iUserDao.findByMail(mail);
        UserEntity user = findUserEntity.get();
        return iUserEntityToDto.convertUserEntityToDto(user);
    }
}
