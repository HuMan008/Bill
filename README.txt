#2.1.0_201911141753-SNAPSHOT
    修改token校验错误的问题

#2.1.0_20191023-SNAPSHOT
    修改属性keyOfHashCompareAuthenticationPathPrefix不能匹配多层路径的问题
    修改HttpBodyStreamWrapperFilter因为keyOfHashCompareAuthenticationPathPrefix导致了误认为是跳过的问题

### 1.0.1
    重置版本号（之前的作废）
    换了新的仓库地址
    前后端分离，去掉spring-session-redis
    增加了JWT


### 1.0.3
    增加响应验签功能;
    日期工具类中增加yyyyMMddHHmmss


### 2.0.17
    springboot升级到2；
    增加spring-session-redis
    添加thyeleaf3；
    增加了页面、后台权限控制；
    修改异常处理机制，支持返回页面；

### 2.1.0
    按照前后端分离的思路





gradle uploadArchives -Dorg.gradle.warning.mode=all -x test





