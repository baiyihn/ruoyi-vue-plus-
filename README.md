## 博客简介


> 使用RuoYi-Vue-Plus 作为博客的后端框架

> 项目代码、文档 均开源免费遵循开源协议在项目中保留开源协议文件即可<br>


> 系统演示: 前台http://8.130.45.202/   后台：http://8.130.45.202:81
> 
> ## 部署博客的操作步骤文章与问题询问请进群：551275273
 
## 业务功能

| 功能 | 介绍 |
|---|---|
| 用户管理 | 用户是系统操作者，该功能主要完成系统用户配置。 |
| 部门管理 | 配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。 |
| 岗位管理 | 配置系统用户所属担任职务。 |
| 菜单管理 | 配置系统菜单，操作权限，按钮权限标识等。 |
| 角色管理 | 角色菜单权限分配、设置角色按机构进行数据范围权限划分。 |
| 字典管理 | 对系统中经常使用的一些较为固定的数据进行维护。 |
| 参数管理 | 对系统动态配置常用参数。 |
| 通知公告 | 系统通知公告信息发布维护。 |
| 操作日志 | 系统正常操作日志记录和查询；系统异常信息日志记录和查询。 |
| 登录日志 | 系统登录日志记录查询包含登录异常。 |
| 文件管理 | 系统文件上传、下载等管理。 |
| 定时任务 | 在线（添加、修改、删除)任务调度包含执行结果日志。 |
| 代码生成 | 前后端代码的生成（java、html、xml、sql）支持CRUD下载 。 |
| 系统接口 | 根据业务代码自动生成相关的api接口文档。 |
| 服务监控 | 监视集群系统CPU、内存、磁盘、堆栈、在线日志、Spring相关配置等。 |
| 缓存监控 | 对系统的缓存信息查询，命令统计等。 |
| 在线构建器 | 拖动表单元素生成相应的HTML代码。 |
| 连接池监视 | 监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。 |
| 使用案例 | 系统的一些功能案例 |

## 博客模块介绍

| 包 | 介绍 |
|--|---|
| blogutils | 博客后台中通用的方法。 |
| domain | 实体层 |
| domain-bo | 前端后台管理系统给后端返回的数据（查询条件）。 |
| domain-vo | 后端给前端后台管理系统返回的数据层。 |
| dto | 后端给前端前台界面返回的数据  |
| dto-vo | 前端的前台界面给后端返回的数据 |
| domain-es | es索引对应查询的实体类 |

## 博客特点：
- 项目规范，属于企业级博客
- 鉴权使用了sa-token极大的降低了与项目的耦合度，代替了spring-security
- 标签与分类动态添加
- 弹幕强留言
- 文章推荐等功能
- 多搜索模式
- 使用just-auth集成三方登录，便于拓展
- 前台参考"Hexo"的"Butterfly"设计
- 后台根据ruoyi-vue-plus开发十分贴合企业级开发规范
- 整合了easy_es mail等流行框架

## 使用技术：
### 前端：前端： vue + vuex + vue-router + axios + vuetify + element + echarts
### 后端： SpringBoot + easy_es + nginx + docker + sa-token + Swagger2 + MyBatisPlus + Mysql + Redis + elasticsearch + RabbitMQ + MaxWell + Websocket


## 存储：使用当前主流的三方云端，cos/oss/七牛等动态切换

## 运行环境：服务器：至少1核2G（关闭es）


## 加群与捐献 🐧群：551275273
<img src="https://gitee.com/kalashok-pan/Images/raw/master/blog-image/ED626A4C123843EAC82303337086A5BE.jpg"  width = "200" height = "350">

## 捐献作者
开源博主创作不易！
<tr>
<td><img src="https://gitee.com/kalashok-pan/Images/raw/master/blog-image/wei.jpg"  width = "250" height = "300"></td>
<td><img src="https://gitee.com/kalashok-pan/Images/raw/master/blog-image/zhi.jpg"  width = "300" height = "320"></td>
        </tr>

## 演示图例

<table border="1" cellpadding="1" cellspacing="1" style="width:500px">
    <tbody>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/50ebedf1b436434b92a6c77b142c758d.jpeg" width="1920" /></td>
            <td><img src="https://img-blog.csdnimg.cn/b80d5455ec054708a19495dae1043e22.jpeg" width="1920" /></td>
        </tr>
        <tr>
            <td>
            <p><img src="https://img-blog.csdnimg.cn/cc8e9143373e48c8a194d2212a9ab074.jpeg" width="1920" /></p>
            </td>
            <td><img src="https://img-blog.csdnimg.cn/ab6c4ee828ce41b8bce1da4cb27967d1.jpeg" width="1920" /></td>
        </tr>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/fa5c4f57ccfa41cea8f5ecbe904a0794.jpeg" width="1920" /></td>
            <td><img src="https://img-blog.csdnimg.cn/7d20cfbaf9a44cef8af698fcd1bbe87c.jpeg" width="1920" /></td>
        </tr>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/42e59e2afc154f3791020244ec063228.jpeg" width="1920" /></td>
            <td><img src="https://img-blog.csdnimg.cn/b3965e9072944422a1a0613aa6102ac6.png" width="1920" /></td>
        </tr>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/6ae67995ba81407e9c8062f0b8457b2d.png" width="1920" /></td>
            <td><img src="https://img-blog.csdnimg.cn/b4cc03e1c76749cfaff51543a57641b4.png" width="1920" /></td>
        </tr>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/b4cc03e1c76749cfaff51543a57641b4.png" width="1920" /></td>
            <td><img src="https://oscimg.oschina.net/oscnet/up-8ea177cdacaea20995daf2f596b15232561.png" width="1920" /></td>
        </tr>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/b4cc03e1c76749cfaff51543a57641b4.png" width="1920" /></td>
            <td><img src="https://img-blog.csdnimg.cn/0c16fd70c8794ec680c01cdd1649dcd8.jpeg" width="1920" /></td>
        </tr>
        <tr>
            <td><img src="https://img-blog.csdnimg.cn/b9aa2b270a88450db454dcf53983f746.png" width="1920" /></td>
            <td><img src="https://oscimg.oschina.net/oscnet/up-eff2b02a54f8188022d8498cfe6af6fcc06.png" width="1920" /></td>
        </tr>
    </tbody>
</table>
