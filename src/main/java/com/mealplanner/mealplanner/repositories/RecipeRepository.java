package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    Recipes findRecipeByName(String name);
}
