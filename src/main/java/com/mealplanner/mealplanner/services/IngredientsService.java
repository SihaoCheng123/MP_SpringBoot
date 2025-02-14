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

    public Optional<Ingredients> getIngredientByName(String ingredients_name){
        return this.ingredientRepository.findIngredientByName(ingredients_name);
    }

    public Ingredients createIngredient(Ingredients ingredients){
        if (this.ingredientRepository.findIngredientByName(ingredients.getName_ingredient()).isPresent()){
            throw new RuntimeException("Ingredient exists already");
        }

        Ingredients newIng = new Ingredients();
        newIng.setName_ingredient(ingredients.getName_ingredient());
        newIng.setPrice(ingredients.getPrice());
        newIng.setCategory(ingredients.getCategory());
        newIng.setCategory(ingredients.getImage());

        return this.ingredientRepository.save(newIng);
    }

    public Ingredients updateIngredient(Ingredients ingredients){
        Ingredients ingredientsOptional = this.ingredientRepository.findById(ingredients.getId()).orElseThrow(() ->
                new RuntimeException("Ingredient not found"));

        ingredientsOptional.setName_ingredient(ingredients.getName_ingredient());
        ingredientsOptional.setPrice(ingredients.getPrice());
        ingredientsOptional.setCategory(ingredients.getCategory());
        ingredientsOptional.setImage(ingredients.getImage());

        return this.ingredientRepository.save(ingredientsOptional);
    }

    public void deleteIngredientByName(Long id){
        Ingredients ingOptional = this.ingredientRepository.findById(id).orElseThrow(()->
                new RuntimeException("No ingredient found with id: " + id));
        this.ingredientRepository.delete(ingOptional);
    }
}
