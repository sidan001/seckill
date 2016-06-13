package org.seckill.dto;

/**
 * Created by liuchou on 16/5/29.
 */
public class SeckillResult<T> {
    private boolean success;
    private T data;
    private  String error;

    public SeckillResult(boolean b, T data) {
        this.success = true;
        this.data = data;
    }

    public SeckillResult(String error) {
        this.success = false;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
