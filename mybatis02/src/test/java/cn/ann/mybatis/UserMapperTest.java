package cn.ann.mybatis;

import cn.ann.bean.QueryVo;
import cn.ann.bean.User;
import cn.ann.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-25 20:27
 */
public class UserMapperTest {
    private SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        sqlSession = factory.openSession();
    }

    @After
    public void after() {
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void addUser() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User(null, "Jenny", 18);
        System.out.println(user);
        mapper.addUser(user);
        System.out.println(user);
    }

    @Test
    public void modifyUserById() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User(11, "Tom", 21);
        mapper.modifyUserById(user);
    }

    @Test
    public void deleteUserById() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteUserById(1);
    }

    @Test
    public void getUsers() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUsers();
        users.forEach(System.out::println);
    }

    @Test
    public void getUserById() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(2);
        System.out.println(user);
    }

    @Test
    public void getUsersByName() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUsersByName("%o%");
        users.forEach(System.out::println);
    }

    @Test
    public void getUserCount() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer count = mapper.getUserCount();
        System.out.println(count);
    }

    @Test
    public void getUserByCondition() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        QueryVo vo = new QueryVo();
        vo.setUser(new User(null, "Tom", null));
        List<User> users = mapper.getUserByCondition(vo);
        users.forEach(System.out::println);
    }

    @Test
    public void getUsersByIdsVo() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        QueryVo vo = new QueryVo();
        vo.setIds(new int[]{11, 12, 13, 14});
        List<User> users = mapper.getUsersByIdsVo(vo);
        users.forEach(System.out::println);
    }

    @Test
    public void getUsersByIds() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer[] ids = {11, 12, 13, 14};
        List<User> users = mapper.getUsersByIds(ids);
        users.forEach(System.out::println);
    }

    @Test
    public void getUsersByIdList() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<Integer> idList = Arrays.asList(11, 12, 13, 14);
        List<User> users = mapper.getUsersByIdList(idList);
        users.forEach(System.out::println);
    }
}
