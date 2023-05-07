package com.project.vinylata.Repository;

import com.project.vinylata.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User getUsersByEmail(String email);

    void  deleteUsersByEmail(String email);

}
