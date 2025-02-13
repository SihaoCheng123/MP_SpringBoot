package com.mealplanner.mealplanner.services;

import com.mealplanner.mealplanner.models.User_Data;
import com.mealplanner.mealplanner.models.Users;
import com.mealplanner.mealplanner.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUsers(){return this.userRepository.findAll();}

    public Optional<Users> getUserByEmail(String email){
        return this.userRepository.findUserByEmail(email);
    }

    public Users createUser(Users user){
        if (this.userRepository.findUserByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User exists already");
        }

        Users newUser = new Users();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        User_Data newUserData = new User_Data();
        newUserData.setName(user.getUser_data().getName());
        newUserData.setPhone(user.getUser_data().getPhone());
        newUserData.setAge(user.getUser_data().getAge());

        newUser.setUser_data(newUserData);
        return this.userRepository.save(newUser);
    }

    public Users updateUser(Users user, Long id){
        Users userOptional = this.userRepository.findById(id).orElseThrow(()->
                new RuntimeException("User not fount"));

        if (user.getPassword() != null && user.getPassword().isEmpty()){
            userOptional.setPassword(user.getPassword());
        }

        return this.userRepository.save(userOptional);
    }

    public void deleteUserById(Long id){
        Users userOptional = this.userRepository.findById(id).orElseThrow(() ->
            new RuntimeException("No user found with id: " + id));
        this.userRepository.delete(userOptional);
    }


}
