package org.seckill.enums;

/**
 * Created by liuchou on 16/5/24.
 */
public enum SeckillStatusEnum {
    SUCCESS(1,"秒杀成功"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改"),
    END(0,"秒杀结束");

    private  int status;

    private String statusInfo;

    SeckillStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public static SeckillStatusEnum fromCode(int index){
        for (SeckillStatusEnum status: values()){
            if (status.getStatus() == index){
                return status;
            }
        }
        return null;
    }
}
