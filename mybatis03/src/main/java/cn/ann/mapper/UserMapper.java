package cn.ann.mapper;

import cn.ann.beans.User;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 12:47
 */
public interface UserMapper {
    List<User> getAll();

    List<User> getUsersByRoleId(Integer roleId);

    User getUserById(Integer id);

    void addUser(User user);
}
