package cn.ann.mapper;

import cn.ann.beans.Account;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 13:52
 */
@Mapper
public interface AccountMapper {

    @Select("select * from t_account")
    @ResultMap("accountMap")
    List<Account> findAll();

    @Select("select * from t_account where id = #{id}")
    @Results(id = "accountMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_id", property = "user",
                    one = @One(
                            select = "cn.ann.mapper.UserMapper.getUserById", fetchType = FetchType.EAGER
                    ))
    })
    Account getAccountById(Integer id);

    @Select("select * from t_account where user_id = #{userId}")
    List<Account> getAccountsByUserId(Integer userId);
}
