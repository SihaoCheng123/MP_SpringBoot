package com.mealplanner.mealplanner.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_data_id", referencedColumnName = "id")
    private User_Data user_data;

    @ManyToMany
    @JoinTable(name = "recipes_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipes> recipes_list;

    @ManyToMany
    @JoinTable(name = "recipes_fav",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipes> recipes_fav;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public User_Data getUser_data() {
        return user_data;
    }

    public void setUser_data(User_Data user_data) {
        this.user_data = user_data;
    }

    public Set<Recipes> getRecipes_list() {
        return recipes_list;
    }

    public void setRecipes_list(Set<Recipes> recipes_list) {
        this.recipes_list = recipes_list;
    }

    public Set<Recipes> getRecipes_fav() {
        return recipes_fav;
    }

    public void setRecipes_fav(Set<Recipes> recipes_fav) {
        this.recipes_fav = recipes_fav;
    }


}
