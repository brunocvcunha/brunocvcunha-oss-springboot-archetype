package ${package}.service.impl;

import ${package}.model.User;
import ${package}.repository.UserRepository;
import ${package}.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserByLoginName(String loginName) {
        return Optional.ofNullable(userRepository.findByLoginName(loginName));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

}
