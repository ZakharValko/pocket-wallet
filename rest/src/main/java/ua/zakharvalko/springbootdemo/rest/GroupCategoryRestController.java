package ua.zakharvalko.springbootdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.GroupOfCategories;
import ua.zakharvalko.springbootdemo.service.GroupOfCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/groups-of-categories")
public class GroupCategoryRestController {

    @Autowired
    private GroupOfCategoryService groupService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> addGroup(@RequestBody @Validated GroupOfCategories groupOfCategories){
        if(groupOfCategories == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.groupService.saveOrUpdate(groupOfCategories);
            return new ResponseEntity<>(groupOfCategories, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> deleteGroup(@PathVariable Integer id) {
        GroupOfCategories group = this.groupService.getById(id);
        if(group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.groupService.delete(id);
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> editGroup(@RequestBody @Validated GroupOfCategories group) {
        if(group == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.groupService.saveOrUpdate(group);
            return new ResponseEntity<>(group, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> getById(@PathVariable Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            GroupOfCategories group = this.groupService.getById(id);
            if(group == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(group, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupOfCategories>> getAllGroups() {
        List<GroupOfCategories> groups = this.groupService.getAll();
        if(groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
    }

}
