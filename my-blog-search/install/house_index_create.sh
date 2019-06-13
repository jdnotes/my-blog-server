## 创建一个索引
curl -XPOST "http://192.168.1.200:9200/blog_article_index"

## 查看一下索引mapping
curl -XGET "http://192.168.1.200:9200/blog_article_index/_mapping?pretty"

## 创建索引setting及mapping
curl -XPOST 'http://192.168.1.200:9200/blog_article_index/blog_article_type?pretty' -d '
{
	"blog_article_type": {
		"properties": {
			"id": {
				"index": "not_analyzed",
				"type": "string"
			},
			"title": {
				"analyzer": "ik_max_word",
				"store": true,
				"type": "string"
			},
			"logo": {
				"index": "not_analyzed",
				"store": true,
				"type": "string"
			},
			"author": {
				"store": true,
				"type": "string"
			},
			"tags": {
				"index": "analyzed",
				"store": true,
				"type": "string"
			},
			"tagsName": {
                "index": "analyzed",
                "store": true,
                "type": "string"
            },
			"articleType": {
				"store": true,
				"type": "integer"
			},
			"articleSection": {
				"store": true,
				"type": "string"
			},
			"remark": {
				"store": true,
				"type": "string"
			},
			"readNum": {
				"store": true,
				"type": "integer"
			},
			"likeNum": {
				"store": true,
				"type": "integer"
			},
			"sort": {
				"store": true,
				"type": "integer"
			},
			"level": {
				"store": true,
				"type": "integer"
			},
			"status": {
				"store": true,
				"type": "integer"
			},
			"updateDate": {
				"format": "strict_date_optional_time||epoch_millis",
				"store": true,
				"type": "date"
			},
			"createDate": {
				"format": "strict_date_optional_time||epoch_millis",
				"store": true,
				"type": "date"
			}
		}
	}
}
'