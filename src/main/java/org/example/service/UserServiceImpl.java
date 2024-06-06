package org.example.service;

import org.example.exception.EntityNotFoundException;
import org.example.repository.UserRepository;
import org.example.repository.entity.UserEntity;
import org.example.service.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long addUser(User user) {
        Optional<UserEntity> userEntity = userRepository.findByName(user.name());
        if(userEntity.isPresent())
            return userEntity.get().getId();
        return userRepository.save(new UserEntity(user.name())).getId();
    }

    @Override
    public User findUser(long id) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new EntityNotFoundException("Юзер с id " + id + " не найден.");
        return new User(user.get().getId(), user.get().getName());
    }

    @Override
    public long getId(String name) throws EntityNotFoundException {
        Optional<UserEntity> user = userRepository.findByName(name);
        if(user.isEmpty())
            throw new EntityNotFoundException("Юзер с именем " + name + " не найден.");
        return user.get().getId();
    }
}
