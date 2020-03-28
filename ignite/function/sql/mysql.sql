drop table userinfo;
create table userinfo
(
   id                 varchar(64) not null comment 'id',
   name                 varchar(200) comment '名称',
   password             varchar(200) comment '密码',
   remark               varchar(200) comment '备注信息',
   primary key (id)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table userinfo comment '用户信息';


drop table course;
create table course
(
   id                 varchar(64) not null comment 'id',
   name                 varchar(200) comment '名称',
   uidd             varchar(64) comment 'userinfoId',
   primary key (id)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table userinfo comment '用户课程';

drop table automation;
create table automation
(
   automation_name                 varchar(4000) not null comment '名称',
   automation_age                 int comment '年龄',
   automation_remark             varchar(4000) comment 'userinfoId',
   primary key (automation_name)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table automation comment '自动化';


DROP TABLE expiry;
CREATE TABLE expiry
(
   id                 VARCHAR(1000) NOT NULL COMMENT 'id',
   NAME               VARCHAR(4000) COMMENT '名称',
   remark             VARCHAR(4000) COMMENT '备注',
   automation_name    VARCHAR(4000) COMMENT 'automation名称',
   automation_age    INT COMMENT 'automation年龄',
   automation_remark    VARCHAR(4000) COMMENT 'automation备注',
   PRIMARY KEY (id)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

ALTER TABLE expiry COMMENT '过期策略';


drop table cachestore1;
create table cachestore1
(
   id                 varchar(64) not null ,
   s01                varchar(100) ,
   s02                varchar(100) ,
   s03                varchar(100) ,
   s04                varchar(100) ,
   s05                varchar(100) ,
   s06                varchar(100) ,
   s07                varchar(100) ,
   s08                varchar(100) ,
   s09                varchar(100) ,
   s10                varchar(100) ,
   s11                varchar(100) ,
   s12                varchar(100) ,
   s13                varchar(100) ,
   s14                varchar(100) ,
   s15                varchar(100) ,
   s16                varchar(100) ,
   s17                varchar(100) ,
   s18                varchar(100) ,
   s19                varchar(100) ,
   s20                varchar(100) ,
   primary key (id)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

drop table cachestore2;
create table cachestore2
(
   id                 varchar(64) not null ,
   s01                varchar(100) ,
   s02                varchar(100) ,
   s03                varchar(100) ,
   s04                varchar(100) ,
   s05                varchar(100) ,
   s06                varchar(100) ,
   s07                varchar(100) ,
   s08                varchar(100) ,
   s09                varchar(100) ,
   s10                varchar(100) ,
   s11                varchar(100) ,
   s12                varchar(100) ,
   s13                varchar(100) ,
   s14                varchar(100) ,
   s15                varchar(100) ,
   s16                varchar(100) ,
   s17                varchar(100) ,
   s18                varchar(100) ,
   s19                varchar(100) ,
   s20                varchar(100) ,
   primary key (id)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;