package com.news.adapters;

/**
 * 布局类型
 *
 * @author slioe shu
 */
public interface ViewType {
    int SEARCH_HISTORY = 0;
    int SEARCH_RESULT = 1;
    int NEWS_IMAGE = 2; // 新闻-标题+长图 URL
    int NEWS_IMAGES = 1;// 新闻-标题+多图
    int NEWS_TEXT = 0;// 新闻-新闻简介
    int NEWS_SPACIAL = 3;// 新闻-标题+长图 专题

    int PAPER_TITLE = 0;
    int PAPER_CONTENT = 1;
}
