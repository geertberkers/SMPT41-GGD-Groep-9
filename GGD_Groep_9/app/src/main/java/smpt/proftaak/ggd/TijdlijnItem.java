package smpt.proftaak.ggd;

/**
 * Created by Joep on 25-6-2015.
 */
public class TijdlijnItem
{
    private String title;
    private String imgSrc;
    private String timestamp;
    private String description;

    public TijdlijnItem(String title, String imgSrc, String timestamp, String description) {
        this.title = title;
        this.imgSrc = imgSrc;
        this.timestamp = timestamp;
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }
}
