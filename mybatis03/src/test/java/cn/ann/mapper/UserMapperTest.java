package cn.ann.mapper;

import cn.ann.beans.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-27 13:25
 */
public class UserMapperTest {
    private InputStream in;
    private SqlSession sqlSession;
    private UserMapper mapper;

    @Before
    public void before() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void after() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void getAll() {
        List<User> users = mapper.getAll();
        users.forEach(System.out::println);
    }

    @Test
    public void getUserById() {
        User user = mapper.getUserById(1);
        System.out.println(user);
        System.out.println(user.getAccounts());
        System.out.println(user.getRoles());
    }

    @Test
    public void addUser() {
        User user = new User(null, "老王", 45);
        mapper.addUser(user);
        System.out.println(user);
    }
}
