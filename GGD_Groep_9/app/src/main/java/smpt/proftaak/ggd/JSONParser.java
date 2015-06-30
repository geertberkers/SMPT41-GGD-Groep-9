package smpt.proftaak.ggd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joep on 30-6-2015.
 */
public class JSONParser
{
    private String jsonString;

    public JSONParser(String jsonString)
    {
        this.jsonString = jsonString;
    }

    public Vragenlijst getVragenlijst() {
        //init vragenlijst variables
        int resultId = -1;
        String resultTijdstip = "";
        ArrayList<Vraag> resultVragen = new ArrayList<>();

        //create JSON  object
        try {
            //init root object
            JSONArray rootArray = new JSONArray(jsonString);
            JSONObject jsonObject = (JSONObject)rootArray.get(0);

            //get vragenlijst info
            resultId = jsonObject.getInt("id");
            resultTijdstip = jsonObject.getString("tijdstip");

            //get vragen
            JSONArray vragenArray = jsonObject.getJSONArray("vragen");
            for (int i = 0; i < vragenArray.length(); i++)
            {
                //init vraag variables
                int vraagId = -1;
                String vraagSoort = "";
                String vraag = "";
                Map<Integer, String> vraagSymptomen = new HashMap<Integer, String>();

                //get info for each vraag
                JSONObject currentVraag = vragenArray.getJSONObject(i);
                vraagId = currentVraag.getInt("id");
                vraagSoort = currentVraag.getString("soort");
                vraag = currentVraag.getString("vraag");

                //get symptomen
                if (currentVraag.has("symptomen"))
                {
                    JSONArray symptomenArray = currentVraag.getJSONArray("symptomen");
                    for (int j = 0; j < symptomenArray.length(); j++)
                    {
                        //get info for each symptoom
                        JSONObject currentSymptoom = symptomenArray.getJSONObject(j);
                        vraagSymptomen.put(currentSymptoom.getInt("id"), currentSymptoom.getString("symptoom"));
                    }
                }

                //create new vraag based on info and add to vragenlijst
                Vraag newVraag = new Vraag(vraagId, vraagSoort, vraag, vraagSymptomen);
                resultVragen.add(newVraag);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        //return vragenlijst
        Vragenlijst result = new Vragenlijst(resultId, resultTijdstip, resultVragen);
        return result;
    }
}
