package ua.zakharvalko.springbootdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.OperationType;
import ua.zakharvalko.springbootdemo.service.OperationTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/operation-types")
public class OperationTypeRestController {

    @Autowired
    private OperationTypeService operationTypeService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationType> addType(@RequestBody @Validated OperationType type) {
        if(type == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.operationTypeService.addOperationType(type);
            return new ResponseEntity<>(type, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationType> deleteOperationType(@PathVariable("id") Integer id) {
        OperationType type = this.operationTypeService.getById(id);
        if(type == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.operationTypeService.deleteOperationType(id);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationType> getById(@PathVariable("id") Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            OperationType type = this.operationTypeService.getById(id);
            if(type == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(type, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<OperationType> editOperationType(@RequestBody @Validated OperationType type) {
        if(type == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.operationTypeService.editOperationType(type);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OperationType>> getAllOperationTypes() {
        List<OperationType> types = this.operationTypeService.getAllOperationTypes();
        if(types.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(types, HttpStatus.OK);
        }
    }
}
