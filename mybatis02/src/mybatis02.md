## 配置文件&事务
1. 核心配置文件
   1. properties标签(设置键值对或引入外部properties文件)
      1.  resource: 填写properties文件的相对路径即可, 使用 ${key} 即可获取相对应的value
      2.  url: 填写properties文件的网络地址, 如: file:///E:/idea/framework/mybatis02/src/main/resources/database.properties
   2. setting标签(设置运行时行为)
      ```
      <settings>
          <!-- 是否开启驼峰命名规则自动映射 -->
          <setting name="mapUnderscoreToCamelCase" value="true"/>
      </settings>
      ```
      1. name写参数名, value写参数值
      2. 这些参数可以在官方文档查询: 点击[链接](https://mybatis.org/mybatis-3/zh/configuration.html#settings)即可查询
   3. typeAliases标签(配置别名)
      1. ```<typeAlias type="类的完整类名" alias="别名"/>```
      2. ```<package name="包名"/>```: 此包下的所有类都可以直接使用类名
   4. mybatis的连接池类型, 即 dataSource 中 type 的取值
      1. POOLED: 采用数据库连接池, 获取连接时从池子中获取, 用完归还给连接池
      2. UNPOOLED: 需要获取连接时再创建, 用完关闭连接
      3. JNDI: 采用服务器提供的JNDI技术实现，来获取DataSource对象，不同的服务器所能拿到DataSource是不一样。
         * 如果不是 web 或 war 工程是不能用的
         * tomcat采用的是dbcp连接池
2. 事务: 数据库管理系统执行过程中的一个逻辑单位，由一个有限的数据库操作序列构成
   1. 事务的四大特性: ACID
      * 原子性（Atomicity）：事务作为一个整体被执行，包含在其中的对数据库的操作要么全部被执行，要么都不执行
      * 一致性（Consistency）：事务应确保数据库的状态从一个一致状态转变为另一个一致状态。一致状态的含义是数据库中的数据应满足完整性约束
      * 隔离性（Isolation）：多个事务并发执行时，一个事务的执行不应影响其他事务的执行
      * 持久性（Durability）：已被提交的事务对数据库的修改应该永久保存在数据库中
   2. 不考虑隔离性会产生的3个问题
      1. 脏读: 一个事务读到了另一个事务未提交的数据.
      2. 幻读: 一个事务读到了另一个事务已经提交的（insert）数据.导致多次查询的结果不一致
      3. 不可重复读: 一个事务读到了另一个事务已经提交(update)的数据.引发一个事务中的多次查询结果不一致.
   3. 解决办法：四种隔离级别
      * read uncommitted : 脏读，不可重复读，虚读都可能发生.
      * read committed : 避免脏读,但是不可重复读和虚读有可能发生.
      * repeatable read : 避免脏读和不可重复读,但是虚读有可能发生的.
      * serializable : 避免脏读,不可重复读和虚读.(串行化的-不可能出现事务并发访问)
      * mybatis 通过 sqlSession 对象的 commit() 方法和 rollback() 方法实现事务的提交和回滚
## 动态sql
1. sql
   1. insert
      ```
      <insert id="addUser" parameterType="User">
          <selectKey resultType="Integer" keyColumn="id" order="AFTER" keyProperty="id">
              select last_insert_id();
          </selectKey>
          insert into t_user(id, `name`, age) values (#{id},#{name},#{age});
      </insert>
      ```
      * id: 唯一值, 一般对应mapper接口中的方法名
      * parameterType: 传入的参数, 因为在核心配置文件里面配置了别名, 所以直接写的类名
        * 其类型可以是基本数据类型、list、map、对象等, 同时也可以传入多个参数
      * #{val}:
        1. 如果只传入了一个参数, 中间的字符串可以是任意值
        2. 如果传入的是对象, 必须是属性名
        3. 当传入多个参数时, 取值可以用 param1、param2...
        4. 如果方法声明的参数列表中用 @Param() 注解规定了 key , 可以用#{key的值}取值
      * selectKey 中, keyColumn是列名, keyProperty是属性名
      * order表示的是数据库是先生成 id 再插入数据还是先插入数据再生成 id, mysql写AFTER就好
   2. update
      ```
      <update id="modifyUserById" parameterType="User">
          update t_user set name = #{name}, age = #{age} where id = #{id};
      </update>
      ```
   3. delete
      ```
      <delete id="deleteUserById" parameterType="Integer">
          delete from t_user where id = #{id};
      </delete>
      ```
      * 此处#{}中间可以随便写
   4. select
      1. selectList
         ```
         <select id="getUsers" resultType="User">
             select * from t_user;
         </select>
         ```
         * resultType: 返回结果类型, 其类型可以是基本数据类型及其包装类, 对象, map
           * 注意:
             1. 基本数据类型用于返回单个结果
             2. 对象用于返回查询对象或对象集合
             3. 返回map时
                1. 如果map泛型是<String, Object>, 将会用列名作为key, 用列的值作为value
                2. 如果map泛型是<String, bean(指的是封装在pojo或者是bean中的对象)>, 将会以对象作为value, key可以在mapper接口的方法上使用@MapKey()指定
      2. selectOne
         ```
         <select id="getUserById" parameterType="Integer" resultType="User">
             select * from t_user where id = #{id};
         </select>
         ```
      3. 模糊查询
         ```
         <select id="getUsersByName" parameterType="String" resultType="cn.ann.bean.User">
             select * from t_user where name like '%'#{name}'%';
             <!-- select * from t_user where name like '%${name}%';
             select * from t_user where name like #{name}; -->
         </select>
         ```
         1. #{name}  
           ![#{name}](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o__%7B%7D.png)
         2. "%"#{name}"%"  
           !["%"#{name}"%"](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_'_'_%7B%7D'_'.png)
         3. '%${name}%'  
           !['%${name}%'](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_$%7B%7D.png)
         * 以上三种写法都可以得到预期的结果, 但是从截图我们可以看到, a和b用的是预编译(PrepareStatement对象), c使用的是字符串拼接(Statement)
         * b中必须使用 " " 包裹 % 才有效, 另外, b写法在idea中有红色波浪线, 不知道是啥原因
2. 动态sql
   1. 准备: 创建queryVo类
      1. queryVo 是sql语句条件的集合, 里面存储了执行sql时需要用到的条件
   2. sql片段
      1. 创建
         ```
         <sql id="query">
             select * from t_user
         </sql>
         ```
      2. 引用  
         ```<include refid="query"/>```
         * 注意:
           1. 创建和引用的id要一致
           2. sql片段相当于是文本的替换, 所以不要加分号(;)
   3. if
      ```
      <select id="getUserByCondition" resultType="User" parameterType="User">
          select * from t_user where 1=1 
          <if test="name != null">
              and name = #{name}
          </if>
          <if test="age != null">
              and age = #{age}
          </if>
      </select>
      ```
      * test里面写添加即可. 注意: 字段需要和参数对象的属性名一致
   4. where
      ```
      <select id="getUserByCondition" resultType="User" parameterType="QueryVo">
          select * from t_user
          <where>
              <if test="user.name != null">
                  and name = #{user.name}
              </if>
              <if test="user.age != null">
                  and age = #{user.age}
              </if>
          </where>
      </select>
      ```
      * 用where标签包裹之后, 上面的sql语句就不能写where了
      * where里面的sql语句的and只能写在前面, 不能写在后面
   5. set
      ```
      <update id="modifyUserById" parameterType="cn.ann.bean.User">
          update t_user
          <set>
              <if test="name!=null">
                  name = #{name},
              </if>
              <if test="age!=null">
                  age = #{age},
              </if>
          </set>
          where id = #{id};
      </update>
      ```
      * 用set标签包裹之后, 上面的sql语句就不能写set了
      * set里面的sql语句的 "," 只能写在后面, 不能写在前面
   6. foreach
      ```
      <select id="getUsersByIdsVo" parameterType="QueryVo" resultType="User">
          <include refid="query"/>
          <where>
              <foreach collection="ids" open="id in (" close=");" separator=", " item="id">
                  #{id}
              </foreach>
          </where>
      </select>
      ```
      * 因为传入的是QueryVo, 所以forEach的collection属性的值是属性名即可
      * 两个坑
        1. 当传入的参数是数组时
           ```
           <select id="getUsersByIds" parameterType="Integer" resultType="User">
               <include refid="query"/>
               <where>
                   <foreach collection="array" open="id in (" close=");" separator=", " item="id">
                       #{id}
                   </foreach>
               </where>
           </select>
           ```
           * 
        2. 当传入的参数是列表时
           ```
           <select id="getUsersByIdList" parameterType="Integer" resultType="User">
               <include refid="query"/>
               <where>
                   <foreach collection="list" open="id in (" close=");" separator=", " item="id">
                       #{id}
                   </foreach>
               </where>
           </select>
           ```
           * forEach的collection属性的值必须是list
   7. choose
      ```
      <select id="getUserByCondition" resultType="User" parameterType="QueryVo">
          select * from t_user
          <where>
              <choose>
                  <when test="user.name != null">
                      name = #{user.name}
                  </when>
                  <when test="user.age != null">
                      age = #{user.age}
                  </when>
                  <otherwise>
                      1=1
                  </otherwise>
              </choose>
          </where>
      </select>
      ```
      * choose表示的是选一个, 相当于switch-case
   8. trim
      ```
      <select id="getUserByCondition" resultType="User" parameterType="QueryVo">
          select * from t_user
          <trim prefix="where " prefixOverrides="and" suffix="" suffixOverrides="">
              <if test="user.name != null">
                  and name = #{user.name}
              </if>
              <if test="user.age != null">
                  and age = #{user.age}
              </if>
          </trim>
      </select>
      ```
      * prefix: 前缀. 再结果字符串前面加的东西
      * prefixOverrides: 如果编译结果前面有该字符串, 就去掉
      * suffix: 后缀. 再结果字符串后面加的东西
      * suffixOverrides: 如果编译结果后面有该字符串, 就去掉
   9. bind
      ```
      <select id="getUsersByName" parameterType="String" resultType="cn.ann.bean.User">
          <bind name="_name" value="'%' + name + '%'"/>
          select * from t_user where name like #{_name};
      </select>
      ```
      * bind可以对参数进行处理, 上面代码就是处理成模糊查询
