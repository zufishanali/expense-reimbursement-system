package com.revature.reimbursementApi;

import com.revature.reimbursementApi.entities.Reimbursement;
import com.revature.reimbursementApi.entities.User;
import com.revature.reimbursementApi.exceptions.ReimbursementNotFoundException;
import com.revature.reimbursementApi.repositories.ReimbursementRepository;
import com.revature.reimbursementApi.repositories.UserRepository;
import com.revature.reimbursementApi.services.ReimbursementService;
import com.revature.reimbursementApi.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ReimbursementServiceTests {

    private ReimbursementService reimbursementService;
    private ReimbursementRepository reimbursementRepository;

    private UserService userService;
    private UserRepository userRepository;

    User expectedUser;
    Reimbursement expectedReimbursement;

    @BeforeEach
    public void initBeforeTest(){
        System.out.println("Init before test.");

        userRepository = mock(UserRepository.class);
        userService = new UserService();
        userService.setUserRepository(userRepository);


        reimbursementRepository = mock(ReimbursementRepository.class);
        reimbursementService = new ReimbursementService();
        reimbursementService.setReimbursementRepository(reimbursementRepository);
        reimbursementService.setUserService(userService);

        expectedUser = new User(1, "user1@business.com", "user1", "password", "manager", 1);
        expectedReimbursement = new Reimbursement(1, "computer", 1000.00, "Requested", new Timestamp(System.currentTimeMillis()), expectedUser, null);

    }


    @Test
    public void viewByIdThrowReimbursementNotFoundException(){
        when(reimbursementRepository.existsById(1)).thenReturn(false);
        ReimbursementNotFoundException ex = Assertions.assertThrows(ReimbursementNotFoundException.class, () -> {
            reimbursementService.viewById(1);
        });
        Assertions.assertEquals("Reimbursement not found", ex.getMessage());
    }

    @Test
    public void shouldGetById(){
        when(userRepository.getById(1)).thenReturn(expectedUser);
        User actual = userRepository.getById(1);
        assertEquals(expectedUser, actual);
    }

    @Test
    public void doesReimbursementExistShouldReturnTrue(){
        when(reimbursementService.doesReimbursementExist(expectedReimbursement.getReimbursementId())).thenReturn(true);
        boolean t = reimbursementService.doesReimbursementExist(1);
        assertEquals(true, t);
    }

    @Test
    public void findAllShouldReturnAll(){
        List<Reimbursement> allReimbursements = new ArrayList<>();
        allReimbursements.add(expectedReimbursement);
        when(reimbursementRepository.findAll()).thenReturn(allReimbursements);
        List<Reimbursement> actual = reimbursementRepository.findAll();
        assertEquals(allReimbursements, actual);
    }

    @Test
    public void shouldGetAllByUserId(){
        when(reimbursementRepository.findAllBySubmittedEmpId(expectedUser.getUserId())).thenReturn(Optional.ofNullable(expectedReimbursement));
        Optional<Reimbursement> actual = reimbursementRepository.findAllBySubmittedEmpId(1);
        assertEquals(Optional.of(expectedReimbursement), actual);
    }

}
