package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.ISeckillDao;
import org.seckill.dao.ISuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatusEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuchou on 16/5/24.
 */
@Service
public class SeckillServiceImpl implements ISeckillService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISeckillDao seckillDao;
    @Autowired
    private ISuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;
    private  final String slat = "123asdfoasdlfk!@#$)*&(^(|+)_!@&";

    private  String getMD5(long seckillId){
        String base = seckillId + "/" +slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return  md5;

    }
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //优化点:缓存优化:超时的基础上维护一致性
        /**
         * get from cache
         * if null
         * get db
         * else
         *      put cache
         * logic
         */
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null){
                return  new Exposer(false,seckillId);
            }else{
                String result = redisDao.putSeckill(seckill);
            }
        }



        Date satrtTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();

        if (nowTime.getTime() < satrtTime.getTime() || nowTime.getTime() > endTime.getTime()){
            return  new Exposer(false,seckillId,nowTime.getTime(),satrtTime.getTime(),endTime.getTime());
        }

        //转换特定字符串的过程,不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true,seckillId,md5);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        try {
            if (md5 == null || ! md5.equals(getMD5(seckillId))){
                throw new SeckillException("seckill data rewrite");
            }
            Date nowTime = new Date();
            //记录购买行为
            int inserCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

            if (inserCount <= 0) {
                //重复秒杀
                throw new RepeatKillException(SeckillStatusEnum.REPEAT_KILL.getStatusInfo());
            } else {
                //减库存  热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);

                if (updateCount <= 0) {
                    //rollback
                    throw new SeckillCloseException(SeckillStatusEnum.END.getStatusInfo());
                } else {
                    //秒杀成功  commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatusEnum.SUCCESS, successKilled);
                }

            }
        }catch (SeckillCloseException e) {
            throw e;
        }catch (RepeatKillException e1) {
            throw e1;
        }catch (Exception e2) {
            //转化未运行期异常,为了spring事物回滚
            throw new SeckillException("seckill inner Error:" + e2.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5) {
        if (StringUtils.isEmpty(md5) || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException(SeckillStatusEnum.DATA_REWRITE.getStatusInfo());
        }

        Date killTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        //执行存储过程 result被赋值
        try {
            seckillDao.killByProcedure(map);
            //获取result
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatusEnum.SUCCESS, sk);
            } else {
                return new SeckillExecution(seckillId, SeckillStatusEnum.fromCode(result));
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new SeckillExecution(seckillId, SeckillStatusEnum.INNNER_ERROR);
        }

    }

}
