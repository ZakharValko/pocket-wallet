package ua.zakharvalko.springbootdemo.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.Operation;
import ua.zakharvalko.springbootdemo.domain.specification.OperationFilter;
import ua.zakharvalko.springbootdemo.service.OperationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationRestController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> addOperation(@RequestBody @Validated Operation operation, Principal principal) {
        if(operation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Integer id = operation.getId();
            checkAuth(principal, id);
            this.operationService.saveOrUpdate(operation);
            return new ResponseEntity<>(operation, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> deleteOperation(@PathVariable Integer id, Principal principal){
        Operation operation = operationService.getById(id);
        if(operation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            checkAuth(principal, id);
            this.operationService.delete(id);
            return new ResponseEntity<>(operation, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> editOperation(@RequestBody @Validated Operation operation, Principal principal) {
        if(operation == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Integer id = operation.getId();
            checkAuth(principal, id);
            this.operationService.saveOrUpdate(operation);
            return new ResponseEntity<>(operation, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Operation> getById(@PathVariable Integer id, Principal principal) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            checkAuth(principal, id);
            Operation operation = this.operationService.getById(id);
            if(operation == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(operation, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<Operation>> getAllOperation() {
        List<Operation> operations = this.operationService.getAll();
        if(operations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-operation-by-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getOperationByFilter(@RequestBody OperationFilter filter, Principal principal) {
        if (filter == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            List<Operation> operations = this.operationService.getOperationsByFilter(filter);
            if (operations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                Integer id = filter.getAccount().getOperations().get(0).getId();
                checkAuth(principal, id);
                return new ResponseEntity<>(operations, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/get-total-by-filter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getTotalExpensesByFilter(@RequestBody OperationFilter filter, Principal principal) {
        if(filter == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Integer id = filter.getAccount().getOperations().get(0).getId();
            checkAuth(principal, id);
            double total = this.operationService.getTotalExpensesByFilter(filter);
            return new ResponseEntity<>(total, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/transfer-btw-accounts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> transferBetweenAccounts(@RequestBody Operation operation, Principal principal) {
        if(operation == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Integer id = operation.getId();
            checkAuth(principal, id);
            List<Operation> operations = operationService.transferBetweenAccounts(operation);
            return new ResponseEntity<>(operations, HttpStatus.OK);
        }
    }

    public void checkAuth(Principal principal, Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!principal.getName().equals(operationService.getById(id).getAccount().getUser().getUsername())) {
            if(auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("Admin"))){
                throw new SecurityException("Access denied");
            }
        }
    }
}
