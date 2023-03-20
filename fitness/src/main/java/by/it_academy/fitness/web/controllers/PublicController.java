package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.users.UserDetailsDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.dto.users.UserLoginDto;
import by.it_academy.fitness.dao.api.IUserDao;
import by.it_academy.fitness.dao.entity.users.UserEntity;
import by.it_academy.fitness.service.convertion.users.IUserEntityToDto;
import by.it_academy.fitness.service.users.api.IUserService;
import by.it_academy.fitness.web.controllers.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final UserDetailsManager userManager;
    private final PasswordEncoder encoder;
    private final IUserService iUserService;
    private final IUserDao iUserDao;
    private final JwtTokenUtil jwtTokenUtil;
    private final IUserEntityToDto iUserEntityToDto;


    public PublicController(UserDetailsManager userManager, PasswordEncoder encoder,
                            IUserService iUserService, IUserDao iUserDao, JwtTokenUtil jwtTokenUtil,
                            IUserEntityToDto iUserEntityToDto) {
        this.userManager = userManager;
        this.encoder = encoder;
        this.iUserService = iUserService;
        this.iUserDao = iUserDao;
        this.jwtTokenUtil = jwtTokenUtil;
        this.iUserEntityToDto = iUserEntityToDto;
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(@RequestBody UserLoginDto userLoginDto) {
//        iUserService.login(userLoginDto);
//        UserDetails details = userManager.loadUserByUsername(userLoginDto.getMail());
//        Optional<UserEntity> userEntity = iUserDao.findByMail(userLoginDto.getMail());
//        UserDetailsDto userDto = iUserEntityToDto.convertUserEntityToDtoDetails(userEntity.get());
//        Map<String, Object> claim = jwtTokenUtil.getClaim(userDto);
//        return String.valueOf(ResponseEntity.ok(JwtTokenUtil.generateAccessToken(claim, details)));

//        UserDetailsDto login = iUserService.login(userLoginDto);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(JwtTokenUtil.generateAccessToken(login));
//    }
}
