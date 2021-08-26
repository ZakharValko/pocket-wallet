package ua.zakharvalko.springbootdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.dao.UserRepository;
import ua.zakharvalko.springbootdemo.domain.Account;
import ua.zakharvalko.springbootdemo.service.AccountService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> addAccount(@RequestBody @Validated Account account) {
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.accountService.save(account);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Integer id, Principal principal) {
        Account account = this.accountService.getById(id);
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            checkAuth(principal, id);
            this.accountService.delete(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Account> editAccount(@RequestBody @Validated Account account, Principal principal) {
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Integer id = account.getId();
            checkAuth(principal, id);
            this.accountService.update(account);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getById(@PathVariable("id") Integer id, Principal principal) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            checkAuth(principal, id);
            Account account = this.accountService.getById(id);
            if(account == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = this.accountService.getAll();
        if(accounts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-balance-on-date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getCurrentBalanceOnDate (@RequestParam("id") Integer id,
                                                           @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                                           Principal principal) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        checkAuth(principal, id);
        double balance = this.accountService.getCurrentBalanceOnDate(id, date);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    public void checkAuth(Principal principal, Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(!principal.getName().equals(userRepository.getById(accountService.getById(id).getUser_id()).getUsername())) {
            if(auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("Admin"))){
                throw new SecurityException("Access denied");
            }
        }
    }

}
