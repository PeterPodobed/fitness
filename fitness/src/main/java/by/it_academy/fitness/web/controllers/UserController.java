package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.users.*;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.service.UserHolder;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.users.api.IUserService;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService iUserService;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final IUserEntityToDto iUserEntityToDto;
    private final UserDetailsManager userManager;
    private final IUserDao iUserDao;


    public UserController(IUserService iUserService, PasswordEncoder encoder, JwtTokenUtil jwtTokenUtil,
                          IUserEntityToDto iUserEntityToDto, UserDetailsManager userManager, IUserDao iUserDao) {
        this.iUserService = iUserService;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.iUserEntityToDto = iUserEntityToDto;
        this.userManager = userManager;
        this.iUserDao = iUserDao;
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    protected ResponseEntity<?> createUser(@RequestBody UserRegistrationDto userRegistration) {
        if (iUserService.create(userRegistration)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<PageDto<UserDto>> getPage(
            @RequestParam(name = "number", required = false, defaultValue = "0") int number,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size)
            throws MultipleErrorResponse {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.getPage(number, size));
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    protected ResponseEntity<?> getCart() {
        UserHolder userHolder = new UserHolder();
        return ResponseEntity.status(HttpStatus.OK).body(userHolder.getUser());
    }

    @RequestMapping(path = "/verification", method = RequestMethod.GET)
    protected ResponseEntity<?> verification(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "mail") String mail) {
        iUserService.verification(code, mail);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/{uuid}")
    protected ResponseEntity<UserDto> get(@PathVariable("uuid") UUID uuid) throws SingleErrorResponse, MultipleErrorResponse {
        return ResponseEntity.status(HttpStatus.OK).body(iUserService.get(uuid));
    }

    @PutMapping(path = "/{uuid}/dt_update/{dt_update}")
    protected ResponseEntity<?> update(
            @PathVariable("uuid") UUID uuid,
            @PathVariable("dt_update") LocalDateTime dt_update,
            @RequestBody UserCreateDto user)
            throws SingleErrorResponse, MultipleErrorResponse {
        iUserService.update(uuid, dt_update, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        UserDetailsDto details = iUserService.login(userLoginDto);
        Map<String, Object> claim = jwtTokenUtil.getClaim(details);
        return ResponseEntity.status(HttpStatus.OK).body(JwtTokenUtil.generateAccessToken(claim, details));

    }
}
