package org.example.controller;

import org.example.controller.dto.UrlDto;
import org.example.controller.dto.UserDto;
import org.example.controller.dto.UserDtoResponse;
import org.example.exception.EntityNotFoundException;
import org.example.service.UserService;
import org.example.service.model.Url;
import org.example.service.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public UserDtoResponse addUser(@RequestBody UserDto userDto) {
        return new UserDtoResponse(userService.addUser(new User(userDto.name())), userDto.name());
    }

    @GetMapping(value = "/get/{id}")
    public UserDtoResponse getUser(@PathVariable("id") long id) throws EntityNotFoundException {
        User res = userService.findUser(id);
        return new UserDtoResponse(res.id(), res.name());
    }
}
