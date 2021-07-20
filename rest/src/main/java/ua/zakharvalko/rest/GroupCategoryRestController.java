package ua.zakharvalko.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.domain.group.GroupOfCategories;
import ua.zakharvalko.service.GroupOfCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/groups-of-categories/")
public class GroupCategoryRestController {

    @Autowired
    GroupOfCategoryService groupOfCategoryService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> addGroup(@RequestBody @Validated GroupOfCategories groupOfCategories){
        if(groupOfCategories == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.groupOfCategoryService.addGroup(groupOfCategories);
            return new ResponseEntity<>(groupOfCategories, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> deleteGroup(@PathVariable Integer id) {
        GroupOfCategories group = this.groupOfCategoryService.getById(id);
        if(group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.groupOfCategoryService.deleteGroup(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOfCategories> getById(@PathVariable Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            GroupOfCategories group = this.groupOfCategoryService.getById(id);
            if(group == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(group, HttpStatus.OK);
            }
        }
    }

    public ResponseEntity<List<GroupOfCategories>> getAllGroups() {
        List<GroupOfCategories> groups = this.groupOfCategoryService.getAllGroups();
        if(groups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(groups, HttpStatus.OK);
        }
    }

}
