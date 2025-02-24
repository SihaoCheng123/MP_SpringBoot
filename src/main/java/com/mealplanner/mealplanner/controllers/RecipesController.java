package com.mealplanner.mealplanner.controllers;

import com.mealplanner.mealplanner.models.Recipes;
import com.mealplanner.mealplanner.models.Users;
import com.mealplanner.mealplanner.services.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipesController {

    @Autowired
    private RecipesService recipesService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Recipes> createRecipe(@RequestBody Recipes recipes, @PathVariable Long userId){
        Date date = recipes.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        recipes.setDate(calendar.getTime());

        System.out.println(recipes.toString());
        System.out.println(userId);
        Recipes createRecipe = this.recipesService.createRecipe(recipes, userId);
        return ResponseEntity.ok(createRecipe);
    }

    @PostMapping("/update")
    public ResponseEntity<Recipes> updateRecipe(@RequestBody Recipes recipes){
        Date date = recipes.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Recipes updateRecipe = this.recipesService.updateRecipe(recipes);
        return ResponseEntity.ok(updateRecipe);
    }

    @GetMapping("/get-recipes")
    public ResponseEntity<List<Recipes>> getAllRecipes(){
        List<Recipes> recipesList = this.recipesService.getAllRecipes();
        return ResponseEntity.ok(recipesList);
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<Optional<Recipes>> getRecipeByName(@PathVariable String name){
        Optional<Recipes> optionalRecipe = this.recipesService.getRecipeByName(name);
        return ResponseEntity.ok(optionalRecipe);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Optional<Recipes>> getRecipeById(@PathVariable Long id){
        Optional<Recipes> optRecipe = this.recipesService.getRecipeById(id);
        return ResponseEntity.ok(optRecipe);
    }

    @GetMapping("/get-by-date/{date}")
    public ResponseEntity<Optional<List<Recipes>>> getRecipeByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        Optional<List<Recipes>> optRecipe = this.recipesService.getRecipeByDate(date);
        return ResponseEntity.ok(optRecipe);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteRecipeByName(@PathVariable String name){
        this.recipesService.deleteRecipeByName(name);
    }
}
