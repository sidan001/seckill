package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by liuchou on 16/5/11.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class ISuccessKilledDaoTest {

    @Resource
    private ISuccessKilledDao dao;

    @Test
    public void testInsertSuccessKilled() throws Exception {

        long seckillid = 1000L;
        long phoneNo = 13892689248L;
        int i = dao.insertSuccessKilled(seckillid,phoneNo);
        System.out.println(i);
        int ii = dao.insertSuccessKilled(seckillid,phoneNo);
        System.out.println(ii);


    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long seckillid = 1000L;
        long phoneNo = 13892689248L;
        dao.queryByIdWithSeckill(seckillid,phoneNo);

    }
}