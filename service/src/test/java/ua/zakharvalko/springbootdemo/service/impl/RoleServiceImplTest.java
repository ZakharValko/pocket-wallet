package ua.zakharvalko.springbootdemo.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.zakharvalko.springbootdemo.dao.RoleRepository;
import ua.zakharvalko.springbootdemo.domain.Role;
import ua.zakharvalko.springbootdemo.service.RoleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RoleServiceImpl.class)
@RunWith(SpringRunner.class)
class RoleServiceImplTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Test
    public void shouldReturnRoleWhenSaved() {
        Role role = Role.builder().id(1).build();

        roleRepository.save(role);
        when(roleRepository.getById(1)).thenReturn(role);
        Role actual = roleService.getById(role.getId());

        assertEquals(role.getId(), actual.getId());
        verify(roleRepository).save(role);
    }

    @Test
    public void shouldDeleteRole() {
        Role role = Role.builder().id(1).build();

        when(roleRepository.getById(role.getId())).thenReturn(role);
        roleService.delete(role.getId());
        verify(roleRepository).delete(role.getId());
    }

    @Test
    public void shouldEditRole() {
        Role oldRole = Role.builder().id(1).title("Admin").build();
        Role newRole = Role.builder().id(1).title("Administrator").build();

        given(roleRepository.getById(oldRole.getId())).willReturn(newRole);
        roleService.update(newRole);

        verify(roleRepository).update(newRole);
    }

    @Test
    public void shouldReturnRoleById() {
        Role role = Role.builder().id(1).build();
        when(roleRepository.getById(1)).thenReturn(role);

        Role actual = roleService.getById(1);

        assertEquals(role.getId(), actual.getId());
        verify(roleRepository).getById(1);
    }

    @Test
    public void shouldReturnAllRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        when(roleRepository.getAll()).thenReturn(roles);

        List<Role> actual = roleService.getAll();

        assertEquals(roles, actual);
        verify(roleRepository).getAll();
    }
}