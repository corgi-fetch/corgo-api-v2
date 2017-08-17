package com.corgo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import com.corgo.model.Account;

public interface AccountRepository extends Repository<Account, String> {
	void delete(Account deleted);
    List<Account> findAll();
    Optional<Account> findOne(String id);
    Account save(Account saved);
    Account findByUsername(String username);
}
