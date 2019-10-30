package cn.ann.mapper;

import cn.ann.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-24 16:21
 */
@Mapper
public interface UserMapper {
    void addUser(User user);

    User getUserById(Integer id);

}
