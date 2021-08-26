package ua.zakharvalko.springbootdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.zakharvalko.springbootdemo.domain.Currency;
import ua.zakharvalko.springbootdemo.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyRestController {

    @Autowired
    private CurrencyService currencyService;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> addCurrency(@RequestBody @Validated Currency currency) {
        if(currency == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.currencyService.save(currency);
            return new ResponseEntity<>(currency, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> deleteCurrency(@PathVariable("id") Integer id) {
        Currency currency = this.currencyService.getById(id);
        if(currency == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.currencyService.delete(id);
            return new ResponseEntity<>(currency, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Currency> editCurrency(@RequestBody @Validated Currency currency) {
        if(currency == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            this.currencyService.update(currency);
            return new ResponseEntity<>(currency, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Currency> getById(@PathVariable("id") Integer id) {
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Currency currency = this.currencyService.getById(id);
            if(currency == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(currency, HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = this.currencyService.getAll();
        if(currencies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(currencies, HttpStatus.OK);
        }
    }
}
