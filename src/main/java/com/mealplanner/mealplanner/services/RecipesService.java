package com.mealplanner.mealplanner.services;

import com.mealplanner.mealplanner.models.Ingredients;
import com.mealplanner.mealplanner.models.Recipes;
import com.mealplanner.mealplanner.models.Steps;
import com.mealplanner.mealplanner.models.Users;
import com.mealplanner.mealplanner.repositories.IngredientRepository;
import com.mealplanner.mealplanner.repositories.RecipeRepository;
import com.mealplanner.mealplanner.repositories.StepRepository;
import com.mealplanner.mealplanner.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class RecipesService {

    private static final String UPLOAD_DIR= "uploads/";

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Recipes> getAllRecipes(){
        return this.recipeRepository.findAll();
    }

    public Optional<Recipes> getRecipeByName(String name){
        return this.recipeRepository.findRecipeByName(name);
    }

    public Optional<Recipes> getRecipeById(Long id){
        return this.recipeRepository.findById(id);
    }

    public Optional<List<Recipes>> getRecipeByDate(Date date){
        return this.recipeRepository.findRecipeByDate(date);
    }

    public Optional<List<Recipes>> getRecipeByDateAndUserId(Date date, Long user_id){
        return this.recipeRepository.findRecipeByDateAndUserId(date, user_id);
    }

    public Recipes createRecipe(Recipes recipes, Long userId, MultipartFile imageFile){
        if(this.recipeRepository.findRecipeByName(recipes.getName()).isPresent()){
            throw new RuntimeException("Recipe already exists");
        }

        Recipes newRecipe = new Recipes();

        newRecipe.setName(recipes.getName());
        newRecipe.setRations(recipes.getRations());
        newRecipe.setTime(recipes.getTime());
        newRecipe.setDate(recipes.getDate());
        newRecipe.setCategory(recipes.getCategory());

        if (imageFile != null && !imageFile.isEmpty()){
            try{
                Files.createDirectories(Paths.get(UPLOAD_DIR));
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                String filePath = UPLOAD_DIR + fileName;
                imageFile.transferTo(new File(filePath));
                newRecipe.setImage(filePath);

            }catch (IOException e){
                System.out.println("Error: " + e);
            }
        }else{
            newRecipe.setImage(null);
        }


        Optional<Users> userOpt = this.userRepository.findById(userId);
        if(userOpt.isEmpty()){
            throw new RuntimeException("User not found");
        }

        Users users = userOpt.get();
        Set<Recipes> users_Recipe = users.getRecipes_list();
        if (users_Recipe == null){
            users_Recipe = new HashSet<>();
        }

        users_Recipe.add(newRecipe);
        users.setRecipes_list(users_Recipe);


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

        Set<Ingredients> ingredientsList = new HashSet<>();
        for (Ingredients ingredients : recipes.getIngredients()){
            Optional<Ingredients> existingIngredientOpt = this.ingredientRepository.findIngredientByName(ingredients.getName());
            Ingredients existingIngredient;
            if (existingIngredientOpt.isPresent()){
                existingIngredient = existingIngredientOpt.get();
            }else {
                existingIngredient = new Ingredients();
                existingIngredient.setName(ingredients.getName());
                this.ingredientRepository.save(existingIngredient);
            }
            ingredientsList.add(existingIngredient);
        }
        newRecipe.setIngredients(ingredientsList);

       return this.recipeRepository.save(newRecipe);
    }

    public Recipes saveFavRecipe(Long user_id, Long recipe_id){
        Optional<Users> userOpt = this.userRepository.findById(user_id);
        Optional<Recipes> recipesOpt = this.recipeRepository.findById(recipe_id);
        Users user = new Users();
        Recipes recipe = new Recipes();
        if (userOpt.isPresent() && recipesOpt.isPresent()){
            user = userOpt.get();
            recipe = recipesOpt.get();
            Set<Recipes> favouriteRecipes = user.getRecipes_fav();
            if (favouriteRecipes == null){
                favouriteRecipes = new HashSet<>();
            }
            favouriteRecipes.add(recipe);
            user.setRecipes_fav(favouriteRecipes);
            this.userRepository.save(user);
        }

        return this.recipeRepository.save(recipe);
    }

    @Transactional
    public Recipes updateRecipe(Recipes recipes){
        Recipes recipesOptional = this.recipeRepository.findById(recipes.getId()).orElseThrow(() ->
                new RuntimeException("Recipe not found"));

        recipesOptional.setName(recipes.getName());
        recipesOptional.setRations(recipes.getRations());
        recipesOptional.setTime(recipes.getTime());
        recipesOptional.setDate(recipes.getDate());
        recipesOptional.setCategory(recipes.getCategory());
        recipesOptional.setImage(recipes.getImage());
        if (recipes.getSteps().isEmpty()){
            throw new RuntimeException("No steps found");
        }
        Set<Steps> stepsList = new HashSet<>();

        if (recipes.getSteps() != null){
            for (Steps step: recipes.getSteps()) {
                Optional<Steps> existingStepOpt = this.stepRepository.findById(step.getId());
                if (existingStepOpt.isPresent()){
                    Steps existingStep = existingStepOpt.get();
                    existingStep.setDescription(step.getDescription());
                    this.stepRepository.save(existingStep);
                }else{
                    step.setRecipes(recipesOptional);
                    this.stepRepository.save(step);
                }
            }
            recipesOptional.setSteps(stepsList);
        }

        Set<Ingredients> ingredientsList = new HashSet<>();
        if (recipes.getIngredients() != null){
            for (Ingredients ingredient : recipes.getIngredients()){
                Optional<Ingredients> existingIngOpt = this.ingredientRepository.findIngredientByName(ingredient.getName());
                Ingredients newIng;
                if (existingIngOpt.isPresent()){
                    newIng = existingIngOpt.get();
                }else {
                    newIng = new Ingredients();
                    newIng.setName(ingredient.getName());
                    this.ingredientRepository.save(newIng);
                }
                ingredientsList.add(newIng);
            }
            recipesOptional.setIngredients(ingredientsList);
        }
        return this.recipeRepository.save(recipesOptional);
    }

    public void deleteRecipeByName(String name){
        Recipes recipeOptional = this.recipeRepository.findRecipeByName(name).orElseThrow(() ->
                new RuntimeException("No recipe found with name: " + name));
        this.recipeRepository.delete(recipeOptional);
    }

    public List<Ingredients> getIngredientsFromThisWeek(Date today){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startOfWeek = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date endOfWeek = calendar.getTime();

        List<Recipes> recipeListWeek = this.recipeRepository.findRecipesBetweenDates(startOfWeek, endOfWeek);

        List<Ingredients> ingredientsListWeek = new ArrayList<>();
        for (Recipes recipes: recipeListWeek){
            ingredientsListWeek.addAll(recipes.getIngredients());
        }
        return ingredientsListWeek;

    }

    public List<Ingredients> getIngredientsFromThisWeekAndUserId(Date today, Long user_id){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startOfWeek = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date endOfWeek = calendar.getTime();

        List<Recipes> recipeListWeek = this.recipeRepository.findRecipesBetweenDates(startOfWeek, endOfWeek);

        List<Ingredients> ingredientsListWeek = new ArrayList<>();
        Optional<Users> userOpt = this.userRepository.findById(user_id);
        if (userOpt.isPresent()){
            Users existingUser = userOpt.get();
            for (Recipes recipes: recipeListWeek){
                if (existingUser.getId().equals(user_id)){
                    ingredientsListWeek.addAll(recipes.getIngredients());
                }
            }
        }

        return ingredientsListWeek;

    }

    public List<Recipes> getFavRecipesByUserId(Long user_id){
        return this.recipeRepository.findFavRecipesByUserId(user_id);
    }

    public List<Recipes> getAllRecipesByUserId(Long user_id){
        return this.recipeRepository.findAllRecipesByUserId(user_id);
    }
}
