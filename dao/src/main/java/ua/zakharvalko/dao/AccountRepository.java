package ua.zakharvalko.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.zakharvalko.domain.account.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
