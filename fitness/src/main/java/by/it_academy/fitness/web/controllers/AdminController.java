//package by.it_academy.fitness.web.controllers;
//
//import by.it_academy.fitness.core.dto.users.UserCreateDto;
//import by.it_academy.fitness.core.dto.users.UserDto;
//import by.it_academy.fitness.core.exception.MultipleErrorResponse;
//import by.it_academy.fitness.core.exception.SingleErrorResponse;
//import by.it_academy.fitness.dao.entity.users.UserEntity;
//import by.it_academy.fitness.service.users.api.IAdminService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/v1/users")
//public class AdminController {
//
//    private final IAdminService service;
//
//    public AdminController(IAdminService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public List<UserEntity> getList() {
//        return service.findAll();
//    }
//
//    @GetMapping(path = "/{uuid}")
//    protected ResponseEntity<UserDto> get(@PathVariable("uuid") UUID uuid) throws SingleErrorResponse {
//        service.get(uuid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PutMapping(path = "{uuid}/dt_update/{dt_update}")
//    protected ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
//                                    @PathVariable("dt_update") LocalDateTime dt_update,
//                                    @RequestBody UserCreateDto user) throws SingleErrorResponse, MultipleErrorResponse {
//        service.update(uuid, dt_update, user);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//    @RequestMapping(method = RequestMethod.DELETE)
//    protected ResponseEntity<?> doDelete(@RequestBody UUID uuid){
//        service.delete(uuid);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//}
