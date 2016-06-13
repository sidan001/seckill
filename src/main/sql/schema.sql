--创建数据库
CREATE DATABASE `seckill` CHARACTER SET utf8 COLLATE utf8_general_ci;

USE seckill;

--创建秒杀库存表
CREATE TABLE `seckill`(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` int NOT NULL  COMMENT '商品库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_ent_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀表';

--初始化seckill表
insert into seckill (name,number,start_time,end_time)
values
  ( '1元秒杀iWatch',1,'2016-05-09 00:00:00','2016-05-10 00:00:00'),
  ( '100元秒杀iPhone6s',1,'2016-05-10 09:00:00','2016-05-10 12:00:00'),
  ( '10元秒杀iPhone5c',1,'2016-05-12 10:00:00','2016-05-12 10:01:00'),
  ( '88元秒杀iPad Pro',1,'2016-05-19 00:00:00','2016-05-20 00:00:00'),
  ( '888元秒杀Mac Pro',1,'2016-05-29 00:00:00','2016-05-30 00:00:00');

--创建秒杀成功明细表
--todo 用户认证相关表
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀的商品ID',
  `user_phone` BIGINT NOT NULL COMMENT '手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标示:-1:无效,0:成功,1:已付款',
  `create_time` TIMESTAMP NOT NULL  COMMENT  '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';