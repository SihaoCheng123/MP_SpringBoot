package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    Optional<Recipes> findRecipeByName(String name);
    Optional<List<Recipes>> findRecipeByDate(Date date);
}
