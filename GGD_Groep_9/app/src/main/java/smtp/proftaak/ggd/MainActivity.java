package smtp.proftaak.ggd;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ListView rampenListView;
    private RampAdapter rampAdapter;
    private ArrayList<Ramp> rampenLijst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.oceaanblauw)));

        rampenLijst = new ArrayList<>();
        rampenLijst.add(new Ramp("Brand Helmond", "Laatste update: 15:00", "Kleine brand met veel rook"));
        rampenLijst.add(new Ramp("Brand Eindhoven", "Laatste update: 14:00", "Grote brand en veel overlast"));
        rampenLijst.add(new Ramp("Brand Veldhoven", "Laatste update: 12:30", "Kleine brand, brandweer ter plekke"));

        rampenListView = (ListView) findViewById(R.id.rampenListview);
        rampAdapter = new RampAdapter(this.getApplicationContext(), rampenLijst);

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

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        if (id == R.id.action_settings) {
            Toast.makeText(context, "Instellingen aangeraakt", duration).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
