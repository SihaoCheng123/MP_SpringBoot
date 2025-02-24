package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    Optional<Recipes> findRecipeByName(String name);
    Optional<List<Recipes>> findRecipeByDate(Date date);

    String query = "SELECT r.* FROM recipes as r, recipes_list as rl WHERE r.id = rl.recipe_id AND r.date = '' AND rl.user_id = 1;";

    @Query("SELECT r FROM Recipes r JOIN r.users_recipes u WHERE r.date = :date AND u.id = :userId")
    Optional<List<Recipes>> findRecipeByDateAndUserId(@Param("date") Date date, @Param("userId") Long user_id);
}
