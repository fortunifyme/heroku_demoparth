package com.gjj.igden.dao;

import com.gjj.igden.model.Account;

public interface UserDao {

    Account findAccountByName(String accountName);
}
