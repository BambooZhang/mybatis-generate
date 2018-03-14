
#项目说明

根据数据库中的表生成，mapper.xml文件和serviceimpl


```
#生成后的文件保存的目录
freemark.project_absolutePath =D:/work-space/java-newspace/xm-service/src/main/java
freemark.base_package=com.xialeme.modulesapi.wxact
#freemark.entity_package=com.xialeme.project.sysconfig.entity
#freemark.dao_package=com.xialeme.xmwebapi.dao
#pojo的package
freemark.entity_package=com.xialeme.modulesapi.wxact.entity
#mapper.xmx中对应的daopackage
freemark.dao_package=com.xialeme.modulesapi.wxact.dao
#pojo对应的表名
freemark.tablename=xm_wxact
#需要去掉的前缀
freemark.tablename_prefix=xm_

```


#生成之后文件说明

└─entity
        user.html
        User.java
        user.js
        UserController.java
        UserMapper.xml
        UserServiceImpl.java

user.html:html后台管理页面，可能不符合你自己的项目前端
User.java：用户类
UserController.java:用户REST接口类
UserMapper.xml：xml对应的sql
UserServiceImpl.java:Service实现类，

上面没有单独生成dao和service文件是因为这两个文件在我的项目中是泛型基础类并且已经包含了查询对应的所有基础方法，可以共用，所以不用单独生成













