package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatusEnum;

/**
 * 封装秒杀执行后结果
 * Created by liuchou on 16/5/24.
 */
public class SeckillExecution {

    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //秒杀状态描述
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStatusEnum statusEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = statusEnum.getStatus();
        this.stateInfo = statusEnum.getStatusInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatusEnum statusEnum) {
        this.seckillId = seckillId;
        this.state = statusEnum.getStatus();
        this.stateInfo = statusEnum.getStatusInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
