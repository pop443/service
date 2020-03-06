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


create table automation
(
   automation_name                 varchar(64) not null comment '名称',
   automation_age                 int comment '年龄',
   automation_remark             varchar(64) comment 'userinfoId',
   primary key (automation_name)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table automation comment '自动化';



create table expiry
(
   id                 varchar(64) not null comment 'id',
   name               varchar(64) comment '名称',
   remark             varchar(64) comment '备注',
   automation_name    varchar(64) comment 'automation名称',
   automation_age    varchar(64) comment 'automation年龄',
   automation_remark    varchar(64) comment 'automation备注',
   primary key (id)
)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

alter table expiry comment '过期策略';