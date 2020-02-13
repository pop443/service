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