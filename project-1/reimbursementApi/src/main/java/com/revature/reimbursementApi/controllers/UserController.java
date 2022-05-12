package com.revature.reimbursementApi.controllers;


import com.revature.reimbursementApi.entities.Reimbursement;
import com.revature.reimbursementApi.entities.User;
import com.revature.reimbursementApi.exceptions.ReimbursementNotFoundException;
import com.revature.reimbursementApi.exceptions.UserNotFoundException;
import com.revature.reimbursementApi.services.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Setter(onMethod = @__({@Autowired}) )
    private UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity getUserById(@PathVariable int id){
        try{
            User u = userService.getUserById(id);
            return ResponseEntity.ok(u);
        } catch(UserNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
