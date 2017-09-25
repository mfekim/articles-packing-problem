package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import main.model.Article;
import main.model.Box;
import main.utils.BoxUtils;

/**
 * Main entry point.
 */
public class Main {
    /**
     * The solutions/algorithms "types" to solve the problem.
     * <p>
     * {@link #SORTING_SOLUTION} sorts the articles in descending order before packing them.
     * {@link #FILL_SOLUTION} takes articles one by one and add them into a box if it has enough
     * space.
     * {@link #MAP_SOLUTION} makes a map which associates a list of articles to an article size
     * and work with it to pack the articles.
     * <p>
     * Note: typically, I use ENUM for this but in Android I always prefer to use constants because
     * it is recommended due to possible performance issue.
     */
    private static final int SORTING_SOLUTION = 0;
    private static final int FILL_SOLUTION = 1;
    private static final int MAP_SOLUTION = 2;

    /**
     * The famous main method :)
     *
     * @param arg Arguments.
     */
    public static void main(String[] arg) {
        // Examples :
        // 163841689525773
        // 53789357725987638927987528622862
        // 727272723333
        // 538758753997248717437
        // 39587853971326429846233212534289857698234152447298487216422984
        List<Article> articles = scanArticles();

        System.out.println("---Sorting Solution");
        List<Box> boxes = packArticles(articles, SORTING_SOLUTION);
        BoxUtils.printBoxes(boxes);
        System.out.println("-------------------");

        System.out.println("---Fill Solution");
        boxes = packArticles(articles, FILL_SOLUTION);
        BoxUtils.printBoxes(boxes);
        System.out.println("----------------");

        System.out.println("---Map Solution");
        boxes = packArticles(articles, MAP_SOLUTION);
        BoxUtils.printBoxes(boxes);
        System.out.println("---------------");
    }

    /**
     * Packs articles into boxes.
     *
     * @param articles     List of {@link Article}.
     * @param solutionType The packing solution to use.
     * @return A list of {@link Box}, a empty list otherwise.
     */
    private static List<Box> packArticles(List<Article> articles, int solutionType) {
        switch (solutionType) {
            case SORTING_SOLUTION:
                return runSortingSolution(articles);
            case FILL_SOLUTION:
                return runFillSolution(articles);
            case MAP_SOLUTION:
                return runMapSolution(articles);
            default:
                System.err.println("ERROR - the solution type " + solutionType + " is unknown!");
                return new ArrayList<>();
        }
    }

    /**
     * Packs articles into boxes by using the {@link #SORTING_SOLUTION}.
     *
     * @param articles List of {@link Article}.
     * @return A list of {@link Box}, a empty list otherwise.
     */
    private static List<Box> runSortingSolution(List<Article> articles) {
        List<Box> boxes = new ArrayList<>();

        if (articles != null && !articles.isEmpty()) {
            List<Article> articlesCopy = new ArrayList<>(articles);

            // Step 1: sort the articles in descending order
            Collections.sort(articlesCopy, Collections.reverseOrder());

            // Step 2: create the boxes by
            // + Adding the first article (the biggest)
            // + Adding the last articles (the smaller ones) until the box cannot contains anymore
            // articles
            while (!articlesCopy.isEmpty()) {
                Box box = new Box();
                box.addArticle(articlesCopy.remove(0));
                while (!articlesCopy.isEmpty()) {
                    Article smallestArticle = articlesCopy.get(articlesCopy.size() - 1);
                    if (box.hasEnoughSpaceToAddArticle(smallestArticle)) {
                        box.addArticle(smallestArticle);
                        articlesCopy.remove(smallestArticle);
                    } else {
                        break;
                    }
                }
                boxes.add(box);
            }
        }

        return boxes;
    }

    /**
     * Packs articles into boxes by using the {@link #FILL_SOLUTION}.
     *
     * @param articles List of {@link Article}.
     * @return A list of {@link Box}, a empty list otherwise.
     */
    private static List<Box> runFillSolution(List<Article> articles) {
        List<Box> boxes = new ArrayList<>();

        if (articles != null && !articles.isEmpty()) {
            List<Article> articlesCopy = new ArrayList<>(articles);
            while (!articlesCopy.isEmpty()) {
                Box box = new Box();
                box.addArticle(articlesCopy.remove(0));
                Set<Article> handledArticles = new HashSet<>();
                for (Article article : articlesCopy) {
                    if (box.hasEnoughSpaceToAddArticle(article)) {
                        box.addArticle(article);
                        handledArticles.add(article);
                    } else if (box.isFull()) {
                        break;
                    }
                }
                articlesCopy.removeAll(handledArticles);
                boxes.add(box);
            }
        }

        return boxes;
    }

