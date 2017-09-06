package l2y2.developer.rssreader;

/**
 * Created by CF0621 on 2017/08/31.
 */

public class RssFeedModel {

    public String date;
    public String title;
    public String link;
    public String description;

    public RssFeedModel(String title, String link, String description, String date) {
        this.date = date;
        this.title = title;
        this.link = link;
        this.description = description;
    }
}