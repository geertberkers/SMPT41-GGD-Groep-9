package smpt.proftaak.ggd;

/**
 * Created by Geert on 30-6-2015
 */
public class Informatie {
    private String infoTitel;
    private String beschrijving;
    private String afbeeldingPath;
    private String laatsteUpdate;

    public Informatie(String titel, String beschrijving, String afbeeldingPath, String laatsteUpdate)
    {
        this.infoTitel = titel;
        this.beschrijving = beschrijving;
        this.afbeeldingPath = afbeeldingPath;
        this.laatsteUpdate = parseDate(laatsteUpdate);
    }

    public String getInfoTitel() {
        return infoTitel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getAfbeeldingPath() {
        return afbeeldingPath;
    }

    public String getLaatsteUpdate() {
        return laatsteUpdate;
    }

    public String parseDate(String date)
    {
        String returnDate = "Laatste update: "+ date.substring(4,9) + "-" + date.substring(0,3) + date.substring(9);
        System.out.println(date.substring(5,10));   // DATUM
        System.out.println(date.substring(0,4));    // JAAR
        System.out.println(date.substring(10));     // TIJD
        return returnDate;
    }
}
