package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liuchou on 16/5/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ISeckillService seckillService;
    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list:{}",list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1000);
        logger.info("seckill:{}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        Exposer export =seckillService.exportSeckillUrl(1000);
        logger.info("export:{}",export);
        //export:Exposer{exposed=true, seckillId=1000, md5='370ac8bd6b7f9a55b9cf51bcb9c29cdb', now=0, start=0, end=0}
    }

    @Test
    public void testExecuteSeckill() throws Exception {
        Exposer exposer =seckillService.exportSeckillUrl(1000);
        logger.info("exposer={}",exposer);
        if (exposer.isExposed()){
            long phone = 18221040232L;
            String md5 = exposer.getMd5();
            long seckillId = exposer.getSeckillId();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
                logger.info("seckillExecution:{}",seckillExecution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillCloseException e1){
                logger.error(e1.getMessage());
            }
        }else {
            logger.warn("exposer={}",exposer);
        }

    }
    @Test
    public void testExecuteSeckillProcedure() {
        long seckillId = 1004l;
        long phone = 15811111122l;

        Exposer exposer = seckillService.exportSeckillUrl(seckillId);

        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            System.out.println(execution.getStateInfo());
        }

    }

}