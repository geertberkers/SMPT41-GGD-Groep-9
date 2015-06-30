package smpt.proftaak.ggd;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class VragenlijstActivity extends BaseActivity {

    private Vragenlijst vragenlijst;
    private Ramp ramp;

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

        if (id == R.id.action_sendVragenlijst) {
            //TODO
            Toast.makeText(this, "VERSTUUR VRAGENLIJST", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setData(String data)
    {
        //Execute when JSON data is retrieved
        JSONParser parser = new JSONParser(data);
        vragenlijst = parser.getVragenlijst();

        if (vragenlijst.getId() == -1)
        {
            Toast.makeText(this, "Er is op dit moment geen vragenlijst beschikbaar voor deze situatie.", Toast.LENGTH_LONG).show();
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

        VragenlijstSymptomenAdapter adapter = new VragenlijstSymptomenAdapter(this, symptomen);
        ListView list = (ListView)findViewById(R.id.vragenlijstSymptomen);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toggle checkbox on row click
                CheckBox symptoomCheck = (CheckBox)view.findViewById(R.id.symptoomCheck);
                symptoomCheck.toggle();
            }
        });
    }
}
