本项目仅作为学习项目，禁止商用！

### 前言


本项目通过爬去58同城页面解析HTML获取房源信息，通过公众号客服消息通知订阅对应城市的用户有新发布的房源信息，项目功能很简单，大致分为三部分：

1. 58同城二手房源数据爬去解析
2. 公众号页面
3. 后台房源数据获取，用户管理，房源推送，充值


爬虫最早是用python scrapy编写的，但是考虑到推送功能多个异步通知，后面就改成了java使用jsoup进行爬取解析。

项目还有一部分小bug，但作为学习项目还是够够的。

公众号二维码不放了，直接搜索【黑客找房】可以体验一下，目前业务基本停止运行，公众号仅有展示功能。


### 开始旅程

源码传送门： [link](https://note.youdao.com/)

#### 爬取的页面：
![58二手房页面](https://github.com/w77996/BlogsImage/blob/master/house/58.png?raw=true)



#### 公众号推送示例：
![image-20210120103037114](https://github.com/w77996/BlogsImage/blob/master/house/kefu_push.jpg?raw=true)

#### 列表页面

![image-20210120103037114](https://github.com/w77996/BlogsImage/blob/master/house/index.jpg?raw=true)


### 技术

#### 后台
 
##### 相关技术

 - spring boot
 - mybatis
 - mongodb
 - xxl-job
 - redis
 - jsoup
 - swagger
 

##### 项目结构

![后台项目结构](https://github.com/w77996/BlogsImage/blob/master/house/server.png?raw=true)

 

##### sawgger接口文档

![sawgger](https://github.com/w77996/BlogsImage/blob/master/house/swagger.png?raw=true)

##### xxl-job控制台

![xxl](https://github.com/w77996/BlogsImage/blob/master/house/xxl.png?raw=true)


#### 前端


- vue
- vant
- axios
- vue-router

为啥只用到这么些技术，别问，问就是我只会这一些

![绝望](https://github.com/w77996/BlogsImage/blob/master/house/w.png?raw=true)






友情传送，之前写的几个小项目：


HiTop热榜 ：[HiTop热榜](https://blog.csdn.net/w77996/article/details/104680821)  
图床小程序前后端开源：[图床小程序前后端开源](https://blog.csdn.net/w77996/article/details/95332033)  
Python实现云之家自动签到：[Python实现云之家自动签到](https://blog.csdn.net/w77996/article/details/102967185)  
java实现算法推荐：Mahout实践：[java实现算法推荐：Mahout实践](https://blog.csdn.net/w77996/article/details/102832481)  