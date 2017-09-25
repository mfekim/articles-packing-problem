package main.model;

/**
 * An article with a size.
 * <p>
 * Note: we suppose here that the size is between 1 and 9, we do not check it.
 */
public class Article implements Comparable<Article> {
    /**
     * Article's size.
     */
    private int mSize;

    /**
     * Constructor.
     *
     * @param size Article size.
     */
    public Article(int size) {
        mSize = size;
    }

    /**
     * @return Article's size.
     */
    public int getSize() {
        return mSize;
    }

    @Override
    public int compareTo(Article article) {
        return Integer.compare(mSize, article.getSize());
    }

    @Override
    public String toString() {
        return String.valueOf(mSize);
    }
}
