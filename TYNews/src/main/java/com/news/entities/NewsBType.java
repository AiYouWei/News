package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.List;

/**
 * @author slioe shu
 */
public class NewsBType implements BaseType {
    private String img;
    private String intro;
    private List<Item> item;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public class Item implements BaseType {
        private List<NewsItem> item_news;
        private String item_tid;
        private String item_title;

        public List<NewsItem> getItem_news() {
            return item_news;
        }

        public void setItem_news(List<NewsItem> item_news) {
            this.item_news = item_news;
        }

        public String getItem_tid() {
            return item_tid;
        }

        public void setItem_tid(String item_tid) {
            this.item_tid = item_tid;
        }

        public String getItem_title() {
            return item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public class NewsItem implements BaseType {
            private String news_title;
            private String news_id;
            private String news_subtitle;
            private String news_icon;

            public String getNews_title() {
                return news_title;
            }

            public void setNews_title(String news_title) {
                this.news_title = news_title;
            }

            public String getNews_id() {
                return news_id;
            }

            public void setNews_id(String news_id) {
                this.news_id = news_id;
            }

            public String getNews_subtitle() {
                return news_subtitle;
            }

            public void setNews_subtitle(String news_subtitle) {
                this.news_subtitle = news_subtitle;
            }

            public String getNews_icon() {
                return news_icon;
            }

            public void setNews_icon(String news_icon) {
                this.news_icon = news_icon;
            }
        }
    }
}
