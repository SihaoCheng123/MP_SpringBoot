package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredients, Long> {
    Optional<Ingredients> findIngredientByName(String ingredients_name);

}
