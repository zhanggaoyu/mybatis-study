package cn.ann.mapper;

import cn.ann.bean.QueryVo;
import cn.ann.bean.User;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-25 20:22
 */
public interface UserMapper {
    void addUser(User user);

    void modifyUserById(User user);

    void deleteUserById(Integer id);

    List<User> getUsers();

    User getUserById(Integer id);

    List<User> getUsersByName(String name);

    Integer getUserCount();

    List<User> getUserByCondition(QueryVo vo);

    List<User> getUsersByIdsVo(QueryVo vo);

    List<User> getUsersByIds(Integer[] ids);

    List<User> getUsersByIdList(List<Integer> idList);
}
