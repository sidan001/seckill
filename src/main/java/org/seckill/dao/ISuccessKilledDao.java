package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * Created by liuchou on 16/5/10.
 */
public interface ISuccessKilledDao {


    /**
     * 插入购买明细,数据库可过滤重复插入
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,
                            @Param("userPhone") long userPhone);

    /**
     * 根据秒杀商品Id,和用户手机号查询秒杀详情,并包含秒杀商品信息
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,
                                       @Param("userPhone") long userPhone);


}
