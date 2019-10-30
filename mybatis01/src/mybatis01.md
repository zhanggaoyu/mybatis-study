## 聊聊框架(闲聊)
1. 什么是框架
   ```
   软件框架（software framework），通常指的是为了实现某个业界标准或完成特定基本任务的软件组件规范，也指为了实现某个软件组件规范时，提供规范所要求之基础功能的软件产品。
   框架的功能类似于基础设施，与具体的软件应用无关，但是提供并实现最为基础的软件架构和体系。软件开发者通常依据特定的框架实现更为复杂的商业运用和业务逻辑。这样的软件应用可以在支持同一种框架的软件系统中运行。
   简而言之，框架就是制定一套规范或者规则（思想），大家（程序员）在该规范或者规则（思想）下工作。或者说使用别人搭好的舞台来做编剧和表演。
   ```
   - 上面的话引用于百度百科, 对于我们来说, 框架就是一个半成品的项目, 它是我们软件开发中的一套解决方案, 不同的框架解决的是不同的问题

2. 框架的好处
   * 框架封装了很多的细节, 使开发者可以使用极简的方式实现功能, 极大的提高开发效率

3. 三层架构(与MVC不同)
   * 表现层
   * 业务层
   * 持久层

4. 持久层的技术解决方案
   1. JDBC技术:
      * Connection
      * PreparedStatement
      * ResultSet
   2. spring的JdbcTemplate
      * spring对JDBC的简单封装
   3. Apache的DBUtils
      * 也是对JDBC的简单封装
      
   * 上面的三个都不是框架
      - JDBC是规范
      - spring的JdbcTemplate和Apache的DBUtils是工具类
      
## MyBatis
1. mybatis介绍
   - mybatis是一个java持久层框架
   - 它封装了很多jdbc的细节, 使开发者只需要关注sql本身, 而不用关注注册驱动, 创建连接等过程
   - 使用ORM(对象关系映射)来实现结果集的封装

2. mybatis入门
   * 环境搭建
     1. 创建maven工程并导入mybatis坐标
        ```
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.15</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.11.1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.11.1</version>
        </dependency>
        ```
     2. 准备 数据库, 创建实体类和dao接口
        * 以前怎么创建的现在依旧怎么创建
     3. 创建mybatis主配置文件
        ```
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
        <configuration>
            <properties resource="database.properties"/>
            <typeAliases>
                <package name="cn.ann.bean"/>
            </typeAliases>
            <!-- 配置环境, default可以是任意值, 但是必须有 environment 的 id 是这个值 -->
            <environments default="development">
                <!-- 配置环境 -->
                <environment id="development">
                    <!-- 配置事务的类型 -->
                    <transactionManager type="JDBC"/>
                    <!-- 配置连接池 -->
                    <dataSource type="POOLED">
                        <property name="driver" value="${driver-class-name}"/>
                        <property name="url" value="${url}"/>
                        <property name="username" value="${username}"/>
                        <property name="password" value="${password}"/>
                    </dataSource>
                </environment>
            </environments>
            <!-- 配置映射文件的位置, 映射配置文件指的是每个dao接口对应的配置文件 -->
            <mappers>
                <mapper resource="cn/ann/mapper/UserMapper.xml"/>
            </mappers>
        </configuration>
        ```
        * environments标签在mybatis与spring整合之后就不用配了
     4. 创建映射配置文件
        ```
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
        <!-- 
           namespace使用dao接口(UserMapper)时, xml文件要与接口放在同一层目录中
           同时, id要和方法名一致
         -->
        <mapper namespace="cn.ann.mapper.UserMapper">
            <insert id="addUser" parameterType="User">
                insert into t_user(id, `name`, age) values (#{id},#{name},#{age})
            </insert>
        </mapper>
        ```
     5. 编写测试类(单元测试)
        ```
        public class ApplicationTest {
            private SqlSessionFactory factory;
        
            @Before
            public void before() throws IOException {
                InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
                factory = new SqlSessionFactoryBuilder().build(is);
            }
        
            @Test
            public void demo01() throws IOException {
                SqlSession session = factory.openSession();
                User user = new User(null, "赵六", 26);
                session.insert("addUser", user);
                session.commit();
                session.close();
            }
        
            @Test
                public void demo02() {
                    SqlSession session = factory.openSession();
                    UserMapper mapper = session.getMapper(UserMapper.class);
                    User user = new User(null, "田七", 27);
                    mapper.addUser(user);
                    System.out.println(user);
                    session.close();
                }
        
        }
        ```

     6. 注解方式
        1. mapper:
           ```
           @Select("select id, name, age from t_user where id = #{id}")
           User getUserById(Integer id);
           ```
        2. 核心配置文件文件:
           ```
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
            <configuration>
                <properties resource="database.properties"/>
                <environments default="development">
                    <environment id="development">
                        <transactionManager type="JDBC"/>
                        <dataSource type="POOLED">
                            <property name="driver" value="${driver-class-name}"/>
                            <property name="url" value="${url}"/>
                            <property name="username" value="${username}"/>
                            <property name="password" value="${password}"/>
                        </dataSource>
                    </environment>
                </environments>
                <!-- 配置映射文件的位置, 映射配置文件指的是每个dao接口对应的配置文件 -->
                <mappers>
                    <mapper class="cn.ann.mapper.UserMapper"/>
                </mappers>
            </configuration>
           ```
        3. 测试类
           ```
            @Test
            public void demo03() {
                SqlSession session = factory.openSession();
                UserMapper mapper = session.getMapper(UserMapper.class);
                User user = mapper.getUserById(1);
                System.out.println(user);
                session.close();
            }
           ```
      
3. 对测试类的分析: 
   * 项目目录

     | 项目目录                                                                                                 | 介绍                                                                                |
     |:-------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------|
     | ![项目目录](https://images.cnblogs.com/cnblogs_com/ann-zhgy/1558457/o_mybatisProjectStruct.png "项目目录") | mapper: 相当于Dao<br>UserMapper.xml: 映射文件<br>mybatis-config.xml: mybatis核心配置文件 |

   * 从单元测试中可以看出其需要引用的类有:
     * class Resources
     * class SqlSessionFactoryBuilder
     * interface SqlSessionFactory
     * interface SqlSession
   * 其中, sqlSession是非线程安全的, 所以在多线程环境中, 要避免将sqlSession定义为成员变量, 而是在每次用的时候再开一个sqlSession对象
