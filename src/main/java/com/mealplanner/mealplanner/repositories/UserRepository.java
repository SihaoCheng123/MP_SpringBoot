package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findUserByEmail(String email);
}
