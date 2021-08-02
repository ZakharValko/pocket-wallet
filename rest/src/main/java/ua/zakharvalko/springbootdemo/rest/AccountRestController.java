package ua.zakharvalko.springbootdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> addAccount(@RequestBody @Validated Account account) {
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.accountService.addAccount(account);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Integer id) {
        Account account = this.accountService.getById(id);
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.accountService.deleteAccount(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Account> editAccount(@RequestBody @Validated Account account) {
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.accountService.editAccount(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getById(@PathVariable("id") Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Account account = this.accountService.getById(id);
            if(account == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = this.accountService.getAllAccounts();
        if(accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-balance-on-date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getCurrentBalanceOnDate (@RequestParam("id") Integer id,
                                                           @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Double balance = this.accountService.getCurrentBalanceOnDate(id, date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

}
