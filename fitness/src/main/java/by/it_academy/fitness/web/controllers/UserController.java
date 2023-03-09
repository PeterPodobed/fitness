package by.it_academy.fitness.web.controllers;

import by.it_academy.fitness.core.dto.PageDto;
import by.it_academy.fitness.core.dto.products.ProductDto;
import by.it_academy.fitness.core.dto.users.UserCreateDto;
import by.it_academy.fitness.core.dto.users.UserDto;
import by.it_academy.fitness.core.dto.users.UserRegistrationDto;
import by.it_academy.fitness.core.exception.MultipleErrorResponse;
import by.it_academy.fitness.core.exception.SingleErrorResponse;
import by.it_academy.fitness.service.users.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<?> doPost(@RequestBody UserRegistrationDto userRegistration) {
        if (service.create(userRegistration)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getCart(@PathVariable("me") UUID uuid) throws MultipleErrorResponse {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCart(uuid));
    }

    @RequestMapping(path = "/verification", method = RequestMethod.GET)
    public ResponseEntity<?> verification(@PathVariable("verificationCode") int verificationCode,
                                          @PathVariable("mail") String mail) {
        if (service.verification(verificationCode, mail)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(path = "/{uuid}")
    protected ResponseEntity<UserDto> get(@PathVariable("uuid") UUID uuid) throws SingleErrorResponse {
        service.get(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "{uuid}/dt_update/{dt_update}")
    protected ResponseEntity<?> update(
            @PathVariable("uuid") UUID uuid,
            @PathVariable("dt_update") LocalDateTime dt_update,
            @RequestBody UserCreateDto user)
            throws SingleErrorResponse, MultipleErrorResponse {
        service.update(uuid, dt_update, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE)
    protected ResponseEntity<?> doDelete(@RequestBody UUID uuid){
        service.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PageDto<UserDto>> getPage(
            @RequestParam(name = "number", required = false, defaultValue = "0") int number,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size)
            throws MultipleErrorResponse {
        return ResponseEntity.status(HttpStatus.OK).body(service.getPage(number, size));
    }


}
