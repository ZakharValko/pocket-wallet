package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Role;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@MybatisTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        Role first = Role.builder()
                .id(1)
                .title("Admin")
                .build();
        Role second = Role.builder()
                .id(2)
                .title("User")
                .build();

        roleRepository.save(first);
        roleRepository.save(second);
    }

    @Test
    void shouldSaveRole() {
        Role saved = Role.builder().id(3).build();
        roleRepository.save(saved);
        Role fromDb = roleRepository.getById(3);
        assertEquals(saved.getId(), fromDb.getId());
    }

    @Test
    void shouldGetRoleById() {
        role = roleRepository.getById(1);
        assertThat(roleRepository.getById(role.getId())).isEqualTo(role);
    }

    @Test
    void shouldGetAllRoles() {
        List<Role> roles = roleRepository.getAll();
        assertNotNull(roles);
        assertThat(roles).hasSize(2);
    }

    @Test
    void shouldEditRole() {
        Role newRole = Role.builder()
                .id(1)
                .title("Administrator")
                .build();
        roleRepository.update(newRole);
        Role editedRole = roleRepository.getById(1);
        assertEquals("Administrator", editedRole.getTitle());
    }

    @Test
    void shouldDeleteRole() {
        roleRepository.delete(1);
        List<Role> roles = roleRepository.getAll();
        assertThat(roles).hasSize(1);
    }

}