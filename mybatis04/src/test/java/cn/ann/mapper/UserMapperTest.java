package cn.ann.mapper;

import cn.ann.pojo.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
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
 * create by 88475 with IntelliJ IDEA on 2019-10-29 10:50
 */
public class UserMapperTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
    }

    @After
    public void after() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void getUserByIdFristCache() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.getUserById(1);
        System.out.println(user1);
        User user2 = mapper.getUserById(1);
        System.out.println(user2);
        System.out.println(user1==user2);
    }

    @Test
    public void getUserByIdSecondCache() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user1 = mapper.getUserById(1);
        System.out.println(user1);
        sqlSession.close();
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
        User user2 = mapper.getUserById(1);
        System.out.println(user2);
        System.out.println(user1==user2);
    }
    @Test
    public void getAll() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Page<Object> page = PageHelper.startPage(2, 5);
        List<User> users = mapper.getAll();
        PageInfo info = new PageInfo(users);
        users.forEach(System.out::println);
        System.out.println(page.toString());
        System.out.println(info);
    }

    @Test
    public void testBatch() {
        sqlSession.close();
        sqlSession = factory.openSession(ExecutorType.BATCH);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    }
}
