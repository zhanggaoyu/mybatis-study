package cn.ann;

import cn.ann.bean.User;
import cn.ann.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-24 11:07
 */
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
        User user = new User();
        user.setId(null);
        user.setName("赵六");
        user.setAge(26);
        session.insert("addUser", user);
        session.commit();
        session.close();
    }

    @Test
    public void demo02() {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setId(null);
        user.setName("田七");
        user.setAge(27);
        mapper.addUser(user);
        System.out.println(user);
        session.close();
    }

    @Test
    public void demo03() {
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);
        session.close();
    }


}
