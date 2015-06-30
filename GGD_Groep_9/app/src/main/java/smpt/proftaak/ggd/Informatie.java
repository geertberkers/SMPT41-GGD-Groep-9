package smpt.proftaak.ggd;

import android.graphics.Bitmap;

/**
 * Created by Geert on 30-6-2015
 */
public class Informatie {
    private String infoTitel;
    private String beschrijving;
    private String afbeeldingPath;
    private String laatsteUpdate;
    private Bitmap warningImg;

    public Informatie(String titel, String beschrijving,String afbeelding, String laatsteUpdate)
    {
        this.infoTitel = titel;
        this.beschrijving = beschrijving;
        this.afbeeldingPath = afbeelding;
        this.laatsteUpdate = parseDate(laatsteUpdate);
    }

    public String getInfoTitel() {
        return infoTitel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getLaatsteUpdate() {
        return laatsteUpdate;
    }

    public String parseDate(String date)
    {
        return "Laatste update: "+ date.substring(5,10) + "-" + date.substring(0,4) + date.substring(10);
    }

    public Bitmap getWarningImg() {
        return warningImg;
    }

    public void setWarningImg(Bitmap warningImg) {
        this.warningImg = warningImg;
    }

    public String getAfbeeldingPath() {
        return afbeeldingPath;
    }
}
