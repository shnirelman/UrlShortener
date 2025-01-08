package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.service.model.User;

public interface UserService {

    long addUser(User user);

    User findUser(long id) throws EntityNotFoundException;

    long getId(String name) throws EntityNotFoundException;
}
