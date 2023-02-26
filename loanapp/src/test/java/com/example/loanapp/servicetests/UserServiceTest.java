package com.example.loanapp.servicetests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.loanapp.model.User;
import com.example.loanapp.repository.UserRepository;
import com.example.loanapp.service.UserService;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddUser() {
        // Arrange
        User newUser = new User("john.doe", "johndoe@gmail.com");
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        User addedUser = userService.addUser(newUser);

        // Assert
        verify(userRepository, times(1)).findByUsername(newUser.getUsername());
        verify(userRepository, times(1)).save(newUser);
        assertEquals(newUser, addedUser);
    }

    @Test
    public void testAddUserWithExistingUsername() {
        // Arrange
        User existingUser = new User("jane.doe", "Jane Doe");
        User newUser = new User("jane.doe", "Another Jane");
        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(Optional.of(existingUser));

        // Act
        User addedUser = userService.addUser(newUser);

        // Assert
        verify(userRepository, times(1)).findByUsername(newUser.getUsername());
        verify(userRepository, never()).save(any(User.class));
        assertTrue(addedUser.getDbStatus());
    }
}
