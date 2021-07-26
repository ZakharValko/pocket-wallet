package ua.zakharvalko.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.domain.account.Account;
import ua.zakharvalko.domain.operation.FilterOperation;
import ua.zakharvalko.domain.operation.Operation;
import ua.zakharvalko.service.AccountService;
import ua.zakharvalko.service.OperationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationRestController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> addOperation(@RequestBody @Validated Operation operation) {
        if(operation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.operationService.addOperation(operation);
            return new ResponseEntity<>(operation, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getAllOperation() {
        List<Operation> operations = this.operationService.getAllOperations();
        if(operations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-by-group/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getOperationByGroupOfCategories(@PathVariable Integer id) {
        List<Operation> operations = this.operationService.getAllOperationsByGroupOfCategories(id);
        if(operations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-by-interval", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getOperationsByTimeInterval(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                                       @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to){
        List<Operation> operations = this.operationService.getAllOperationsByTimeInterval(from, to);
        if(operations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-by-id-and-interval", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getOperationsByAccountAndTimeInterval(@RequestParam("id") Integer id,
                                                                                 @RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                                                 @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Operation> operations = this.operationService.getOperationsByAccountAndTimeInterval(id, from, to);
        if(operations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-operation-by-filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getOperationByFilter(@RequestParam(value = "account", required = false) Integer account,
                                                          @RequestParam(value = "category", required = false) Integer category,
                                                          @RequestParam(value = "group", required = false) Integer group,
                                                          @RequestParam(value = "currency", required = false) Integer currency,
                                                          @RequestParam(value = "operation-type", required = false) Integer operationType,
                                                          @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                          @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date to) {

        List<Operation> filteredOperation = this.operationService.getOperationByFilter(account, category, group, currency, operationType, from, to);
        return new ResponseEntity<>(filteredOperation, HttpStatus.OK);
    }
}
