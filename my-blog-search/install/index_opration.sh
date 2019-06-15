## 删除索引
curl -XDELETE "http://192.168.1.200:9200/blog_article_index"

## 查看一下索引mapping
curl -XGET "http://192.168.1.200:9200/blog_article_index/_mapping?pretty"
