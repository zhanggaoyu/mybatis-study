package cn.ann.mapper;

import cn.ann.beans.Account;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 13:52
 */
public interface AccountMapper {
    List<Account> findAll();
    Account getAccountById(Integer id);
    List<Account> getAccountsByUserId(Integer userId);
}
