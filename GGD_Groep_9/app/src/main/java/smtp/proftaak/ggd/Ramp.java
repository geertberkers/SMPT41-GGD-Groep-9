package smtp.proftaak.ggd;

/**
 * Created by Geert on 18-6-2015
 */
public class Ramp {

    private String titelRamp;
    private String laatsteUpdate;
    private String omschrijving;


    public Ramp(String titelRamp, String laatsteUpdate, String omschrijving) {
        this.titelRamp = titelRamp;
        this.laatsteUpdate = laatsteUpdate;
        this.omschrijving = omschrijving;
    }

    public String getTitelRamp() {
        return titelRamp;
    }

    public void setTitelRamp(String titelRamp) {
        this.titelRamp = titelRamp;
    }

    public String getLaatsteUpdate() {
        return laatsteUpdate;
    }

    public void setLaatsteUpdate(String laatsteUpdate) {
        this.laatsteUpdate = laatsteUpdate;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
