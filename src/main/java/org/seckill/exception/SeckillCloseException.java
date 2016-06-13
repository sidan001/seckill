package org.seckill.exception;

/**
 * Created by liuchou on 16/5/24.
 */
public class SeckillCloseException extends  SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
