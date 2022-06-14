package fr.yan36.westerlife.client.utils;

public class News {

    private final String url;
    private final String title;
    private final String content;

    public News(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
