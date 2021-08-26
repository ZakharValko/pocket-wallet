package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        User first = User.builder()
                .id(1)
                .first_name("Fill")
                .build();
        User second = User.builder()
                .id(2)
                .first_name("Alex")
                .build();

        userRepository.save(first);
        userRepository.save(second);
    }

    @Test
    void shouldSaveUser() {
        User saved = User.builder().id(3).build();
        userRepository.save(saved);
        User fromDb = userRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());

    }

    @Test
    void shouldGetUserById() {
        user = userRepository.getById(1);
        assertThat(userRepository.getById(user.getId())).isEqualTo(user);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = userRepository.getAll();
        assertNotNull(users);
        assertThat(users).hasSize(2);
    }

    @Test
    void shouldEditUser() {
        User newUser = User.builder()
                .id(1)
                .first_name("Filipp")
                .build();
        userRepository.update(newUser);
        User editedUser = userRepository.getById(1);
        assertEquals("Filipp", editedUser.getFirst_name());
    }

    @Test
    void shouldDeleteUser() {
        userRepository.delete(1);
        List<User> users = userRepository.getAll();
        assertThat(users).hasSize(1);
    }

}