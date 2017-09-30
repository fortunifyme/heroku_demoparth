package com.gjj.igden.dao.daoimpl;

import com.gjj.igden.dao.UserDao;
import com.gjj.igden.model.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("userDao")
public class UserDaoImpl implements UserDao {


    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Account findAccountByName(String accountName) {
        return entityManager
                .createQuery("select u from Account u where u.accountName = :accountName", Account.class)
                .setParameter("accountName", accountName)
                .getSingleResult();
    }
}
