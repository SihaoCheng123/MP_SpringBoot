package com.mealplanner.mealplanner.controllers;

import com.mealplanner.mealplanner.dto.ApiDelivery;
import com.mealplanner.mealplanner.dto.LoginRequest;
import com.mealplanner.mealplanner.dto.LoginResponse;
import com.mealplanner.mealplanner.dto.PasswordChangeRquest;
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

    @PatchMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody Users users, @PathVariable Long id){
        Users updateUser = this.usersService.updateUser(users, id);
        return ResponseEntity.ok(updateUser);
    }

    @PutMapping("change-password/{id}")
    public ResponseEntity<Users> changePassword(@RequestBody PasswordChangeRquest passwordChangeRquest, @PathVariable Long id){
        Users updatedUser = this.usersService.updatePassword(passwordChangeRquest, id);
        return ResponseEntity.ok(updatedUser);
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

    @GetMapping("get-user-id/{id}")
    public ResponseEntity<Optional<Users>> getUserById(@PathVariable Long id){
        Optional<Users> userOpt = this.usersService.getUserById(id);
        return ResponseEntity.ok(userOpt);
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
