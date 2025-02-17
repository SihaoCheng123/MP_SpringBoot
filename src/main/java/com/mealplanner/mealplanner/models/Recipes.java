package com.mealplanner.mealplanner.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String time;

    @Column
    private String rations;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "recipes_list")
    private Set<Users> users_recipes;

    @ManyToMany(mappedBy = "recipes_fav")
    private Set<Users> users_recipes_fav;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ingredients_list",
            joinColumns = @JoinColumn(name = "recipe_id", nullable = true),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", nullable = true))
    private Set<Ingredients> ingredients = new HashSet<>();

    @OneToMany(mappedBy = "recipes")
    private Set<Steps> steps;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRations() {
        return rations;
    }

    public void setRations(String rations) {
        this.rations = rations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Users> getUsers_recipes() {
        return users_recipes;
    }

    public void setUsers_recipes(Set<Users> users_recipes) {
        this.users_recipes = users_recipes;
    }

    public Set<Users> getUsers_recipes_fav() {
        return users_recipes_fav;
    }

    public void setUsers_recipes_fav(Set<Users> users_recipes_fav) {
        this.users_recipes_fav = users_recipes_fav;
    }

    public Set<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Steps> getSteps() {
        return steps;
    }

    public void setSteps(Set<Steps> steps) {
        this.steps = steps;
    }
}
