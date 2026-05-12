package controllers;

import core.DataStorage;
import model.research.News;

import java.util.Date;
public class NewsController {
    public static boolean publishNews(String title,
                                      String content,
                                      String topic) {

        News news = new News(title, content, topic, new Date());
        DataStorage.addNews(news);
        boolean result = DataStorage.save();
        if (news.isPinned()) {
            System.out.println("[News] Published (PINNED): " + title);
        } else {
            System.out.println("[News] Published: " + title);
        }
        return result;
    }

    public static void listNews() {
        System.out.println("=== News (pinned first) ===");
        for (News n : DataStorage.getNews()) {
            String prefix;
            if (n.isPinned()) {
                prefix = "  [📌] ";
            } else {
                prefix = "  [ ] ";
            }
            System.out.println(
                    prefix
                            + n.getTitle()
                            + " | "
                            + n.getTopic()
            );
        }
    }
}