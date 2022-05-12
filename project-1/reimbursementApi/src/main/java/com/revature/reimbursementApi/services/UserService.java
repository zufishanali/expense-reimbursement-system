package com.revature.reimbursementApi.services;

import com.revature.reimbursementApi.entities.User;
import com.revature.reimbursementApi.exceptions.UserNotFoundException;
import com.revature.reimbursementApi.repositories.UserRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Setter(onMethod = @__({@Autowired}) )
    private UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id) throws UserNotFoundException {
        if(!doesUserExist(id)){
            logger.warn("User not found in db");
            throw new UserNotFoundException("User not found");
        }
        return userRepository.findById(id).get();
    }

    public boolean doesUserExist(int id)  throws UserNotFoundException{
        if(!userRepository.existsById(id)){
            logger.warn("User not found in db");
            throw new UserNotFoundException("User not found");
        }
        else
            return true;
    }

    /*
    public List<User> getAllEmployeesByManagerId(int id){
        return userRepository.getAllEmployeesByManagerId(id);
    }
    */

}
