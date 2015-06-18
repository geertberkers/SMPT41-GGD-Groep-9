package smtp.proftaak.ggd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Geert on 18-6-2015
 */
public class RampActivity extends ActionBarActivity {

    private Ramp ramp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ramp_activity);

        // Get the message from the intent
        Bundle b = getIntent().getExtras();
        ramp = b.getParcelable("ramp");

        System.out.println(ramp.getTitelRamp());
        System.out.println(ramp.getLaatsteUpdate());
        System.out.println(ramp.getOmschrijving());
        // Create the text view
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
            Toast.makeText(this.getApplicationContext(), "Instellingen aangeraakt", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}