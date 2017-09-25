package main.model;

import java.util.HashSet;
import java.util.Set;

/**
 * A box which can contains articles. Its size is the sum of the articles' size.
 */
public class Box {
    /**
     * Maximum size of a box.
     * For instance, a box can contains three articles with the following sizes: 1, 6, 3.
     */
    private static final int MAX_SIZE = 10;

    /**
     * Articles.
     */
    private Set<Article> mArticles = new HashSet<>(MAX_SIZE);

    /**
     * Adds an article.
     *
     * @param article Article.
     */
    public void addArticle(Article article) {
        if (article != null) {
            int size = getSize();
            if (size < MAX_SIZE) {
                if (size + article.getSize() <= MAX_SIZE) {
                    mArticles.add(article);
                } else {
                    // TODO throw an exception.
                }
            } else {
                // TODO throw an exception.
            }
        }
    }

    /**
     * @param article Article.
     * @return true if there is enough space to add a given article, false otherwise.
     */
    public boolean hasEnoughSpaceToAddArticle(Article article) {
        if (article != null) {
            return getSize() + article.getSize() <= MAX_SIZE;
        }

        // TODO throw an exception.
        return false;
    }

    /**
     * @return True if the box is full, false otherwise.
     */
    public boolean isFull() {
        return getSize() == MAX_SIZE;
    }

    @Override
    public String toString() {
        String res = "";
        if (mArticles != null) {
            for (Article article : mArticles) {
                res += article.toString();
            }
        }

        return res;
    }

    /**
     * @return The box's size which is the sum of the articles' sizes.
     */
    private int getSize() {
        int size = 0;
        if (mArticles != null) {
            for (Article article : mArticles) {
                size += article.getSize();
            }
        }

        return size;
    }
}
