package cn.ann.mapper;

import cn.ann.beans.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 12:47
 */
public interface UserMapper {
    @Select("select * from t_user")
    List<User> getAll();

    @Select("select * from t_user where id in (select user_id from t_user_role where role_id = #{roleId})")
    List<User> getUsersByRoleId(Integer roleId);

    @Select("select * from t_user where id = #{id}")
    @Results(id = "userMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "accounts", column = "id",
                    many = @Many(select = "cn.ann.mapper.AccountMapper.getAccountsByUserId",
                            fetchType = FetchType.LAZY))
    })
    User getUserById(Integer id);

    @Insert("insert into t_user values (#{id}, #{name}, #{age})")
    void addUser(User user);

    @Update("update t_user set name = #{name}, age = #{age} where id = #{id}")
    void modifyUser(User user);
}
