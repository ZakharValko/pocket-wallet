package ua.zakharvalko.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.domain.category.Category;
import ua.zakharvalko.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> addCategory(@RequestBody @Validated Category category) {
        if(category == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.categoryService.addCategory(category);
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> deleteCategory(@PathVariable Integer id) {
        Category category = this.categoryService.getById(id);
        if(category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.categoryService.deleteCategory(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getById(@PathVariable Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Category category = categoryService.getById(id);
            if(category == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> editCategory(@RequestBody @Validated Category category) {
        if(category == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.categoryService.editCategory(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = this.categoryService.getAllCategories();
        if(categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

}
