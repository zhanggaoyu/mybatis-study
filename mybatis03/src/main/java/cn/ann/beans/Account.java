package cn.ann.beans;

import java.io.Serializable;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 11:54
 */
public class Account implements Serializable {
    private static final long serialVersionUID = -5436012481440032985L;

    private Integer id;
    private String accountName;
    private Double accountMoney;
    private Integer userId;

    private User user;

    public Account() {
    }

    public Account(Integer id, String accountName, Double accountMoney, Integer userId, User user) {
        this.id = id;
        this.accountName = accountName;
        this.accountMoney = accountMoney;
        this.userId = userId;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", accountMoney=" + accountMoney +
                '}';
    }
}
