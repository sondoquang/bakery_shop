package com.stlang.bakery_shop.repositories;

import com.stlang.bakery_shop.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    User findByPhoneNumberAndIdNot(String phoneNumber, Long id);
    User findByEmailAndIdNot(String email, Long id);
}
