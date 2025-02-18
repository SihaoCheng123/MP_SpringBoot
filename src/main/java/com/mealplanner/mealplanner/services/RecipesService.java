package com.mealplanner.mealplanner.services;

import com.mealplanner.mealplanner.models.Recipes;
import com.mealplanner.mealplanner.models.Steps;
import com.mealplanner.mealplanner.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipesService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipes> getAllRecipes(){
        return this.recipeRepository.findAll();
    }

    public Optional<Recipes> getRecipeByName(String name){
        return this.recipeRepository.findRecipeByName(name);
    }

    public Recipes createRecipe(Recipes recipes){
        if(this.recipeRepository.findRecipeByName(recipes.getName()).isPresent()){
            throw new RuntimeException("Recipe already exists");
        }

        Recipes newRecipe = new Recipes();
        newRecipe.setName(recipes.getName());
        newRecipe.setRations(recipes.getRations());
        newRecipe.setTime(recipes.getTime());

        if (recipes.getSteps().isEmpty()){
            throw new RuntimeException("No steps found");
        }
        Set<Steps> stepsList = new HashSet<>();
        for (Steps step: recipes.getSteps()) {
            Steps newStep = new Steps();
            newStep.setNumber_step(step.getNumber_step());
            newStep.setDescription(step.getDescription());
            newStep.setRecipes(newRecipe);
            stepsList.add(newStep);
            System.out.println(step.getDescription());
        }

        newRecipe.setSteps(stepsList);
       return this.recipeRepository.save(newRecipe);
    }

    public Recipes updateRecipe(Recipes recipes){
        Recipes recipesOptional = this.recipeRepository.findById(recipes.getId()).orElseThrow(() ->
                new RuntimeException("Recipe not found"));

        recipesOptional.setName(recipes.getName());
        recipesOptional.setRations(recipes.getRations());
        recipesOptional.setTime(recipes.getTime());

        if (recipes.getSteps().isEmpty()){
            throw new RuntimeException("No steps found");
        }
        Set<Steps> stepsList = new HashSet<>();

        if (recipes.getSteps() != null){
            for (Steps step: recipes.getSteps()) {
                Steps newStep = new Steps();
                newStep.setRecipes(recipesOptional);
                newStep.setNumber_step(step.getNumber_step());
                newStep.setDescription(step.getDescription());
                newStep.setRecipes(recipesOptional);
                stepsList.add(newStep);
            }
            recipesOptional.setSteps(stepsList);
        }



        return this.recipeRepository.save(recipesOptional);
    }

    public void deleteRecipeByName(String name){
        Recipes recipeOptional = this.recipeRepository.findRecipeByName(name).orElseThrow(() ->
                new RuntimeException("No recipe found with name: " + name));
        this.recipeRepository.delete(recipeOptional);
    }
}
