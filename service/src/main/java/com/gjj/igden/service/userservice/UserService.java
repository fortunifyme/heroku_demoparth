package com.gjj.igden.service.userservice;

import com.gjj.igden.dao.UserDao;
import com.gjj.igden.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        Account account = userDao.findAccountByName(accountName);

        if (account == null) {
            throw new UsernameNotFoundException(String.format("User with username=\"%s\" does not exist", accountName));
        }

        if (!account.isEnabled()) {
            throw new IllegalArgumentException(String.format("User not enabled"));
        }
        return account;
    }
}
