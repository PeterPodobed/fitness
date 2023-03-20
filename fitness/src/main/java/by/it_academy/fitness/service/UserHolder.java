package by.it_academy.fitness.service;

import by.it_academy.fitness.core.dto.users.UserDetailsDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public UserDetailsDto getUser(){
        return (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
