## mybatis注解开发
------
1. 方法上的注解
   1. 原来xml中的\<select\>, \<update\>, \<insert\>和\<delete\>分别对应注解
      * @select("sql语句")
      * @update("sql语句")
      * @insert("sql语句")
      * @delete("sql语句")
   2. 结果映射配置: 原来xml中的\<resultMap\>对应 @results
      ```
      @Select("select * from t_account where id = #{id}")
      @Results(id = "accountMap", value = {
              @Result(id = true, column = "id", property = "id"),
              @Result(column = "user_id", property = "user",
                      one = @One(
                              select = "cn.ann.mapper.UserMapper.getUserById", fetchType = FetchType.EAGER
                      ))
      })
      Account getAccountById(Integer id);
      ```
      * \<resultMap\>中的\<id\>对应```@Result(id = true, column = "id", property = "id")```
      * \<resultMap\>中的\<result\>对应@Result注解去掉id属性, 或是将id属性设为false
      * \<resultMap\>中的\<association\>对应@Result注解中的 one = @one(...), 其中, property指定属性, column指定作为参数传入方法的列
      ```
      @Select("select * from t_user where id = #{id}")
      @Results(id = "userMap", value = {
              @Result(id = true, property = "id", column = "id"),
              @Result(property = "accounts", column = "id",
                      many = @Many(select = "cn.ann.mapper.AccountMapper.getAccountsByUserId",
                              fetchType = FetchType.LAZY))
      })
      User getUserById(Integer id);
      ```
      * \<resultMap\>中的\<collection\>对应@Result注解中的 many = @many(...), 其中, property指定属性, column指定作为参数传入方法的列
      ```@ResultMap("accountMap")```
      * @ResultMap相当于\<select\>中的resultMap属性, 它需要一个已经存在的结果映射集的 id 值
2. 接口上的注解
   1. @Mapper
      * 我并不清楚这个注解具体有什么作用, 可能是标明这个接口是一个mapper类
   2. @CacheNamespace
      * 配置是否开启缓存
   3. @CacheNamespaceRef
      * 引用其他命名空间的缓存
