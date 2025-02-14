package com.mealplanner.mealplanner.controllers;

import com.mealplanner.mealplanner.models.Ingredients;
import com.mealplanner.mealplanner.services.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
