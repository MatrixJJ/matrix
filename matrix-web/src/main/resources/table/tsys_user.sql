-- auto-generated definition
create table tsys_user
(
    user_id           int auto_increment comment '用户编号'
        primary key,
    user_name         varchar(200)  null,
    login_pwd         varchar(64)   null comment '登陆密码',
    org_id            int           not null comment '组织编号',
    mobile_tel        varchar(64)   not null comment '手机号',
    user_status       varchar(16)   null comment '用户状态',
    login_error_times int default 0 null comment '登录错误次数',
    gmt_create        datetime      null comment '创建时间',
    gmt_modify        datetime      null comment '修改时间',
    remark            varchar(256)  null comment '备注'
)
    comment '用户表';
