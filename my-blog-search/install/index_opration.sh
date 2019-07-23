## 删除索引
curl -XDELETE "http://192.168.1.200:9200/blog_article_index"

## 查看一下索引mapping
curl -XGET "http://192.168.1.200:9200/blog_article_index/_mapping?pretty"

## 查询某个ID记录
curl -XGET 'http://localhost:9200/blog_article_index/blog_article_type/2588881489379348480'
