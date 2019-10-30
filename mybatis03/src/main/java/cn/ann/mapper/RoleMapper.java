package cn.ann.mapper;

import cn.ann.beans.Role;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-28 19:42
 */
public interface RoleMapper {
    Role getRoleById(Integer id);

    List<Role> getRolesByUserId(Integer userId);
}
