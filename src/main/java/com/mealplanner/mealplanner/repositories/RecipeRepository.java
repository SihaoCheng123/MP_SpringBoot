package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {
    Optional<Recipes> findRecipeByName(String name);
    Optional<List<Recipes>> findRecipeByDate(Date date);

    @Query("SELECT r FROM Recipes r JOIN r.users_recipes u WHERE r.date = :date AND u.id = :userId")
    Optional<List<Recipes>> findRecipeByDateAndUserId(@Param("date") Date date, @Param("userId") Long user_id);

    @Query("SELECT r FROM Recipes r WHERE r.date BETWEEN :startDate AND :endDate")
    List<Recipes> findRecipesBetweenDates(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    @Query("SELECT r FROM Recipes r JOIN r.users_recipes u WHERE r.date BETWEEN :startDate AND :endDate AND u.id = :userId")
    List<Recipes> findRecipesBetweenDatesAndUserId(@Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("userId") Long user_id);

    @Query("SELECT u.recipes_fav FROM Users u WHERE u.id = :userId")
    List<Recipes> findFavRecipesByUserId(@Param("userId") Long user_id);

    @Query("SELECT u.recipes_list FROM Users u WHERE u.id = :userId")
    List<Recipes> findAllRecipesByUserId(@Param("userId") Long user_id);
}
