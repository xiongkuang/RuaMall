**模块信息**

    rua-common:共有工具
    rua-manager:商品管理服务
    rua-manager-web:集成管理模块
    rua-content:内容管理服务
    rua-front-web:首页

**端口信息：**

    数据库：3306
    dubbo-admin：8083
    
    
    rua-sso-web:      8082
    rua-item-web:     8084
    rua-content:      8085
    rua-search-web:   8086
    rua-front-web:    8087
    rua-manager：     8088
    rua-manager-web： 8089
    rua-search:       8090
    rua-sso:          8091
    rua-cart:         8092
    
    
    zookeeper：2181
    
    activeMq: 8161(admin,admin)
    
    本机enginx:8888   /usr/local/etc/nginx.conf
    本机enginx2:8889
    
    dubbo服务：
        rua-manager: 20880
        rua-content: 20881
        rua-search:  20882
        rua-sso:     20883
        rua-cart:    20884
        
    FastDFS服务器：192.168.25.133



**启动**

    ~$ zkServer start
    ~$ activemq start
    ~$ solr start
    ~$ redis-server /usr/local/etc/redis.conf
    启动图片服务器
    
    
    
**redis**

    单机版端口号 6379
    ~$ redis-server /usr/local/etc/redis.conf
    集群版  7001 ～ 7003（master） 7004 ～ 7006（slave）
    /usr/local/Cellar/redis/redis-cluster
    启动：./start-all.sh
    
    ~$ redis-cli -p 700x -c


**solr**
        
       solr start
       localhost:8983
    
    