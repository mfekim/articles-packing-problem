package main.utils;

import java.util.ArrayList;
import java.util.List;

import main.model.Article;
import main.model.Box;

/**
 * Regroups utils methods for box.
 */
public class BoxUtils {
    /**
     * Tries to fill a box with a maximum articles.
     * <p>
     * Important: the box and articles parameters may be modified, the method does not work with
     * copies.
     *
     * @param box      A box which can already contains articles.
     * @param articles List of articles.
     * @return The same box which has been passed as parameter with probably more articles.
     */
    public static Box fillBox(Box box, List<Article> articles) {
        if (box != null && !box.isFull() && articles != null && !articles.isEmpty()) {
            List<Article> handledArticles = new ArrayList<>();
            for (int index = 0; index < articles.size() && !box.isFull(); index++) {
                Article article = articles.get(index);
                if (box.hasEnoughSpaceToAddArticle(article)) {
                    box.addArticle(article);
                    handledArticles.add(article);
                }
            }
            articles.removeAll(handledArticles);
        }

        return box;
    }

    /**
     * Shows the articles of a list of boxes.
     *
     * @param boxes List of {@link Box}.
     */
    public static void printBoxes(List<Box> boxes) {
        if (boxes != null) {
            String resultString = "";
            for (Box box : boxes) {
                String boxString = box.toString();
                if (boxString != null && !boxString.isEmpty()) {
                    resultString += boxString + "/";
                }
            }

            if (!resultString.isEmpty()) {
                System.out.println("Result: " + resultString);
                System.out.println("Nb boxes: " + boxes.size());
            } else {
                System.out.println("No boxes");
            }
        } else {
            System.out.println("No boxes");
        }
    }
}
