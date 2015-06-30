package smpt.proftaak.ggd;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private ArrayList<Ramp> rampenLijst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dit is testdata
        // Goede data ophalen uit de database
        rampenLijst = new ArrayList<>();
        rampenLijst.add(new Ramp(1,"Brand Helmond", "Laatste update: 15:00", "Kleine brand met veel rook"));
        rampenLijst.add(new Ramp(2,"Brand Eindhoven", "Laatste update: 14:00", "Grote brand en veel overlast"));
        rampenLijst.add(new Ramp(3,"Brand Veldhoven", "Laatste update: 12:30", "Kleine brand, brandweer ter plekke"));

        ListView rampenListView = (ListView) findViewById(R.id.rampenListview);
        RampAdapter rampAdapter = new RampAdapter(this.getApplicationContext(), rampenLijst);

        rampenListView.setAdapter(rampAdapter);

        rampenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RampActivity.class);
                intent.putExtra("ramp", rampenLijst.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, settingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
