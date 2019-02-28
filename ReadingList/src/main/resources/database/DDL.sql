drop table if exists readinglist_role;
create table readinglist_role
(
  role_id   int(2)       not null
  comment '角色相应ID'
    primary key,
  role_name varchar(255) not null
  comment '角色名称',
  auth      varchar(255) null
  comment '角色所拥有的权限'
);

drop table if exists readinglist_user;
create table readinglist_user
(
  username             varchar(255) not null
  comment '用户名'
    primary key,
  password             varchar(255) not null
  comment '密码',
  role_id              int(2)       not null
  comment '用户所属角色id',
  last_password_change datetime     not null
  comment '最后一次密码修改时间',
  enable               tinyint(1)   not null
  comment '是否启用该账号，可以用来做黑名单'
);