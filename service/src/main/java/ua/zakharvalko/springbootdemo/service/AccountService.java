package ua.zakharvalko.springbootdemo.service;

import ua.zakharvalko.springbootdemo.domain.Account;

import java.util.Date;

public interface AccountService extends AbstractCrudService<Account> {

    double getCurrentBalanceOnDate(Integer id, Date date);

}
