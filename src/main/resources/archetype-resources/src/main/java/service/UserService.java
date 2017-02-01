package ${package}.service;

import ${package}.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByLoginName(String loginName);

    User findById(Long id);

}