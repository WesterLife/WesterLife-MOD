package fr.gabidut76.westerlife.client.utils;

public class News {

    private final String abouturl;
    private final String title;
    private final String content;
    private final String author;

    public News(String title, String content, String author, String abouturl) {
        this.title = title;
        this.content = content;
        this.abouturl = abouturl;
        this.author = author;
    }

    public String getAbouturl() {
        return abouturl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
