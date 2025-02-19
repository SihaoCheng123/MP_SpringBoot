package com.mealplanner.mealplanner.controllers;

import com.mealplanner.mealplanner.dto.ApiDelivery;
import com.mealplanner.mealplanner.dto.LoginRequest;
import com.mealplanner.mealplanner.dto.LoginResponse;
import com.mealplanner.mealplanner.models.User_Data;
import com.mealplanner.mealplanner.models.Users;
import com.mealplanner.mealplanner.services.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        Users createUser = this.usersService.createUser(users);
        return ResponseEntity.ok(createUser);
    }

    @PostMapping("/update")
    public ResponseEntity<Users> updateUser(@RequestBody Users users){
        Users updateUser = this.usersService.updateUser(users, users.getId());
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> usersList = this.usersService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    @GetMapping("/get-user/{email}")
    public ResponseEntity<Optional<Users>> getUserByEmail(@PathVariable String email){
        Optional<Users> optionalUser = this.usersService.getUserByEmail(email);
        return ResponseEntity.ok(optionalUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        this.usersService.deleteUserById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest credentials){
        ApiDelivery<LoginResponse> response = this.usersService.login(credentials.getEmail(), credentials.getPassword());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
