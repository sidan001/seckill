package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liuchou on 16/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class ISeckillDaoTest {

    @Autowired
    private ISeckillDao dao;
    @Test
    public void testReduceNumber() throws Exception {

        long id = 1000L;
        Date killTime = new Date();
        int i = dao.reduceNumber(id,killTime);
        System.out.println(i);

    }

    @Test
    public void testQueryById() throws Exception {
        long id =1000;
        Seckill seckill = dao.queryById(id);
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {

       List<Seckill> list =  dao.queryAll(1,5);

        System.out.println(list.toString());

    }
}