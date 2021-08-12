package ua.zakharvalko.springbootdemo.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.domain.Role;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    void setUp() {
        List<Role> roles = new ArrayList<>();
        Role first = Role.builder()
                .id(1)
                .title("Admin")
                .build();
        Role second = Role.builder()
                .id(2)
                .title("User")
                .build();
        roles.add(first);
        roles.add(second);

        roleRepository.saveAll(roles);
    }

    @Test
    void shouldSaveRole() {
        Role saved = roleRepository.saveAndFlush(Role.builder().id(3).build());
        Role fromDb = roleRepository.getById(3);
        assertEquals(saved, fromDb);
    }

    @Test
    void shouldGetRoleById() {
        role = roleRepository.getById(1);
        assertThat(roleRepository.getById(role.getId())).isEqualTo(role);
    }

    @Test
    void shouldGetAllRoles() {
        List<Role> roles = roleRepository.findAll();
        assertNotNull(roles);
        assertThat(roles).hasSize(2);
    }

    @Test
    void shouldEditRole() {
        Role newRole = Role.builder()
                .id(1)
                .title("Administrator")
                .build();
        roleRepository.saveAndFlush(newRole);
        Role editedRole = roleRepository.getById(1);
        assertEquals("Administrator", editedRole.getTitle());
    }

    @Test
    void shouldDeleteRole() {
        roleRepository.deleteById(1);
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(1);
    }

}