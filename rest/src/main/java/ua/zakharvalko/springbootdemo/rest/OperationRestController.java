package ua.zakharvalko.springbootdemo.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.service.OperationService;

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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> deleteOperation(@PathVariable Integer id){
        Operation operation = operationService.getById(id);
        if(operation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.operationService.deleteOperation(id);
            return new ResponseEntity<>(operation, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> editOperation(@RequestBody @Validated Operation operation) {
        if(operation == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.operationService.editOperation(operation);
            return new ResponseEntity<>(operation, HttpStatus.OK);
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

    @RequestMapping(value = "/get-total-by-filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getTotalExpensesByFilter(@RequestParam(value = "account") Integer account,
                                                   @RequestParam(value = "category", required = false) Integer category,
                                                   @RequestParam(value = "group", required = false) Integer group,
                                                   @RequestParam(value = "currency", required = false) Integer currency,
                                                   @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                   @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date to) {
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long total = this.operationService.getTotalExpensesByFilter(account, category, group, currency, from, to);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-cashflow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getCashFlow(@RequestParam(value = "account") Integer account,
                                              @RequestParam(value = "from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                              @RequestParam(value = "to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to) {
        if(account == null || from == null || to == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long cashFlow = this.operationService.getCashFlow(account, from, to);
        return new ResponseEntity<>(cashFlow, HttpStatus.OK);
    }

    @RequestMapping(value = "/transfer-btw-accounts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> transferBetweenAccounts(@RequestBody @Validated Operation operation,
                                                                   @RequestParam(value = "account") Integer id) {
        if(operation == null && id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Operation> operations = this.operationService.transferBetweenAccounts(operation, id);
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }
}
