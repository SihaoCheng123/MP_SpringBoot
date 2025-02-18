package com.mealplanner.mealplanner.controllers;

import com.mealplanner.mealplanner.models.Recipes;
import com.mealplanner.mealplanner.services.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @PostMapping("/create")
    public ResponseEntity<Recipes> createRecipe(@RequestBody Recipes recipes){
        Recipes createRecipe = this.recipesService.createRecipe(recipes);
        return ResponseEntity.ok(createRecipe);
    }

    @PostMapping("/update")
    public ResponseEntity<Recipes> updateRecipe(@RequestBody Recipes recipes){
        Recipes updateRecipe = this.recipesService.updateRecipe(recipes);
        return ResponseEntity.ok(updateRecipe);
    }

    @GetMapping("/get-recipes")
    public ResponseEntity<List<Recipes>> getAllRecipes(){
        List<Recipes> recipesList = this.recipesService.getAllRecipes();
        return ResponseEntity.ok(recipesList);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Optional<Recipes>> getRecipeByName(@PathVariable String name){
        Optional<Recipes> optionalRecipe = this.recipesService.getRecipeByName(name);
        return ResponseEntity.ok(optionalRecipe);
    }

    @DeleteMapping("delete/{name}")
    public void deleteRecipeByName(@PathVariable String name){
        this.recipesService.deleteRecipeByName(name);
    }
}
