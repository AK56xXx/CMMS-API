package com.cmms.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmms.api.entity.User;
import com.cmms.api.security.JwtService;
import com.cmms.api.service.IServiceUser;

@RestController
@RequestMapping("/api/v1/users")
public class RestUserController {

    @Autowired
    private IServiceUser iServiceUser;

    @Autowired
    private JwtService jwtService;

    // get list of all users
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public List<User> findAllUsers() {
        return iServiceUser.findAllUsers();
    }

    // get list of all technicians
    @GetMapping("/technicians")
    @PreAuthorize("isAuthenticated()")
    public List<User> findAllTechnicians() {
        return iServiceUser.getAllTechnicians();
    }

    // get list of all clients
    @GetMapping("/clients")
    @PreAuthorize("isAuthenticated()")
    public List<User> findAllClients() {
        return iServiceUser.getAllClients();
    }

    // find user by id
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public User findUserById(@PathVariable int id) {
        return iServiceUser.findUserById(id);
    }

    // get user by username via token
    @GetMapping("/token/{token}")
    // @PreAuthorize("isAuthenticated()")
    public Optional<User> findUserByToken(@PathVariable String token) {

        String username = jwtService.extractUsername(token);
        return iServiceUser.getUserByUsername(username);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/admin")
    @PreAuthorize("isAuthenticated()")
    public Optional<User> findUserByUsername(@RequestParam String username) {

        return iServiceUser.getUserByUsername(username);

    }



    // for adding user we use register in AuthenticationService
    /*
     * @PostMapping("/add")
     * 
     * @PreAuthorize("hasAuthority('ADMIN')")
     * public User AddUser(@RequestBody User user) {
     * return iServiceUser.createUser(user);
     * }
     */

    // for updating user
    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public User UpdateUser(@RequestBody User user) {
        return iServiceUser.updateUser(user);
    }

    // for deleting user
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public void DeleteUser(@PathVariable int id) {
        iServiceUser.deleteUser(iServiceUser.findUserById(id));
    }

    @PutMapping("/{userId}/image")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateUserImage(@PathVariable int userId, @RequestParam String imageUrl) {
        Optional<User> updatedUser = iServiceUser.updateUserImage(userId, imageUrl);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
