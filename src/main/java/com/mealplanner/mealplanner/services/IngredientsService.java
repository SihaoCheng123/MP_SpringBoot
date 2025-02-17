package com.mealplanner.mealplanner.services;

import com.mealplanner.mealplanner.models.Ingredients;
import com.mealplanner.mealplanner.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredients> getAllIngredients(){
        return this.ingredientRepository.findAll();
    }

    public Optional<Ingredients> getIngredientByName(String name){
        return this.ingredientRepository.findIngredientByName(name);
    }

    public Ingredients createIngredient(Ingredients ingredients){
        if (this.ingredientRepository.findIngredientByName(ingredients.getName()).isPresent()){
            throw new RuntimeException("Ingredient exists already");
        }

        Ingredients newIng = new Ingredients();
        newIng.setName(ingredients.getName());
        newIng.setPrice(ingredients.getPrice());
        newIng.setCategory(ingredients.getCategory());
        newIng.setImage(ingredients.getImage());

        return this.ingredientRepository.save(newIng);
    }

    public Ingredients updateIngredient(Ingredients ingredients){
        Ingredients ingredientsOptional = this.ingredientRepository.findById(ingredients.getId()).orElseThrow(() ->
                new RuntimeException("Ingredient not found"));

        ingredientsOptional.setName(ingredients.getName());
        ingredientsOptional.setPrice(ingredients.getPrice());
        ingredientsOptional.setCategory(ingredients.getCategory());
        ingredientsOptional.setImage(ingredients.getImage());

        return this.ingredientRepository.save(ingredientsOptional);
    }

    public void deleteIngredientByName(String name){
        Ingredients ingOptional = this.ingredientRepository.findIngredientByName(name).orElseThrow(()->
                new RuntimeException("No ingredient found with name: " + name));
        this.ingredientRepository.delete(ingOptional);
    }
}
