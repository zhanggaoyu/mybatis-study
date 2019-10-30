package cn.ann.mapper;

import cn.ann.beans.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * create by 88475 with IntelliJ IDEA on 2019-10-29 10:20
 */
public class RoleMapperTest {
    private InputStream in;
    private SqlSession sqlSession;
    private RoleMapper mapper;

    @Before
    public void before() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(RoleMapper.class);
    }

    @After
    public void after() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void getRoleById() {
        Role role = mapper.getRoleById(1);
        System.out.println(role);
        System.out.println(role.getUsers());
    }
}
