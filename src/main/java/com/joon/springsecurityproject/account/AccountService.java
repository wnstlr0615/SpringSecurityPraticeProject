package com.joon.springsecurityproject.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account=accountRepository.findByUsername(username);
        if(account.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        Account findAccount=account.get();
        return  User.builder()
                    .username(findAccount.getUsername())
                    .password(findAccount.getPassword())
                    .roles(findAccount.getRole())
                    .build();
    }

    public Account createUser(Account account) {
        account.encodePassword(account);
        return  accountRepository.save(account);
    }
}
