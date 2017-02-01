package ${package}.repository;

import ${package}.model.Role;
import ${package}.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginName(String loginName);

    List<User> findByRole(Role admin);

}
