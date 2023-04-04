package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.users.UserDetailsDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public UserDto getUser(){
        return (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
