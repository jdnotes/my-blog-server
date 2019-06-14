# docker安装Elasticsearch

## 准备docker环境
系统环境:CentOS,docker

## 拉取镜像

```shell
sudo docker search elasticsearch

sudo docker pull elasticsearch
```

## 运行容器
ElasticSearch的默认端口是9200，我们把宿主环境9200端口映射到Docker容器中的9200端口，就可以访问到Docker容器中的ElasticSearch服务了，同时我们把这个容器命名为es。
注意：5.0默认分配jvm空间大小为2g,5.0之前好像是1g。若你的服务器内存够大请随意。

```shell
$ sudo docker run --name es -d -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -p 9200:9200 -p 9300:9300 elasticsearch
07294dbb5936bdbb6b012befbddf725f2835625e18d0270c5cb6b4044bfb4541
$ sudo docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                            NAMES
07294dbb5936        elasticsearch       "/docker-entrypoint.…"   8 seconds ago       Up 6 seconds        0.0.0.0:9200->9200/tcp, 0.0.0.0:9300->9300/tcp   es
```

## 测试
浏览器或控制台输入：http://192.168.1.xxx:9200/ (你的服务器ip:端口号)
浏览器返回如下信息，证明安装成功。(ps:需要开启9200端口号)
我是在控制台查看的:
```
[dev@bogon ~]$ curl localhost:9200
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
$ sudo docker logs -f es
```

## 进入容器
由于要进行配置，因此需要进入容器当中修改相应的配置信息。

```shell
$ sudo docker exec -it es /bin/bash
root@ebee5bd008fc:/usr/share/elasticsearch# ls
NOTICE.txt  README.textile  bin  config  data  lib  logs  modules  plugins
root@ebee5bd008fc:/usr/share/elasticsearch# pwd
/usr/share/elasticsearch
root@ebee5bd008fc:/usr/share/elasticsearch# exit;
exit
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
## 临时更改配置
$ sysctl -w vm.max_map_count=262144
vm.max_map_count = 262144
#或永久更改配置
$ /etc/sysctl.conf
vm.max_map_count=262144
$ sysctl -p
```

2.在使用docker容器时。有时候里边没有安装vim。敲vim命令时提示说：vim: command not found，这个时候就须要安装vim，但是当你敲apt-get install vim命令时，提示：

```shell
Reading package lists... Done
Building dependency tree
Reading state information... Done
E: Unable to locate package vim
```

这时候须要敲：apt-get update。这个命令的作用是：同步 /etc/apt/sources.list 和 /etc/apt/sources.list.d 中列出的源的索引。这样才干获取到最新的软件包。
等更新完成以后再敲命令：apt-get install vim 命令就可以。

3.怎么修改无法启动的docker容器的配置？
原因：
由于错误的配置导致原来可以启动的docker容器不能启动了。相信很多人的做法是删除容器重建一个，这样也是可以的，但是你的配置和插件就得重新安装，非常麻烦。
最小的代价当然是修改原来的配置让他能启动了。docker容器起不来了怎么改配置？

解决方案：
由于我开启的容器是elasticsearch-6.6.1，安装了ik插件，修改配置重启时，起不来了，瘫痪了。正确的解决姿势是将容器的配置cp出来（到宿主机），修改正确然后cp回去，启动就行了。

我的容器：es-node-1，容器配置路径：/usr/share/elasticsearch/config

```shell
$ cd /tmp
$ sudo docker cp es-node-1:/usr/share/elasticsearch/config/elasticsearch.yml . #cp出来，到宿主机当前目录下
vi elasticsearch.yml #修改配置
docker cp elasticsearch.yml es-node-1:/usr/share/elasticsearch/config/elasticsearch.yml #cp回去
docker start es-node-1 #重启
```

4.启动java客户端报NoNodeAvailableException[None of the configured nodes are available:
[{#transport#-1}{uWCHq4EvTTSMJGCCuP4ThA}{192.168.1.200}{192.168.1.200:9300}]错误。

```shell
## 修改elasticsearch.yml配置
http.host: 0.0.0.0
cluster.name: my-blog
network.host: 0.0.0.0
http.port: 9200
transport.tcp.port: 9300
network.publish_host: 192.168.1.200
```

## 安装ik分词器
查看版本
```
[dev@bogon ~]$ curl 192.168.1.200:9200
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
可知,Elasticsearch的版本号是5.6.12。

### 方式一:在线安装
进入容器
```
docker exec -it es /bin/bash
``
在线下载并安装
```
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.12/elasticsearch-analysis-ik-5.6.12.zip
```
进入plugins可以看到IK分词器已经安装成功。
```

```

### 方式二:离线安装
将IK分词器(elasticsearch-analysis-ik-5.6.12.zip)上传到/tmp目录中

将压缩包移动到容器中
```
docker cp /tmp/elasticsearch-analysis-ik-5.6.12.zip es:/usr/share/elasticsearch/plugins
```
进入容器
```
$ docker exec -it es /bin/bash
### 创建目录
$ mkdir /usr/share/elasticsearch/plugins/ik
### 将文件压缩包移动到ik中
$ mv /usr/share/elasticsearch/plugins/elasticsearch-analysis-ik-5.6.12.zip /usr/share/elasticsearch/plugins/ik
### 进入目录
$ cd /usr/share/elasticsearch/plugins/ik
### 解压文件到ik目录
$ unzip elasticsearch-analysis-ik-5.6.12.zip
elasticsearch
## 移动elasticsearch目录中内容到ik目录中
$ cd elasticsearch
$ mv * ../../ik/
$ cd /usr/share/elasticsearch/plugins/ik
$ ls
commons-codec-1.9.jar  commons-logging-1.2.jar	config	elasticsearch  elasticsearch-analysis-ik-5.6.12.jar
httpclient-4.5.2.jar  httpcore-4.4.4.jar  plugin-descriptor.properties

### 删除压缩包
rm -rf elasticsearch-analysis-ik-5.6.12.zip
### 退出并重启镜像
exit
### 重启ES
docker restart es
```

### 测试
```
$ curl -X POST "192.168.1.200:9200/_analyze" -H 'Content-Type: application/json' -d'
{
  "analyzer": "ik_max_word",
  "text":     "我是一个可爱的人."
}
'

{
	"tokens": [
	{
		"token": "我",
		"start_offset": 0,
		"end_offset": 1,
		"type": "CN_CHAR",
		"position": 0
	}, {
		"token": "是",
		"start_offset": 1,
		"end_offset": 2,
		"type": "CN_CHAR",
		"position": 1
	}, {
		"token": "一个",
		"start_offset": 2,
		"end_offset": 4,
		"type": "CN_WORD",
		"position": 2
	}, {
		"token": "一",
		"start_offset": 2,
		"end_offset": 3,
		"type": "TYPE_CNUM",
		"position": 3
	}, {
		"token": "个",
		"start_offset": 3,
		"end_offset": 4,
		"type": "COUNT",
		"position": 4
	}, {
		"token": "可爱",
		"start_offset": 4,
		"end_offset": 6,
		"type": "CN_WORD",
		"position": 5
	}, {
		"token": "的人",
		"start_offset": 6,
		"end_offset": 8,
		"type": "CN_WORD",
		"position": 6
	}]
}
```
可以看到，对于每个term，记录了它的位置和偏移量.