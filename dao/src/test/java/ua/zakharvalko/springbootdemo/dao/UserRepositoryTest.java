package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        List<User> users = new ArrayList<>();
        User first = User.builder()
                .id(1)
                .firstName("Fill")
                .build();
        User second = User.builder()
                .id(2)
                .firstName("Alex")
                .build();
        users.add(first);
        users.add(second);

        userRepository.saveAll(users);
    }

    @Test
    void shouldSaveUser() {
        User saved = userRepository.saveAndFlush(User.builder().id(3).build());
        User fromDb = userRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetUserById() {
        user = userRepository.getById(1);
        assertThat(userRepository.getById(user.getId())).isEqualTo(user);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
        assertThat(users).hasSize(2);
    }

    @Test
    void shouldEditUser() {
        User newUser = User.builder()
                .id(1)
                .firstName("Filipp")
                .build();
        userRepository.saveAndFlush(newUser);
        User editedUser = userRepository.getById(1);
        assertEquals("Filipp", editedUser.getFirstName());
    }

    @Test
    void shouldDeleteUser() {
        userRepository.deleteById(1);
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }

}