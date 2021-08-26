package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.UserRepository;
import ua.zakharvalko.springbootdemo.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserServiceImpl.class)
@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void shouldReturnUserWhenSaved() {
        User user = User.builder().id(1).build();

        userRepository.save(user);
        when(userRepository.getById(1)).thenReturn(user);
        User actual = userService.getById(user.getId());

        assertEquals(user.getId(), actual.getId());
        verify(userRepository).save(user);
    }

    @Test
    public void shouldDeleteUser() {
        User user = User.builder().id(1).build();

        when(userRepository.getById(user.getId())).thenReturn(user);
        userService.delete(user.getId());
        verify(userRepository).delete(user.getId());
    }

    @Test
    public void shouldEditUser() {
        User oldUser = User.builder().id(1).build();
        oldUser.setFirst_name("Old First Name");
        User newUser = User.builder().id(1).build();
        newUser.setFirst_name("New First Name");

        given(userRepository.getById(oldUser.getId())).willReturn(oldUser);
        userService.update(newUser);

        verify(userRepository).update(newUser);
    }

    @Test
    public void shouldReturnUserById() {
        User user = User.builder().id(1).build();
        when(userRepository.getById(1)).thenReturn(user);

        User actual = userService.getById(1);

        assertEquals(user.getId(), actual.getId());
        verify(userRepository).getById(1);
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.getAll()).thenReturn(users);

        List<User> actual = userService.getAll();

        assertEquals(users, actual);
        verify(userRepository).getAll();
    }
}