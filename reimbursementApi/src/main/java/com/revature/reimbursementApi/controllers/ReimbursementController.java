package com.revature.reimbursementApi.controllers;

import com.revature.reimbursementApi.dtos.ReimbursementSubmitDTO;
import com.revature.reimbursementApi.dtos.ReimbursementUpdateDTO;
import com.revature.reimbursementApi.entities.Reimbursement;
import com.revature.reimbursementApi.exceptions.ReimbursementNotFoundException;
import com.revature.reimbursementApi.exceptions.UserNotFoundException;
import com.revature.reimbursementApi.services.ReimbursementService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reimbursements")
public class ReimbursementController {


    @Setter(onMethod = @__({@Autowired}) )
    private ReimbursementService reimbursementService;

    @GetMapping
    public ResponseEntity getAllReimbursements(){
        return ResponseEntity.ok(reimbursementService.viewAllReimbursements());
    }

    @GetMapping("{id}")
    public ResponseEntity getReimbursementById(@PathVariable int id){
        try{
            Reimbursement r = reimbursementService.viewById(id);
            return ResponseEntity.ok(r);
        }catch(ReimbursementNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{empId}")
    public ResponseEntity getReimbursementsForUser(@PathVariable int empId){
        try{
            Optional<Reimbursement> re = reimbursementService.getAllReimbursementsByUserId(empId);
            if(re.isPresent()){
                return ResponseEntity.ok(re);
            } else
                return ResponseEntity.ok("No reimbursements submitted by user");
        } catch(UserNotFoundException e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity addReimbursement(@RequestBody ReimbursementSubmitDTO input){
        try{
            reimbursementService.submitRequest(input.getItemName(), input.getItemCost(), input.getEmpId());
            return ResponseEntity.ok("Created new reimbursement");
        } catch(UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error creating reimbursement. " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateReimbursement(@RequestBody ReimbursementUpdateDTO input){
        try{
            reimbursementService.handleUpdateRequest(input);
            return ResponseEntity.ok("Successfully updated reimbursement");
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error updating reimbursement. " + e.getMessage());

        }
    }
}
