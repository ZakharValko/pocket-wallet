package ua.zakharvalko.springbootdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.Role;
import ua.zakharvalko.springbootdemo.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> addRole(@RequestBody @Validated Role role) {
        if(role == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.roleService.addRole(role);
            return new ResponseEntity<>(role, HttpStatus.CREATED);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> deleteRole(@PathVariable("id") Integer id) {
        Role role = this.roleService.getById(id);
        if(role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.roleService.deleteRole(id);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Role> editRole(@RequestBody @Validated Role role) {
        if(role == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.roleService.editRole(role);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> getById(@PathVariable("id") Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Role role = this.roleService.getById(id);
            if(role == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(role, HttpStatus.OK);
            }
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = this.roleService.getAllRoles();
        if(roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }

    }
}
