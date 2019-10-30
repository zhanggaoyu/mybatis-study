package cn.ann.mapper;

import cn.ann.pojo.User;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 12:47
 */
public interface UserMapper {
    User getUserById(Integer id);
    List<User> getAll();
}
