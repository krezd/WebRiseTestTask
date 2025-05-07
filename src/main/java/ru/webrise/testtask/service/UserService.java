package ru.webrise.testtask.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import ru.webrise.testtask.entity.User;
import ru.webrise.testtask.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        log.info("Find user by id - {}",id);
        return user;
    }

    @Transactional
    public User createUser(User userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
        User savedUser = userRepository.save(user);
        log.info("Create new user: {}", savedUser.getName() + ", " + savedUser.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id,User userRequest) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        User savedUser = userRepository.save(user);
        log.info("Update user: {}", savedUser.getName() + ", " + savedUser.getEmail());
        return savedUser;
    }

    @Transactional
    public Boolean deleteUser(long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        log.info("Delete user by id - {}",id);
        return true;
    }
}
