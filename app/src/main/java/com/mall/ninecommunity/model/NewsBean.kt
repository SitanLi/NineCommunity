package com.mall.ninecommunity.model

/**
 * 包名       com.azhon.mvvm.main
 * 文件名:    NewsBean
 * 创建时间:  2019-03-27 on 15:44
 *
 * @author 阿钟
 */
class NewsBean(var date: String? = null,
               var stories: List<StoriesBean>? = null,
               var top_stories: List<TopStoriesBean>? = null) {
    override fun toString(): String {
        return "NewsBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}'
    }
}

class StoriesBean(
        var type: Int = 0,
        var id: Int = 0,
        var ga_prefix: String? = null,
        var title: String? = null,
        var images: List<String>? = null
) {
    override fun toString(): String {
        return "StoriesBean{" +
                "type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                ", images=" + images +
                '}'
    }
}

class TopStoriesBean(
        var image: String? = null,
        var type: Int = 0,
        var id: String ? = null,
        var ga_prefix: String? = null,
        var title: String? = null,
        var hint:String? = null,
        var url:String? = null
) {


    override fun toString(): String {
        return "TopStoriesBean{" +
                "image='" + image + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                '}'
    }
}