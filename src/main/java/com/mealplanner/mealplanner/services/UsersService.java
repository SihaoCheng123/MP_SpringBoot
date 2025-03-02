package com.mealplanner.mealplanner.services;

import com.mealplanner.mealplanner.dto.ApiDelivery;
import com.mealplanner.mealplanner.dto.LoginResponse;
import com.mealplanner.mealplanner.models.User_Data;
import com.mealplanner.mealplanner.models.Users;
import com.mealplanner.mealplanner.repositories.UserRepository;
import com.mealplanner.mealplanner.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwt;

    public List<Users> getAllUsers(){return this.userRepository.findAll();}

    public Optional<Users> getUserByEmail(String email){
        return this.userRepository.findUserByEmail(email);
    }

    public Optional<Users> getUserById(Long id){
        return this.userRepository.findById(id);
    }

    public Users createUser(Users user){
        if (this.userRepository.findUserByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("User exists already");
        }

        Users newUser = new Users();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if (user.getUser_data() == null){
            throw new RuntimeException("User data not found");
        }
        User_Data newUserData = new User_Data();
        newUserData.setName(user.getUser_data().getName());
        newUserData.setPhone(user.getUser_data().getPhone());
        newUserData.setAge(user.getUser_data().getAge());
        System.out.println(user.getUser_data().getName());
        System.out.println(user.getUser_data().getAge());
        System.out.println(user.getUser_data().getPhone());


        newUser.setUser_data(newUserData);
        return this.userRepository.save(newUser);
    }

    public Users updateUser(Users user, Long id){
        Users userOptional = this.userRepository.findById(id).orElseThrow(()->
                new RuntimeException("User not fount"));

        userOptional.setEmail(user.getEmail());
        User_Data newUserData = new User_Data();
        newUserData.setName(user.getUser_data().getName());
        newUserData.setAge(user.getUser_data().getAge());
        newUserData.setPhone(user.getUser_data().getPhone());
        userOptional.setUser_data(newUserData);

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

    public ApiDelivery<LoginResponse> login(String email, String password){
        Optional<Users> optionaUser = this.userRepository.findUserByEmail(email);

        if (optionaUser.isEmpty()){
            return new ApiDelivery<>("User not found", false, 404, null, "Not found");
        }

        Users user = optionaUser.get();
        if (!this.passwordEncoder.matches(password, user.getPassword())){
            return new ApiDelivery<>("Incorrect password", false, 400, null, "Incorrect password");
        }
        String token = this.createToken(email);
        LoginResponse login = new LoginResponse(user, token);
        return new ApiDelivery<>("Login success", true, 200, login, "Login success");
    }

    public String createToken(String email){
        return this.jwt.generateToken(email);
    }

}
