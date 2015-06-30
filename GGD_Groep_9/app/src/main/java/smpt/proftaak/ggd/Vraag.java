package smpt.proftaak.ggd;

import java.util.Map;

/**
 * Created by Joep on 30-6-2015.
 */
public class Vraag
{
    private int id;
    private String soort;
    private String vraag;
    private Map<Integer, String> symptomen;

    public Vraag(int id, String soort, String vraag, Map<Integer, String> symptomen)
    {
        this.id = id;
        this.soort = soort;
        this.vraag = vraag;
        this.symptomen = symptomen;
    }

    public int getId() {
        return id;
    }

    public String getSoort() {
        return soort;
    }

    public String getVraag() {
        return vraag;
    }

    public Map<Integer, String> getSymptomen() {
        return symptomen;
    }
}
