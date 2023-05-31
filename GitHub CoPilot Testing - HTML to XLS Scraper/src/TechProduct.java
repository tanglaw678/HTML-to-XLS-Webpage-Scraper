
public class TechProduct {
    // initialize variables
    private String title;
    private String language;
    private String stars;
    private String htmlContent;



    // getters
    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getStars() {
        return stars;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }


    // toString
    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Language: " + language + "\n" +
                "Stars: " + stars + "\n" +
                "HTML Content: " + htmlContent + "\n";
    }

}
