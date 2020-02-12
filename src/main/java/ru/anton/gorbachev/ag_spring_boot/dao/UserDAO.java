package ru.anton.gorbachev.ag_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.anton.gorbachev.ag_spring_boot.models.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    @Query("select user from User user where user.login=:login")
    User findByLogin(@Param("login") String login);

    @Query("select user from User user where user.id=:id")
    User findUserById(@Param("id") Long id);
}