    /**
     * Packs articles into boxes by using the {@link #MAP_SOLUTION}.
     * <p>
     * TODO: do refactoring to render this method more generic.
     *
     * @param articles List of {@link Article}.
     * @return A list of {@link Box}, a empty list otherwise.
     */
    private static List<Box> runMapSolution(List<Article> articles) {
        List<Box> boxes = new ArrayList<>();

        if (articles != null && !articles.isEmpty()) {
            List<Article> articlesCopy = new ArrayList<>(articles);

            // Step 1: create a map which associates a empty list of articles to an article size.
            Map<Integer, List<Article>> articlesMap = new HashMap<>(9);
            for (int i = 1; i < 10; i++) {
                articlesMap.put(i, new ArrayList<>());
            }

            // Step 2: init the map
            for (Article article : articlesCopy) {
                articlesMap.get(article.getSize()).add(article);
            }

            // Step 3: retrieve the lists of articles
            List<Article> articlesOne = articlesMap.get(1);
            List<Article> articlesTwo = articlesMap.get(2);
            List<Article> articlesThree = articlesMap.get(3);
            List<Article> articlesFour = articlesMap.get(4);
            List<Article> articlesFive = articlesMap.get(5);
            List<Article> articlesSix = articlesMap.get(6);
            List<Article> articlesSeven = articlesMap.get(7);
            List<Article> articlesHeight = articlesMap.get(8);
            List<Article> articlesNine = articlesMap.get(9);

            // Step 4: handle the "nine articles"
            while (!articlesNine.isEmpty()) {
                Box box = new Box();
                box.addArticle(articlesNine.remove(0));

                if (!articlesOne.isEmpty()) {
                    box.addArticle(articlesOne.remove(0));
                }

                boxes.add(box);
            }

            // Step 5: handle the "height articles"
            while (!articlesHeight.isEmpty()) {
                Box box = new Box();
                box.addArticle(articlesHeight.remove(0));

                if (!articlesTwo.isEmpty()) {
                    box.addArticle(articlesTwo.remove(0));
                }

                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 6: handle the "seven articles"
            while (!articlesSeven.isEmpty()) {
                Box box = new Box();
                box.addArticle(articlesSeven.remove(0));

                if (!articlesThree.isEmpty()) {
                    box.addArticle(articlesThree.remove(0));
                }

                BoxUtils.fillBox(box, articlesTwo);
                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 7: handle the "six articles"
            while (!articlesSix.isEmpty()) {
                Box box = new Box();
                box.addArticle(articlesSix.remove(0));

                if (!articlesFour.isEmpty()) {
                    box.addArticle(articlesFour.remove(0));
                }

                BoxUtils.fillBox(box, articlesThree);
                BoxUtils.fillBox(box, articlesTwo);
                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 8: handle the "five articles"
            while (!articlesFive.isEmpty()) {
                Box box = new Box();

                BoxUtils.fillBox(box, articlesFive);
                BoxUtils.fillBox(box, articlesFour);
                BoxUtils.fillBox(box, articlesThree);
                BoxUtils.fillBox(box, articlesTwo);
                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 9: handle the "four articles"
            while (!articlesFour.isEmpty()) {
                Box box = new Box();

                BoxUtils.fillBox(box, articlesFour);
                BoxUtils.fillBox(box, articlesThree);
                BoxUtils.fillBox(box, articlesTwo);
                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 10: handle the "three articles"
            while (!articlesThree.isEmpty()) {
                Box box = new Box();

                BoxUtils.fillBox(box, articlesThree);
                BoxUtils.fillBox(box, articlesTwo);
                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 11: handle the "two articles"
            while (!articlesTwo.isEmpty()) {
                Box box = new Box();

                BoxUtils.fillBox(box, articlesTwo);
                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }

            // Step 12: handle the "one articles"
            while (!articlesOne.isEmpty()) {
                Box box = new Box();

                BoxUtils.fillBox(box, articlesOne);

                boxes.add(box);
            }
        }

        return boxes;
    }

    /**
     * Scans a list of articles given by the user.
     *
     * @return A list of {@link Article}.
     */
    private static List<Article> scanArticles() {
        List<Article> articles = new ArrayList<>();
        boolean invalidInput;
        do {
            invalidInput = false;
            System.out.println("Please enter a list of articles (without spaces - between 1 and 9): ");
            Scanner in = new Scanner(System.in);
            try {
                for (char c : in.nextLine().toCharArray()) {
                    articles.add(new Article(Integer.parseInt(c + "")));
                }
            } catch (NumberFormatException e) {
                System.err.println("ERROR - The list of articles must contain only digits!");
                invalidInput = true;
                in.nextLine();
            }
        } while (invalidInput);

        return articles;
    }
}
