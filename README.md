# hrmanagement

## 🎄 简介

<p>HRMANAGEMENT是一个人事管理系统，包括人员信息录入、修改、删除。另外有消息通知、考勤、薪资模块。</p>

## 🚀 技术栈

`前端：vue2 + vuex + vue-router + elementui + axios`

`后端：springboot + mysql`

`工具：VSCode、IDEA`

## ✨ 功能

```
管理员
- 添加、删除员工，查看、修改员工信息
- 查看、修改、增加出勤信息
- 查看、修改、增加薪资信息
- 查看人事变动
- 查看、创作文章
- 将员工信息、考勤信息、薪资信息导出为excel表格

普通员工
- 查看自己的考勤
- 查看公司发的通知
- 查看自己的工资情况
- 查看个人信息
- 修改头像
- 修改密码
- 接收实时通知
```

## 🕕 一些说明

```
测试管理员：账号—1150202 密码：000000
测试普通员工： 账号—1170502 密码：000000
可以试一下实时通信这个功能（一个浏览器进管理员账号发文章，另一个浏览器进普通员工账号可以看到实时消息通知）

* 后端运行在8083端口
* 员工工号是用部门号、职位号、序号拼接
* department、edu_level、job三个表为代码表，表中的数据都是之前测试的时候乱写的，没有特意生成数据，可能比较乱
* 有一些数据没有从后端获取了（和其他的逻辑一样的，就没有写了），比如登录用户考勤和薪资等
```

## 🚗 运行

```
main分支为前端代码，master分支为后端代码

git clone -b master https://github.com/SEEMORELEARNMORE/hrmanagement.git

git clone -b main https://github.com/SEEMORELEARNMORE/hrmanagement.git

cd hrmanagement

npm install

npm run serve
```
