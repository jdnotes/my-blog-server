## 删除索引
curl -XDELETE "http://192.168.1.200:9200/blog_article_index"

## 查看一下索引mapping
curl -XGET "http://192.168.1.200:9200/blog_article_index/_mapping?pretty"

## 创建索引setting及mapping
curl -XPOST 'http://192.168.1.200:9200/blog_article_index/blog_article_type?pretty' -d '
{
	"blog_article_type": {
		"properties": {
			"articleHtml": {
				"type": "text",
				"index": false,
				"store": true
			},
			"articleSection": {
				"type": "text",
				"index": false,
				"store": true
			},
			"articleType": {
				"type": "integer",
				"index": false,
				"store": true
			},
			"author": {
				"type": "text",
				"index": false,
				"store": true
			},
			"code": {
				"type": "text",
				"index": false,
				"store": true
			},
			"createDate": {
				"type": "date",
				"index": false,
				"store": true
			},
			"id": {
				"type": "keyword"
			},
			"level": {
				"type": "integer",
				"index": false,
				"store": true
			},
			"likeNum": {
				"type": "integer",
				"index": false,
				"store": true
			},
			"logo": {
				"type": "text",
				"index": false,
				"store": true
			},
			"readNum": {
				"type": "integer",
				"index": false,
				"store": true
			},
			"remark": {
				"type": "text",
				"index": false,
				"store": true
			},
			"sort": {
				"type": "integer",
				"index": false,
				"store": true
			},
			"status": {
				"type": "integer",
				"index": false,
				"store": true
			},
			"tag": {
				"type": "text",
				"index": false,
				"store": true
			},
			"tagId": {
				"type": "text",
				"index": false,
				"store": true
			},
			"tags": {
				"type": "text",
				"store": true,
				"analyzer": "ik_smart"
			},
			"tagsName": {
				"type": "text",
				"index": false,
				"store": true
			},
			"title": {
				"type": "text",
				"store": true,
				"analyzer": "ik_max_word"
			},
			"updateDate": {
				"type": "date",
				"index": false,
				"store": true
			}
		}
	}
}
'