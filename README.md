# vueblog
> 使用SSM+vue实现前后端分离的博客项目

#### 技术栈：

- 前端: vue3、element plus、axios
- 后端：Spring、Spring MVC、MybatisPlus、redis

#### 效果：

![](https://i.niupic.com/images/2021/09/26/9CX0.png)

![](https://i.niupic.com/images/2021/09/20/9CcY.png)

#### 总结简介：

​		该项目还有很多缺点和不足，可以在本地运行学习，但不适合部署在服务器上实际使用。（<font color='Brown'>博客项目将会不断更新，缺点和不足将会在之后的更新中修正和完善</font>）

#### 优点：

 - 使用redis存储session
 - 注册、重置密码使用邮箱进行验证

#### 缺点:

- 没有做权限控制
- 对于图片没有有效的管理

#### 使用说明：

- 前端服务启动前需要下载node.js, 启动命令： npm run start
- 后端服务在使用前需要修改数据库配置和邮箱配置
