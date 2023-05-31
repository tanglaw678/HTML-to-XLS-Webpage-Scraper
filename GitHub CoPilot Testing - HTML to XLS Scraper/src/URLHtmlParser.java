import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class URLHtmlParser {
    public static void main(String[] args) {
        List<TechProduct> techProducts = new ArrayList<>();
        Set<String> pagesParsed = new HashSet<>();
        List<String> pagesToParse = new ArrayList<>();

        pagesToParse.add("https://gitee.com/explore/new-tech");


        int i = 0;
        int limit = 4;

        while (!pagesToParse.isEmpty() && i < limit) {
            try {
                String htmlContent = parseHtmlContent(techProducts, pagesParsed, pagesToParse);
                saveToTextFile(htmlContent, "output" + i + ".txt");
                System.out.println("Saved to output" + i + ".txt");
                techProducts.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            i++;
        }
    }

    public static String parseHtmlContent(
            List<TechProduct> techProducts,
            Set<String> pagesParsed,
            List<String> pagesToParse
    ) throws IOException {
        if (!pagesToParse.isEmpty()) {
            String url = pagesToParse.remove(0);

            pagesParsed.add(url);

            Document document;

            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements items = document.select("div.content");


            for (Element item : items) {
                TechProduct techProduct = new TechProduct();
                techProduct.setTitle(parseProjectTitle(item));
                techProduct.setLanguage(parseProjectLanguage(item));
                techProduct.setStars(parseProjectStars(item));
                techProduct.setHtmlContent(parseProjectHtmlContent(item));
                techProducts.add(techProduct);

                System.out.println("New Item");
            }

            Elements pageNumbers = document.select("a.icon.item");

            for (Element pageNumber : pageNumbers) {
                String link = pageNumber.attr("abs:href");

                if (!pagesParsed.contains(link) && !pagesToParse.contains(link)) {
                    pagesToParse.add(link);
                    System.out.println("Added to pagesToParse: " + link);
                }
            }

            return techProducts.toString();
        }

        return null;
    }

    private static String parseProjectTitle(Element item) {
        Elements projectTitle = item.select("a");
        return projectTitle.text();
    }

    private static String parseProjectLanguage(Element item) {
        Elements projectLanguage = item.select("span");
        return projectLanguage.text();
    }

    private static String parseProjectStars(Element item) {
        Elements projectStars = item.select("div.stars-count");
        return projectStars.text();
    }

    private static String parseProjectHtmlContent(Element item) {
        Elements projectHtmlContent = item.select("div.project-desc.mb-1");
        return projectHtmlContent.text();
    }

    private static void saveToTextFile(String content, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
}
