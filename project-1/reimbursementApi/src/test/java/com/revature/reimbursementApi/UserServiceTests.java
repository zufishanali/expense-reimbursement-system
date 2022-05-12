package com.revature.reimbursementApi;
import com.revature.reimbursementApi.entities.User;
import com.revature.reimbursementApi.exceptions.UserNotFoundException;
import com.revature.reimbursementApi.repositories.UserRepository;
import com.revature.reimbursementApi.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTests {

    private UserRepository userRepository;
    private UserService userService;
    User expectedUser;

    @BeforeEach
    public void initBeforeTest() {
        System.out.println("Init before test.");

        userRepository = mock(UserRepository.class);
        userService = new UserService();
        userService.setUserRepository(userRepository);

        expectedUser = new User(1, "user1@business.com", "user1", "password", "manager", 1);
    }

    @Test
    public void shouldGetAllUsers(){
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<User> users = userService.getAllUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    public void getUserByIdThrowsUserNotFoundException(){
        when(userRepository.existsById(1)).thenReturn(false);
        UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1);
        });
        Assertions.assertEquals("User not found", ex.getMessage());
    }

    @Test
    public void doesUserExistThrowsUserNotFoundException(){
        when(userRepository.existsById(10)).thenReturn(false);
        UserNotFoundException ex = Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.doesUserExist(10);
        });
        Assertions.assertEquals("User not found", ex.getMessage());
    }


    @Test
    public void doesUserExistsShouldReturnTrue(){
        when(userRepository.existsById(expectedUser.getUserId())).thenReturn(true);
        boolean t = userService.doesUserExist(1);
        assertEquals(true, t);
    }





}
