package com.mealplanner.mealplanner.services;

import com.mealplanner.mealplanner.models.Recipes;
import com.mealplanner.mealplanner.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipesService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipes> getAllRecipes(){
        return this.recipeRepository.findAll();
    }

    public Recipes getRecipeByName(String name){
        return this.recipeRepository.findRecipeByName(name);
    }
}
