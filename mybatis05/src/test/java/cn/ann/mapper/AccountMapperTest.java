package cn.ann.mapper;

import cn.ann.beans.Account;
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
 * create by 88475 with IntelliJ IDEA on 2019-10-30 9:47
 */
public class AccountMapperTest {
    private InputStream in;
    private SqlSession sqlSession;
    private AccountMapper mapper;

    @Before
    public void before() throws IOException {
        in = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        mapper = sqlSession.getMapper(AccountMapper.class);
    }

    @After
    public void after() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }

    @Test
    public void findAll() {
        List<Account> accounts = mapper.findAll();
        accounts.forEach((account) -> {
            System.out.println(account);
            System.out.println(account.getUser());
        });
    }

    @Test
    public void getAccountById() {
        Account account = mapper.getAccountById(1);
        System.out.println(account);
        System.out.println(account.getUser());
        System.out.println(account.getUser().getAccounts());
    }
}
