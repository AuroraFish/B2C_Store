# 基于B2C的电子商务平台设计与实现

## 项目简介

大三下学期课设, B2C模式电商项目. 前后端分离, 此项目重点关注后端服务的开发, 包括前端商城的开发, 以及后台管理部分开发, 前端页面采用github上现成的开源项目(https://github.com/hai-27/store-server).
前台部分通过浏览器访问, 用户可以登录, 浏览商品, 添加购物车, 生成订单, 后台部分管理员登录, 对前台用户, 类别, 商品, 以及订单数据维护.



### 项目技术栈

**后端**
springboot, springcloud, springcloud alibaba, spring cache, rabbitmq, elasticsearch, druid, mybatis-plus, lombok.

**前端**
vue, node, element-ui, layui, axios, vuex. 使用github上现成的开源项目(https://github.com/hai-27/store-server).



### 功能模块

**商城前端服务**

| 模块     | 功能                                                         |
| -------- | ------------------------------------------------------------ |
| 用户模块 | 登录，注册，账号检查                                         |
| 轮播模块 | 轮播图展示                                                   |
| 商品模块 | 热门商品，类别商品，全部商品，单类别商品，商品详细，商品图片，商品搜索 |
| 类别模块 | 类别展示                                                     |
| 地址模块 | 地址添加，地址删除，地址展示                                 |
| 收藏模块 | 收藏添加，收藏展示，收藏删除                                 |
| 购物模块 | 购物车添加，购物车修改，购物车展示，购物车删除               |
| 订单模块 | 订单生成，订单展示                                           |
| 性能模块 | 缓存实现，搜索实现，搜索同步，搜索添加，搜索删除             |

**商城后台管理服务**

| 模块     | 功能                                             |
| -------- | ------------------------------------------------ |
| 用户模块 | 用户展示，用户添加，用户修改，用户删除           |
| 类别模块 | 类别展示，类别添加，类别修改，类别删除           |
| 商品模块 | 商品添加，商品展示，商品搜索，商品修改，商品删除 |
| 订单模块 | 订单展示                                         |



<details>
  <summary>点击展开</summary>
<!-- 在这里添加需要折叠的内容 -->



## 微服务环境搭建

系统: centos 7+ 版本.

### Docker安装

**Docker介绍**
Docker 是一个开源的应用容器引擎，基于 [Go 语言](https://www.runoob.com/go/go-tutorial.html) 并遵从 Apache2.0 协议开源. 简化环境配置过程.

**Docker安装**

1. 清空原有组件残留

   ```bash
   yum remove docker \
                     docker-client \
                     docker-client-latest \
                     docker-common \
                     docker-latest \
                     docker-latest-logrotate \
                     docker-logrotate \
                     docker-selinux \
                     docker-engine-selinux \
                     docker-engine \
                     docker-ce
   ```

2. 设置Docker仓库

   ```bash
   yum install -y yum-utils \
              device-mapper-persistent-data \
              lvm2 --skip-broken
   ```

3. 配置yum阿里镜像

   ```bash
   # 设置docker镜像源
   yum-config-manager \
       --add-repo \
       https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
       
   sed -i 's/download.docker.com/mirrors.aliyun.com\/docker-ce/g' /etc/yum.repos.d/docker-ce.repo
   
   yum makecache fast
   ```

4. 安装Docker
   docker-ce为社区免费版本.

   ```bash
   yum install -y docker-ce
   ```

5. 启动docker

   ```bash
   #* 关闭防火墙, docker涉及端口映射, 建议先关闭防火墙, 避免端口屏蔽
   # 关闭
   systemctl stop firewalld
   # 禁止开机启动防火墙
   systemctl disable firewalld
   
   #* 启动和停止docker
   systemctl start docker  # 启动docker服务
   
   systemctl stop docker  # 停止docker服务
   
   systemctl restart docker  # 重启docker服务
   
   docker -v
   ```

6. 配置docker阿里镜像, 提高下载速度

   ```bash
   sudo mkdir -p /etc/docker
   sudo tee /etc/docker/daemon.json <<-'EOF'
   {
     "registry-mirrors": ["https://as08lme3.mirror.aliyuncs.com"]
   }
   EOF
   sudo systemctl daemon-reload
   sudo systemctl restart docker
   ```

**Docker基本概念**

1. <u>*镜像(Image):*</u> 镜像(Image), 就相当于是一个root文件系统. 相当于软件的安装包.
2. <u>*容器(Container):*</u> 镜像(Image)和容器(Container)的关系, 相当于类和实例, 镜像是静态的定义, 容器时镜像运行的实体. 相当于安装包安装以后的运行程序.
3. *<u>仓库(Repository):</u>* 用来保存镜像. 存放安装包的仓库.

Docker镜像网站: http://hub.docker.com

**Docker基本命令**

1. docker push : 推送镜像到服务器.
2. docker pull : 从服务器拉取镜像.
3. docker build : 构建镜像.
4. dockr images : 查看镜像.
5. docker rmi : 删除镜像.
6. docker save : 保存镜像为一个压缩包.
7. docker load : 加载压缩包为镜像.

```bash
#* 拉取nginx镜像
# 默认 最新 latest
docker pull nginx 
docker pull nginx:版本号
    
docker images 查看版本
    
#* 镜像备份和加载
docker save 镜像名 -o /输出的位置
docker load -i /输入的镜像文件
```

**Docker容器相关命令**

1. docker run :  运行容器.
2. docker exec : 进入容器执行命令.
3. docker logs : 查看容器运行日志.
4. docker ps : 查看所有运行的容器及状态.
5. docker pause : 运行->暂停.
6. docker start : 停止->运行.
7. docker stop : 运行->停止.
8. docker unpause : 暂停->运行.

```bash
#* 运行nginx容器, 并访问
docker run --name containerName -p 80:80 -d nginx
    
docker run ：创建并运行一个容器
--name : 给容器起一个名字，比如叫做mn
-p ：将宿主机端口与容器端口映射，冒号左侧是宿主机端口，右侧是容器端口
-d：后台运行容器
nginx：镜像名称，例如nginx
```



### MYSQL安装

**镜像拉取**

```bash
docker pull mysql
```

**容器运行**

```bash
docker run --name msql -v /msql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=root -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

# -v /msql/conf:/etc/mysql/conf.d 数据卷
# MYSQL_ROOT_PASSWORD=root 密码:root
# --character-set-server=utf8mb4 编码格式
```

**连接测试**

```bash
docker exec -it 容器名 bash
mysql -uroot -p密码
```



### RABBITMQ安装

**Rabbitmq介绍**
RabbitMQ是实现了高级消息队列协议(AMQP)的开源消息代理软件(亦称面向消息的中间件). RabbitMQ服务器是用[Erlang](https://baike.baidu.com/item/Erlang?fromModule=lemma_inlink)语言编写的，而集群和故障转移是构建在[开放电信平台](https://baike.baidu.com/item/开放电信平台?fromModule=lemma_inlink)框架上的. 所有主要的[编程语言](https://baike.baidu.com/item/编程语言/9845131?fromModule=lemma_inlink)均有与代理接口通讯的[客户端](https://baike.baidu.com/item/客户端/101081?fromModule=lemma_inlink)库. 

**导入镜像压缩文件**
mq.tar

```bash
# 加载镜像
docker load -i mq.tar

#启动容器
docker run \
 -e RABBITMQ_DEFAULT_USER=root \
 -e RABBITMQ_DEFAULT_PASS=123456 \
 --name mq \
 --hostname mq1 \
 -p 15672:15672 \
 -p 5672:5672 \
 -d \
 rabbitmq:3-management
```

**基本使用**
外部访问: http://公网IP:15672 . 输入账号和密码即可.



### REDIS安装

**Redis介绍**
Redis是一个高性能的key-value数据库. Redis的出现, 很大程度弥补了Mysql这类key/value存储的不足, 在部分场合可以对关系数据库起到很好的补充作用.

**Redis安装**

```bash
# 下载镜像
docker pull redis

# 启动容器
docker run --name myredis -p 6379:6379 -d redis redis-server --appendonly yes
# 创建和启动容器
# redis-server --appendonly yes 设置持久化手段

```

**Redis测试**

```bash
docker exec -it myredis redis-cli

set name Mike
get name
```

**安装Redis可视化客户端**
redis-desktop-manager-0.9.3.817.exe



### Elasticsearch安装

**所需文件**
kibana.tar , es.tar , ik.zip(解压)

**配置网络**

```bash
# es搜索数据库和kibana可视化工具 容器互联
docker network create es-net
```

**加载镜像**

```bash
# 从文件存放位置加载
docker load -i /tmp/es.tar
docker load -i /tmp/kibana.tar
```

**启动容器**

```bash
# 单点es容器运行
docker run -d \
  --name es \
    -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
    -e "discovery.type=single-node" \
    -v es-data:/usr/share/elasticsearch/data \
    -v es-plugins:/usr/share/elasticsearch/plugins \
    --privileged \
    --network es-net \
    -p 9200:9200 \
    -p 9300:9300 \
elasticsearch:7.12.1

# 单点运行kibana. 提供数据可视化.
docker run -d \
--name kibana \
-e ELASTICSEARCH_HOSTS=http://es:9200 \
--network=es-net \
-p 5601:5601  \
kibana:7.12.1
```

浏览器输入: 公网ip:端口号, 测试访问.

**配置IK中文分词器**

```bash
# 查看IK需解压位置
docker volume inspect es-plugins

# 运行结果
[
    {
        "CreatedAt": "2024-03-19T16:39:48+08:00",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/es-plugins/_data",
        "Name": "es-plugins",
        "Options": null,
        "Scope": "local"
    }
]
# 将IK解压, 存放至_data下

# 重启es容器
docker restart es
```



## 开发环境搭建

### JDK环境安装

**所需文件**
jdk-8u151-windows-x64.exe.

**软件安装**
下一步即可, 安装位置可以自定义.

**环境配置**
配置环境变量,  JAVA_HOME值为JDK安装根目录, PAHT添加变量%JAVA_HOME%/bin .



### Idea环境安装



### Maven环境安装

**Maven介绍**
项目构建和依赖管理的工具, 简化导入jar包的过程, 可以用命令简化对项目的构建.

**所需文件**
apache-maven-3.6.3-bin.zip. 解压即可. 要求本地配置JAVA_HOME.

**环境配置**
配置环境变量, MAVEN_HOME值为Maven安装根目录, PATH添加变量%MAVEN_HOME%/bin .

**配置修改**
配置文件位置: maven/conf/setting.xml

修改maven本地仓库.

```xml
<!-- 大概55行左右,改为本地其他文件夹即可,如果没有,会自动创建! -->
<localRepository>D:\repository</localRepository>
```

修改maven镜像地址.

```xml
  <mirrors>
   <!-- 大概在148行中,在mirrors标签中添加以下参数的mirror标签即可! -->
     <mirror>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
```

修改maven jdk编译版本.

```xml
<!-- 注意: 是在profiles标签中,添加此标签内容! -->
<profile>
   <id>jdk8</id>
   <activation>  
     <activeByDefault>true</activeByDefault>  
     <jdk>1.8</jdk>  
   </activation>  
   <properties>  
     <maven.compiler.source>1.8</maven.compiler.source>  
     <maven.compiler.target>1.8</maven.compiler.target>  
     <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>  
   </properties>
</profile>
```



### MYSQL环境安装

**测试**

```cmd
mysql -uroot -p密码
```



## 前端服务开发

### 父工程搭建

**导入依赖配置**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.atguigu</groupId>
    <artifactId>b2c_cloud_store</artifactId>
    <version>1.0.0</version>
    <packaging>pom</packaging>


    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.9.RELEASE</version>
        <relativePath/>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring-cloud.version>Hoxton.SR10</spring-cloud.version>
        <mysql.version>5.1.47</mysql.version>
        <mybatis.version>2.1.1</mybatis.version>
        <druid.version>1.2.5</druid.version>
        <mybatis-plus.version>3.5.2</mybatis-plus.version>

    </properties>


    <!-- spring cloud 和 spring cloud alibaba 和 mybatis 相关依赖管理-->
    <dependencyManagement>

        <dependencies>
            <!-- springCloud -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--nacos的管理依赖-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.5.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- mysql驱动 -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <!--mybatis-->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.version}</version>
            </dependency>

            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.version}</version>
            </dependency>

        </dependencies>

    </dependencyManagement>


    <!-- 后续子莫夸可能需要的依赖 -->
<!--    <dependency>-->
<!--        <groupId>org.springframework.boot</groupId>-->
<!--        <artifactId>spring-boot-starter-web</artifactId>-->
<!--    </dependency>-->

    <!-- nacos 注册中心客户端依赖包 -->
<!--    <dependency>-->
<!--        <groupId>com.alibaba.cloud</groupId>-->
<!--        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>-->
<!--    </dependency>-->
<!--    &lt;!&ndash;nacos 配置中心配置管理依赖&ndash;&gt;-->
<!--    <dependency>-->
<!--        <groupId>com.alibaba.cloud</groupId>-->
<!--        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>-->
<!--    </dependency>-->
     <!-- feign依赖 -->
<!--    <dependency>-->
<!--        <groupId>org.springframework.cloud</groupId>-->
<!--        <artifactId>spring-cloud-starter-openfeign</artifactId>-->
<!--    </dependency>-->

      <!-- feign相关依赖 -->
<!--    <dependency>-->
<!--        <groupId>io.github.openfeign</groupId>-->
<!--        <artifactId>feign-httpclient</artifactId>-->
<!--    </dependency>-->

    <!--网关gateway依赖-->
<!--    <dependency>-->
<!--        <groupId>org.springframework.cloud</groupId>-->
<!--        <artifactId>spring-cloud-starter-gateway</artifactId>-->
<!--    </dependency>-->


    <!-- lombok通用依赖引入 -->
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>

</project>

```



### 网关服务搭建

**导入依赖**

```xml

<dependencies>
<!--        网关gateway依赖-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
<!--         nacos 注册中心客户端依赖包-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!--nacos 配置中心配置管理依赖-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    </dependency>
</dependencies>
```



**添加配置**
bootstrap.yml

```bash
server:
  port: 3000 # 前端默认访问端口号为3000
  servlet:
    context-path: / # 前端默认访问的根路径
spring:
  application:
    name: gateway-service  # 程序名就是服务名
  cloud:
    nacos:
      # 如果注册中心不在本机,需要移到本位置,否则一致查找本地:8848端口!
      server-addr: 124.221.70.206:8848 #注册中心
```

application.yml

```bash
# 配置网关
spring:
  cloud:
    gateway:
      routes:
        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/user/**  # 访问user路径转发用户服务
        - id: product-service # 此名称随意定义
          uri: lb://product-service #使用负载均衡,调用服务名,这是服务名
          predicates:
            - Path=/product/** # 访问product相关,转发到product服务
```



### 通用服务搭建



### 搜索服务搭建

#### 同步数据

**准备DSL语句**

```json
# 删除索引结构
DELETE /product

# type : text 和 keyword的区别 , text支持分词查询, keyword只能完整匹配
# index : true 代表可以根据该索引查询 , false 代表不可以
# copy_to : all 汇总到all里一起查询 , 提高效率

# 创建商品索引!
# 根据多列搜索性能较差,组成成一列搜索提高性能
PUT /product
{
  "mappings": {
    "properties": {
      "productId":{
        "type": "integer"
      },
      "productName":{
        "type": "text",
        "analyzer": "ik_smart",
        "copy_to": "all"
      },
      "categoryId":{
        "type": "integer"
      },
      "productTitle":{
        "type": "text",
        "analyzer": "ik_smart",
        "copy_to": "all"
      },
      "productIntro":{
        "type":"text",
        "analyzer": "ik_smart",
        "copy_to": "all"
      },
      "productPicture":{
        "type": "keyword",
        "index": false
      },
      "productPrice":{
        "type": "double",
        "index": true
      },
      "productSellingPrice":{
        "type": "double"
      },
      "productNum":{
        "type": "integer"
      },
      "productSales":{
        "type": "integer"
      },
      "all":{
        "type": "text",
        "analyzer": "ik_max_word"
      }
    }
  }
}



#查询索引
GET /product/_mapping

#全部查询

GET /product/_search
{
  "query": {
      "match_all": {
        
      }
  }
}

#关键字查询

GET /product/_search
{
  "query": {
     "match": {
       "all": "最好"
     }
  }

}


# 关键字 和 添加分页
GET /product/_search
{
  "query": {
     "match": {
       "all": "最好"
     }
  },
  "from": 0,
  "size": 1

}



# 添加数据
POST /product/_doc/1
{
  "productId":1,
  "productName":"雷碧",
  "productTitle":"最好的碳酸饮料",
  "categoryId":1,
  "productIntro":"硫酸+煤炭制品最好的产品!",
  "productPicture":"http://www.baidu.com",
  "productPrice":10.5,
  "productSellingPrice":6.0,
  "productNum":10,
  "productSales":5
}

# 删除数据
DELETE /product/_doc/1
```



## 项目部署

### 环境部署

```sql
JDK >= 1.8 (推荐1.8版本) 
Mysql >= 5.7.25+ (推荐8.0.25版本) 
Maven >= 3.0+ (推荐3.6.3版本)
```



### 数据库脚本导入

脚本文件: store.sql

```sql
# 将数据库脚本复制到某盘符下，方便导入！
# 执行数据库脚本导入命令！注意乱码问题！
# 步骤1:使用cmd命令窗口登录mysql，防止导入乱码问题
mysql -u账号 -p密码 --default-character-set=utf8  回车
# 步骤2:导入数据文件 注意，要写你自己的文件地址
source d:\store.sql  回车  
# 步骤3: 查看数据库
show databases;
```



### 注册中心搭建

基于docker搭建.
**拉取镜像**

```bash
docker pull nacos/nacos-server
```



**启动容器**

```bash
docker run --name nacos-quick -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:latest
```



**测试访问**
公网ip:8848/nacos , 账号:nacos 密码:nacos .



## 开发过程中遇到的问题

**程序包com.sun.org.apache.regexp.internal不存在**
jdk版本不对.

**Request 503错误**
大概率服务没有完全启动, 重启所有服务, 重新编译运行,或者等待一定时间.

**找不到store_commons依赖**
maven本地仓库抽风, 整个项目clean, 重新install store_commons.

**Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.NullPointerException] with root cause**
Controllor层引入的Service接口上面忘记写@Autowired 注解.





**数据库操作**
springboot + mybatis (Mapper/QueryWrapper)

**搜索**
springboot + es搜索中间件使用

**lombok基本使用**
主要以@Data注解, 简化代码为主

**缓存数据**
springboot + springCache + redis缓存中间件使用

**es数据库同步**
springboot + rabbitMQ消息队列中间件使用

**JDK8 stream流**
stream.map.collect



</details>