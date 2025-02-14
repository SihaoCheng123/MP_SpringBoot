package com.mealplanner.mealplanner.repositories;

import com.mealplanner.mealplanner.models.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientInterface extends JpaRepository<Ingredients, Long> {
}
