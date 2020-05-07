# leyou
one E-commerce website project to practise, mainly be built by java language and spring framwork

## 乐优商城项目
```
总结说明：
  第一部分： 后台管理系统（前后端分离）
   1.1 前端(leyou-manage-web文件): 主要涉及es6,vuejs vuetify webpack 实现热部署；主要功能实现分类管理、品牌管理、商品列表、规格参数；
   1.2 后端：spring cloud 微服务架构（基础微服务搭建： 注册中心eureka,网关zuul,商品服务item,图片上传微服务，通用工具类微服务);
   1.3 nginx 实现反向代理 + FastDFS（分布式图片存储),mysql数据库（以上都有在电脑上实现过）；

 第二部分：门户系统（搜索微服务 elasticsearch)
  1.1 前端（leyou-portal）: html live-server 实现热部署
  1.2 后端： 搜索微服务search; 创建索引->从数据库中导入数据->查询->前端页面渲染

注意：以下步骤都是学习中记录，会有点冗余；
```


#### 1. 项目介绍
- 全品类电商购物网站（B2C)
- 用户： 在线购买商品、加入购物车、下单、秒杀商品
- 评论已购买商品
- 管理员后台管理商品的上下架、促销活动
- 管理员监控商品的销售状况
- 客户后台处理退款操作
- 未来3~5年支持千万用户使用
#### 2. 系统架构（技术架构）
![框架整体图]()
- 前端页面：
- 后端页面：

#### 3. 前端项目搭建
- 前端页面准备、后台管理
- 技术选型：
  - 基础的HTML、CSS、javascript、（基于es6标准）
  - JQuery
  - Vue.js 2.0 以及Vue的UI框架： vuetify
  - 前端构建工具： wepack
  - 前端安装包工具： npm
  - Vue脚手架：Vue-cli
  - Vue路由：vue-route
  - ajax框架：axios
  - 基于Vue的富文本框架：quill-editor
### 4. 后端环境搭建
- 技术选型：
  - 基础的SpringMVC、Spring5.0和Mybatis3
  - Spring Boot 2.0.4版本
  - Spring Cloud： Finchley SR1
  - Redis-4.0
  - RabbitMQ-3.4
  - Elasticsearcg-5.6.8
  - nginx - 1.10.2
  - FastDFS -5.0.8
  - MyCat
  - Thymelear
  - jwt
- 域名说明
  - 一级域名： www.leyou.com
  - 二级域名： manage.leyou.com , api.leyou.com
- 搭建父工程，导入依赖、管理版本、锁定版本
- 搭建注册中心子模块：
  - 导入eureka, 添加启动类：@SpringBootApplication @EnableEurekaServer
  - 编写配置（application.yml): 修改端口,应用重名(port, name) ,地址（service-url）
- 搭建网关模块：
  - 导入zuul 、eureka,添加启动类: @SpringCloudApplication @EnableZuulProxy
  - 编写配置(application.yml):端口、名字、注册地址、路由前缀、熔断超时时长、ribbon链接超时时长、ribbon读取超时时长、服务重试次数、切换服务重试次数
### 5. 商品微服务简单搭建
- 创建聚合工程: 包含两个子模块：实体类接口，服务类
- 导入依赖： 服务类(web、eureka、mapper、pagehelper、mysql、实体类接口坐标)
- 服务类： 启动类（eureka,注册服务 @SpringBootApplication @EnableDiscoveryClient），配置文件
- 配置路由：

### 6. 通用工程（工具)模块
- 创建通用工具包: Json序列化和反序列工具类
- 通用异常处理: 自定义异常
- REST风格简单说明： 只有名词，不含动词，通过请求的方式分别不同功能（get 获取资源，post 创建一个资源，put 修改一个资源状态， delete 删除一个资源）； 响应的规范，严格按照状态码
- insomnia 发起post请求测试
### 7. nginx与品牌管理
- 域名解析配置(ip 域名映射): 127.0.0.1 变更别名： www.leyou.com; switchHosts工具
- 端口问题：nginx
- nginx
  - 反向代理
  - 负载均衡
  - 动态路由
  - 请求过滤
- nginx(静态资源) + tomcat（动态资源）
### 8. 商品分类页面(商品微服务1：后台管理)
- 请求分析：
  - 请求的方式： GET
  - 请求的路径： /item/category/list
  - 请求的参数： pid(父分类id)
  - 返回的结果： 商品分类的集合
- 后端代码实现： 实体类（ly-item-interface) -> mapper -> service -> controller
- 解决跨域（jsonp,nginx,cors)
### 9. 品牌页面(商品微服务2： 后台管理)
- 品牌展示页面
  - 重新设计品牌页面： vuetify axios发起异步请求(前端 vuejs vuetifyjs)
  - 后端：
    - 请求的方式： GET
    - 请求的路径： /item/brand/page
    - 请求的参数： ke（搜索条件）page（当前页码）rows（每页行数） sortBy（排序字段）desc（排序方式）
    - 返回结果： 自定义通用分页结果类 -> 通用mapper实现分页过滤查询
- 品牌新增页面
  - 品牌分类展示： 三级联动
  - 后端：
    - 请求方式： POST
    - 请求路径：/item/brand
    - 请求的参数：Brand{name imag letter categories[]}
    - 返回值：无

### 10. 图片上传（图片微服务）
- 微服务搭建（eureka web)
- 后端:
  - 请求方式： POST
  - 请求路径： /upload/image
  - 请求的参数： image
  - 返回值: 文件保存的路径
- 文件上传优化1：
  - 绕过网关缓存，减轻zuul压力（/zuul 开头）: nginx 重写请求路径 rewrite
- 文件上传优化2:
  - FastDFS 分布式文件系统：nginx继承fdfs
  - FastDFS java 交互： fastdfs-client

### 11. 规格参数页面（后台管理）
- 大致类型分析：{分类, 详情[组, 规格键值[{规格键：规格参数值}]]}
  - 总体： {category: xxx, spec: xxx};
  - 细分： {category: xxx, spec:[{group:xxx, param:[xxxxx]}]}
  - 细分： {category: xxx, spec:[{group:xxx, param:[{k:v, k1:v1},.......]}]}
- 后端：
  - 请求方式： get
  - 请求路径： /spec/{cid}
  - 请求参数： cid(分类id)
  - 返回值：规格参数Json对象

  - 更新操作

### 12. 商品列表页（后台管理）
- 数据表分析：
  - spu: 商品标准单位（抽象的某个商品： iphone8）
  - sku:商品库存单位（具体的某个商品：黑色 64gb iphone 8)
- 后端：
  - 分页查询页面
    - 请求方式: GET
    - 请求路径：/item/spu/page
    - 请求参数：page rows search(过滤条件：key saleable) sortBy（排序字段) desc(排序方式)
    - 返回结果： PageResult<spu>
  - 新增页面（前端页面传递不了specification,后端接口已实现） todo： 以后改进
  - 更细页面（前端页面传递不了specification,后端接口已实现） todo： 以后改进

### 13, Elasticsearch 全文搜索技术
- 使用，端口导入数据到索引中（mysql 相关数据 ——> elasticsearch index）
- 后端搜索功能的实现
- 前端页面渲染
