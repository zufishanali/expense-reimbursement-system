package com.revature.reimbursementApi.services;

import com.revature.reimbursementApi.dtos.ReimbursementUpdateDTO;
import com.revature.reimbursementApi.dtos.RequestEmailDTO;
import com.revature.reimbursementApi.entities.Reimbursement;
import com.revature.reimbursementApi.entities.User;
import com.revature.reimbursementApi.exceptions.ReimbursementNotFoundException;
import com.revature.reimbursementApi.exceptions.UserNotFoundException;
import com.revature.reimbursementApi.exceptions.UserNotPermittedException;
import com.revature.reimbursementApi.repositories.ReimbursementRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReimbursementService {

    final Logger logger = LoggerFactory.getLogger(ReimbursementService.class);

    @Setter(onMethod = @__({@Autowired}) )
    private RequestEmailService requestEmailService;

    @Setter(onMethod = @__({@Autowired}) )
    private ReimbursementRepository reimbursementRepository;

    @Setter(onMethod = @__({@Autowired}) )
    private UserService userService;

    public boolean doesReimbursementExist(int id){
        return reimbursementRepository.existsById(id);
    }

    /**
     * This method returns all the reimbursements in db
     * @return all reimbursements in db
     */
    public List<Reimbursement> viewAllReimbursements(){
        return reimbursementRepository.findAll();
    }

    /**
     * This method returns the reimbursement with the id specified
     * @param id
     * @return reimbursement with the id specified
     * @throws ReimbursementNotFoundException
     */
    public Reimbursement viewById(int id) throws ReimbursementNotFoundException {
       if(!reimbursementRepository.existsById(id)){
           logger.warn("Reimbursement not found in db");
           throw new ReimbursementNotFoundException("Reimbursement not found");
       }
       return reimbursementRepository.findById(id).get();

    }


    /**
     * This method returns all the reimbursements for the specified user
     * @param id
     * @return list of reimbursements that a specific user owns
     * @throws UserNotFoundException
     */
    public Optional<Reimbursement> getAllReimbursementsByUserId(int id) throws UserNotFoundException {
        if(!userService.doesUserExist(id)){
            logger.warn("User not found in db");
            throw new UserNotFoundException("User not found");
        }
        return reimbursementRepository.findAllBySubmittedEmpId(id);
    }


    /**
     * This method creates a new reimbursement object and saves it to the db
     * @param itemName
     * @param itemCost
     * @param empId
     * @return true if successful, false if failed
     * @throws IllegalArgumentException
     * @throws UserNotFoundException
     */
    public boolean submitRequest(String itemName, double itemCost, int empId) throws UserNotFoundException {

        if(!userService.doesUserExist(empId)){
            logger.warn("User not found in db");
            throw new UserNotFoundException("User not found");
        }

        User emp = userService.getUserById(empId);

        Reimbursement reimbursement = new Reimbursement();
        reimbursement.setItemName(itemName);
        reimbursement.setItemCost(itemCost);
        reimbursement.setRequestStatus("Requested");
        reimbursement.setRequestDate(new Timestamp(System.currentTimeMillis()));
        reimbursement.setSubmittedByEmp(emp);
        reimbursementRepository.save(reimbursement);
        requestEmail(emp, reimbursement, "Your reimbursement request was submitted");
        return true;
    }


    /**
     * This method updates an existing reimbursement in db
     * It updates the status to either "Approved", "Denied", "Reassigned" based on the updateType specified
     * It also updates the id of the manager who makes the update
     * @param status allows "Approved", "Denied", "Reassigned"
     * @param reimbursementId id of existing reimbursement
     * @param manId id of existing user whose role is "manager"
     * @param reassignId the id of the user, with role "employee", to whom the reimbursement is reassigned to
     * @return true if successful, false otherwise
     * @throws UserNotFoundException
     * @throws ReimbursementNotFoundException
     * @throws UserNotPermittedException
     * @throws IllegalArgumentException
     */
    public boolean updateReimbursement(String status, String emailSubject, int reimbursementId, int manId, int... reassignId) throws UserNotFoundException, ReimbursementNotFoundException, UserNotPermittedException, IllegalArgumentException {

        /* Validating Manager */
        if(!userService.doesUserExist(manId)){
            logger.warn("Manager not found in db");
            throw new UserNotFoundException("Manager not found");
        }

        User manager = userService.getUserById(manId);

        if(!manager.getRole().equalsIgnoreCase("manager")){
           logger.warn("Employee cannot update reimbursement.");
           throw new UserNotPermittedException("Exception: User must be a manager to update reimbursement status");
        }

        /* Validating Reimbursement */
        if(!doesReimbursementExist(reimbursementId)){
            logger.warn("Reimbursement not found in db");
            throw new ReimbursementNotFoundException("Reimbursement not found");
        }

        Reimbursement reimbursement = reimbursementRepository.findById(reimbursementId).get();

        if (!(reimbursement.getRequestStatus().equalsIgnoreCase("Requested") || reimbursement.getRequestStatus().equalsIgnoreCase("Reassigned"))){
            logger.warn("Status is not requested or reassigned. It has already been " + reimbursement.getRequestStatus());
            throw new IllegalArgumentException("Exception: This reimbursement has already been Approved/Denied in past.");
        }


        /* Setting the changes based on status passed */
        if (status.equals("Approved") || status.equals("Denied")) {
            reimbursement.setRequestStatus(status);
            reimbursement.setUpdatedByMan(manager);
            reimbursementRepository.save(reimbursement);
            try {
                requestEmail(reimbursement.getSubmittedByEmp(), reimbursement, emailSubject);

            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        } else if(status.equals("Reassigned")) {
            if (reassignId.length == 0) {
                logger.warn("Reassign Id not specified");
                throw new IllegalArgumentException("Update type = reassigned but no reassign id specified.");
            }
            if (!userService.doesUserExist(reassignId[0])) {
                logger.warn("User id for Reassign to not found in db");
                throw new UserNotFoundException("Reassign User not found");
            }

            User reassignEmp = userService.getUserById(reassignId[0]);
            User previousEmp = reimbursement.getSubmittedByEmp();

            reimbursement.setRequestStatus(status);
            reimbursement.setSubmittedByEmp(reassignEmp);
            reimbursement.setUpdatedByMan(manager);
            reimbursementRepository.save(reimbursement);
            try {
                requestEmail(previousEmp, reimbursement, emailSubject + reassignEmp.getUsername());
                requestEmail(reimbursement.getSubmittedByEmp(), reimbursement, "Reimbursement previously submitted by " + previousEmp.getUsername() + " has been reassigned to you.");
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        return true;
    }


    /**
     * This method handles the update request based on the update type passed and calls the udateReimbursement method with the corresponding args
     * @param input
     * @throws IllegalArgumentException
     */
    public void handleUpdateRequest(ReimbursementUpdateDTO input) throws IllegalArgumentException{
        if(input.getUpdateType().equalsIgnoreCase("approve")){
            String emailSubject = "Your reimbursement has been approved.";
            updateReimbursement("Approved", emailSubject, input.getReimbursementId(), input.getManagerId());

        } else if (input.getUpdateType().equalsIgnoreCase("deny")) {
            String emailSubject = "Your reimbursement has been denied.";
            updateReimbursement("Denied", emailSubject, input.getReimbursementId(), input.getManagerId());

        } else if(input.getUpdateType().equalsIgnoreCase("reassign")){
            String emailSubject = "Your reimbursement has been reassigned to employee: ";
            updateReimbursement("Reassigned", emailSubject, input.getReimbursementId(), input.getManagerId(), input.getReassignId());

        } else {
            logger.warn("Wrong update type entered: " + input.getUpdateType());
            throw new IllegalArgumentException("Exception: Invalid update type entered. Only values [approve, deny, reassign] allowed");
        }
    }

    /**
     * This method requests the emailService to ping the email pai to send an email message to the employee email address passed
     * @param emp
     * @param reimbursement
     * @param subject
     */
    public void requestEmail(User emp, Reimbursement reimbursement, String subject){
        logger.info("Requesting email-api to send reimbursement email.");
        RequestEmailDTO emailRequest = new RequestEmailDTO(emp.getEmail(), subject, reimbursement.getReimbursementId());
        try {
            requestEmailService.createEmailRequest(emailRequest);
        } catch(Exception e){
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
    }
}
