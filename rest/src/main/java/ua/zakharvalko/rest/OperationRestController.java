package ua.zakharvalko.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.service.OperationService;

import java.util.List;

@RestController
@RequestMapping("/api/operations/")
public class OperationRestController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> addOperation(@RequestBody @Validated Operation operation) {
        if(operation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.operationService.addOperation(operation);
            return new ResponseEntity<>(operation, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> getById(@PathVariable Integer id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Operation operation = this.operationService.getById(id);
            if(operation == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(operation, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getAllOperation() {
        List<Operation> operations = this.operationService.getAllOperations();
        if(operations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

}
