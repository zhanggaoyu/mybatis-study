package cn.ann.mapper;

import cn.ann.beans.Role;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-28 19:42
 */
public interface RoleMapper {

    @Select("select * from t_role where id = #{id}")
    @Results(id = "roleMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "users", column = "id", many = @Many(
                    select = "cn.ann.mapper.UserMapper#getUsersByRoleId", fetchType = FetchType.LAZY
            ))
    })
    Role getRoleById(Integer id);

    @Select("select * from t_role where id in (select role_id from t_user_role where user_id = #{userId})")
    List<Role> getRolesByUserId(Integer userId);
}
