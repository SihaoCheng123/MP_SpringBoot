package com.mealplanner.mealplanner.controllers;

import com.mealplanner.mealplanner.models.Ingredients;
import com.mealplanner.mealplanner.services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/create")
    public ResponseEntity<Ingredients> createIngredient(@RequestBody Ingredients ingredient){
        Ingredients createIngredient = this.ingredientsService.createIngredient(ingredient);
        return ResponseEntity.ok(createIngredient);
    }

    @PostMapping("/update")
    public ResponseEntity<Ingredients> updateIngredient(@RequestBody Ingredients ingredients){
        Ingredients updateIng = this.ingredientsService.updateIngredient(ingredients);
        return ResponseEntity.ok(updateIng);
    }

    @GetMapping("/get-ingredients")
    public ResponseEntity<List<Ingredients>> getAllIngredients(){
        List<Ingredients> ingredientsList = this.ingredientsService.getAllIngredients();
        return ResponseEntity.ok(ingredientsList);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Optional<Ingredients>> getIngredientByName(@PathVariable String name){
        Optional<Ingredients> optionalIng = this.ingredientsService.getIngredientByName(name);
        return ResponseEntity.ok(optionalIng);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteIngredientByName(@PathVariable String name){
        this.ingredientsService.deleteIngredientByName(name);
    }
}
