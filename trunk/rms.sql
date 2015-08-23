#用户基本信息表，存储用户基本信息
create table rms_users(
  id bigint not null auto_increment primary key,#ID
  username varchar(25) not null,# 用户姓名
  password varchar(25) not null,#登陆密码
  email varchar(50) not null,#email地址
  cTime varchar(50),#创建时间
  type int(1),#用户类型，主要是超级管理员和一般用户
  isCheck int(1) default 1,#审核通过与否
  userdetailID bigint,#详细用户信息关联ID
  guestTimes bigint,#用户登录次数记录
  lastGuestIP varchar(50),#最后一次登录IP地址记录
  lastGuestTime varchar(50),#最后一次登录时间
  roleID bigint,#所属的角色组
  key(username)
);

#用户日志表
create table rms_logs(
    id bigint not null auto_increment primary key,#ID
    oName varchar(255),#日志名称
    oRemark varchar(255),#日志细节，一般是标题记录
    oTime varchar(50),#发生时间
    userID bigint ,#操作用户
    oIP varchar(50),#操作IP地址
    autoCollection varchar(50)#自动回收时间
);



  #用户角色组表
 create table rms_roles(
     id bigint not null auto_increment primary key,#id
     name varchar(250),#角色名称
     modules varchar(255),#角色可以管理的模块
     cTime varchar(50),
     mTime varchar(50),
     cAdmin varchar(25)#创建者
 );

#用户详细信息表
 create table rms_userdetails(
    id bigint not null auto_increment primary key,
    number varchar(250),#职工号
    trueName varchar(20),#真实姓名
    sex int(1),#性别
    jobName varchar(50),#职称
    birthDate varchar(50),#出生日期
    subject varchar(100),#所学专业
    nSubject varchar(100),#现从事专业
    background varchar(200),#学历
    degree varchar(200),#学位
    workTime varchar(100),#工作时间
    part varchar(200),#所属部门
    remark mediumtext,#备注
    address varchar(250),
    userID#外键
  );

#论文信息表
 create table rms_thesis(
    id bigint not null auto_increment primary key,
    lName varchar(255),#论文名称
    kName varchar(255),#刊物名称
    kSortname varchar(50),#刊物类别
    kGrade varchar(50),#刊物级别
    mPart varchar(255),#主办单位
    author varchar(100),#作者
    pTime varchar(50),#发表时间
    userID bigint,#对应用户
    kNumber varchar(100),#刊号
    remark mediumtext,#备注
    cTime varchar(50),#添加时间
    mTime varchar(50)#修改时间
  );

#论著信息表
 create table rms_books(
    id bigint not null auto_increment primary key,
    bName varchar(255),#论著名称
    bNumber varchar(255),#论著编号
    bSortname varchar(50),#著作类别
    bPress varchar(255),#出版社
    xSortname varchar(255),#学科类别
    pTime varchar(50),#出版日期
    pOrder varchar(50),#版次
    remark mediumtext,#备注
    author varchar(100),#作者
    userID bigint,#对应用户ID
    cTime varchar(50),
    mTime varchar(50)
  );

  #科研项目表
 create table rms_apps(
    id bigint not null auto_increment primary key,
    aNumber varchar(255),#项目编号
    aName varchar(255),#项目名称
    aSource varchar(255),#项目来源
    aSortname varchar(50),#项目类别
    aPart varchar(250),#完成单位
    aPrincipal varchar(50),#负责人
    aMemebr varchar(255),#课题组成员
    bTime varchar(50),#立项时间
    limitTime varchar(200),#拟定期限
    amount decimal(11),#经费金额
    isCheck int(1),#是否鉴定
    checkTime varchar(50),#鉴定时间
    checkPart varchar(200),#鉴定单位
    remark mediumtext,#备注
    cTime varchar(50),
    mTime varchar(50)
  );

  #科研成果信息表
 create table rms_results(
    id bigint not null auto_increment primary key,
    rName varchar(255),#成果名称
    rSortname varchar(50),#成果类别
    rPart varchar(250),#完成单位
    rPrincipal varchar(50),#负责人
    rTime varchar(50),#完成时间
    amount decimal(11),#完成经费金额
    isProduce int(1),#是否投入生产
    benefits varchar(250),#经济效益
    remark mediumtext,#备注
    cTime varchar(50),
    mTime varchar(50)
  );

  #科研奖励信息表
 create table rms_rewards(
    id bigint not null auto_increment primary key,
    rPerson varchar(255),#获奖人员
    appName varchar(250),#项目名称
    rName varchar(250),#奖励名称
    rPart varchar(255),#授予单位
    rTime varchar(50),#授予时间
    remark mediumtext,#备注
    cTime varchar(50),
    mTime varchar(50),
    userID bigint 
  );

  #科研项目申报表
 create table rms_declares(
    id bigint not null auto_increment primary key,
    aNumber varchar(255),#项目编号
    aName varchar(255),#项目名称
    kName varchar(255),#课题名称
    jobName varchar(50),#职称
    sPart varchar(200),#所属部门
    tel varchar(100),#联系电话
    aPrincipal varchar(50),#负责人
    dTable varchar(200),#申报表
    dTime varchar(50),#申报时间
    amount decimal(11),#申报经费
    isAllow int(1),#是否批准
    allowTime varchar(50),#批准时间
    allowAmount decimal(11),#批准经费
    agreementTime varchar(200),#合同时间
    agreementContent mediumtext,#合同内容
    isEnd int(1),#是否结题
    endTime varchar(50),#结题时间
    checkAdmin varchar(50),#鉴定技术负责人
    resultBenefit mediumtext,#成果效益
    remark mediumtext,#备注
    userID bigint,#对应用户ID
    cTime varchar(50),
    mTime varchar(50)
  );



