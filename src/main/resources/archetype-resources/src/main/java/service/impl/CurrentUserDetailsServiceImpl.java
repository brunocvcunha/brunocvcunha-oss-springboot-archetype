package ${package}.service.impl;

import ${package}.model.CurrentUser;
import ${package}.model.User;
import ${package}.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CurrentUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByLoginName(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentUser(user);
    }

}
