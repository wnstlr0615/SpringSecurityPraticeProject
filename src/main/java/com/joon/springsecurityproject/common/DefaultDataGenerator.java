package com.joon.springsecurityproject.common;

import com.joon.springsecurityproject.account.Account;
import com.joon.springsecurityproject.account.AccountService;
import com.joon.springsecurityproject.book.Book;
import com.joon.springsecurityproject.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataGenerator implements ApplicationRunner {
    @Autowired
    AccountService accountService;
    @Autowired
    BookRepository bookRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account joon=createUser("joon");
        Account joon1=createUser("joon1");
        Book book= createBook("spring", joon);
        Book book1= createBook("jpa", joon1);

    }

    private Book createBook(String title, Account joon) {
        return bookRepository.save(Book.builder()
                        .title(title)
                        .author(joon)
                        .build());
    }

    private Account createUser(String username) {
        Account account=Account.builder()
                .username(username)
                .password("123")
                .role("USER")
                .build();
        return accountService.createUser(account);
    }
}
