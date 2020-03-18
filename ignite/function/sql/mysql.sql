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
   uid             varchar(64) comment 'userinfoId',
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