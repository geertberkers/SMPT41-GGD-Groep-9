package smpt.proftaak.ggd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Map;

public class VragenlijstActivity extends BaseActivity {

    private Vragenlijst vragenlijst;
    private Ramp ramp;
    private ListView symptomenList;
    private ListView openvragenList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vragenlijst);

        ramp = getIntent().getParcelableExtra("ramp");

        APICallTask apiTest = new APICallTask(this, APICallType.GET_VRAGENLIJST, "http://stanjan.nl/smpt/API/vragen.php?id=" + ramp.getID());
        apiTest.execute();
    }

    /**
     * Deze methode wordt gebruikt om de hoogte van de listview met symptomen aan te geven.
     * Als deze methode niet wordt aangeroepen is de lsitview slechts 1 row hoog
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vragenlijst, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String antwoordURL = "";

        if (id == R.id.action_sendVragenlijst) {
            try
            {
                antwoordURL = buildAntwoordURL();
            }
            catch (IllegalArgumentException ex)
            {
                finish();
            }

            sendAntwoordToAPI(antwoordURL);
        }

        return super.onOptionsItemSelected(item);
    }

    private String buildAntwoordURL()
    {
        String baseURL = "http://stanjan.nl/smpt/API//antwoord.php?id=";

        //Add ramp id to url
        baseURL += ramp.getID();

        //Add user email to url
        SharedPreferences prefs = this.getSharedPreferences("smpt.proftaak.ggd", Context.MODE_PRIVATE);
        String email = prefs.getString(getString(R.string.sharedpref_email), "");
        if (email.length() == 0)
        {
            Toast.makeText(this, "Uw emailadres kon niet worden opgehaald. Log a.u.b. eerst in.", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("INVALID EMAIL");
        }
        baseURL += "&email=" + email;

        //Add user postcode to url
        String postcode = prefs.getString(getString(R.string.sharedpref_postcode), "");
        if (postcode.length() == 0)
        {
            Toast.makeText(this, "Uw locatie kon niet worden opgehaald. Log a.u.b. eerst in.", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("INVALID POSTCODE");
        }
        baseURL += "&postcode=";

        //Add antwoorden to url
        baseURL += "&antwoord=";

        //symptoomvraag
        String symptomenAntwoord = "";

        for (int i = 0; i < symptomenList.getCount(); i++)
        {
            View currentRow = symptomenList.getChildAt(i);
            CheckBox symptoomCheck = (CheckBox)currentRow.findViewById(R.id.symptoomCheck);

            if (symptoomCheck.isChecked())
            {
                symptomenAntwoord += "Y";
            }
            else
            {
                symptomenAntwoord += "N";
            }
        }
        baseURL += symptomenAntwoord;

        //open vragen
        for (int i = 0; i < openvragenList.getCount(); i++)
        {
            View currentRow = openvragenList.getChildAt(i);
            String answer = ((EditText)currentRow.findViewById(R.id.openvraagInput)).getText().toString();

            //replace spaces to url format
            answer = answer.replaceAll(" ", "%20");

            //zorg dat answer niet leeg is
            if (answer.length() == 0)
            {
                answer = "null";
            }

            //add to base url
            baseURL += "$" + answer;
        }

        //return result
        System.out.println(baseURL);
        return baseURL;
    }

    public void setData(String data)
    {
        //Execute when JSON data is retrieved
        JSONParser parser = new JSONParser(data);
        vragenlijst = parser.getVragenlijst();

        if (vragenlijst.getId() == -1)
        {
            Toast.makeText(this, "Er is op dit moment geen vragenlijst beschikbaar voor deze situatie.", Toast.LENGTH_SHORT).show();
            finish();
        }

        ArrayList<String> symptomen = new ArrayList<>();

        if (vragenlijst.getSymptoomVragen() != null)
        {
            for (Map.Entry<Integer, String> current: vragenlijst.getSymptoomVragen().entrySet())
            {
                symptomen.add(current.getValue());
            }
        }
        else
        {
            TextView symptomenTitle = (TextView)findViewById(R.id.vragenlijstTitle);
            symptomenTitle.setVisibility(View.GONE);
        }

        VragenlijstSymptomenAdapter symptomenAdapter = new VragenlijstSymptomenAdapter(this, symptomen);
        symptomenList = (ListView)findViewById(R.id.vragenlijstSymptomen);
        symptomenList.setAdapter(symptomenAdapter);
        setListViewHeightBasedOnChildren(symptomenList);

        symptomenList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toggle checkbox on row click
                CheckBox symptoomCheck = (CheckBox) view.findViewById(R.id.symptoomCheck);
                symptoomCheck.toggle();
            }
        });

        ArrayList<Vraag> openVragen = new ArrayList<>();
        for (Vraag current: vragenlijst.getVragen())
        {
            if (current.getSoort().equals("open"))
            {
                openVragen.add(current);
            }
        }

        VragenlijstOpenVragenAdapter openAdapter = new VragenlijstOpenVragenAdapter(this, openVragen);
        openvragenList = (ListView)findViewById(R.id.vragenlijstOpenVragen);
        openvragenList.setAdapter(openAdapter);
        setListViewHeightBasedOnChildren(openvragenList);
    }

    private void sendAntwoordToAPI(String antwoordString)
    {
        APICallTask task = new APICallTask(this, APICallType.SEND_ANTWOORD, antwoordString);
        task.execute();
    }

    public void receiveResponse(String response)
    {
        if (response.toLowerCase().equals("succes!"))
        {
            Toast.makeText(this, "Uw vragenlijst is verstuurd. Bedankt voor uw medewerking.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Uw vragenlijst kon helaas niet verstuurd worden.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
