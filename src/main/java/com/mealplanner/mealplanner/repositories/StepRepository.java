package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Steps, Long> {

}
