package smpt.proftaak.ggd;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class VragenlijstActivity extends ActionBarActivity {

    private ArrayList<String> symptomen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vragenlijst);

        symptomen = populateSymptomen();

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

    private ArrayList<String> populateSymptomen()
    {
        ArrayList<String> TESTDATA = new ArrayList<>();
        TESTDATA.add("Buik");
        TESTDATA.add("Hoofd");
        TESTDATA.add("Hart");
        TESTDATA.add("Longen");
        TESTDATA.add("Oren");
        TESTDATA.add("Ogen");

        return TESTDATA;
    }
}
