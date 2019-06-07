# docker安装Elasticsearch

## 准备docker环境
系统环境:CentOS,docker

## 拉取镜像

```shell
sudo docker search elasticsearch

docker pull elasticsearch
```

## 运行容器
ElasticSearch的默认端口是9200，我们把宿主环境9200端口映射到Docker容器中的9200端口，就可以访问到Docker容器中的ElasticSearch服务了，同时我们把这个容器命名为es。
注意：5.0默认分配jvm空间大小为2g,5.0之前好像是1g。若你的服务器内存够大请随意。

```shell
$ docker run --name es -d -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -p 9200:9200 -p 9300:9300 elasticsearch
07294dbb5936bdbb6b012befbddf725f2835625e18d0270c5cb6b4044bfb4541
$ sudo docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                            NAMES
07294dbb5936        elasticsearch       "/docker-entrypoint.…"   8 seconds ago       Up 6 seconds        0.0.0.0:9200->9200/tcp, 0.0.0.0:9300->9300/tcp   es
```

## 测试
浏览器或控制台输入：http://192.168.1.xxx:9200/ (你的服务器ip:端口号)

浏览器返回如下信息，证明安装成功。(ps:需要开启9200端口号)

```
[dev@bogon ~]$ curl 192.168.1.xxx:9200
{
  "name" : "b8BDG5Q",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "goK3oOSoTV6eUq0JTaJvxw",
  "version" : {
    "number" : "5.6.12",
    "build_hash" : "cfe3d9f",
    "build_date" : "2018-09-10T20:12:43.732Z",
    "build_snapshot" : false,
    "lucene_version" : "6.6.1"
  },
  "tagline" : "You Know, for Search"
}
```

## 查看容器运行日志

```shell
docker logs -f 07294dbb5936
```

## 进入容器
由于要进行配置，因此需要进入容器当中修改相应的配置信息。

```shell
docker exec -it es /bin/bash
```

## 错误问题
1.提示：vm.max_map_count [65530] is too low, increase to at least [262144]，说max_map_count的值太小了，需要设大到262144
查看max_map_count的值

```
$ cat /proc/sys/vm/max_map_count
65530
```

重新设置max_map_count的值

```
$ sysctl -w vm.max_map_count=262144
vm.max_map_count = 262144
```

