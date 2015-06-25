package smpt.proftaak.ggd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Geert on 18-6-2015
 */
public class RampActivity extends BaseActivity {

    private Ramp ramp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ramp_activity);

        Bundle b = getIntent().getExtras();
        ramp = b.getParcelable("ramp");

        getSupportActionBar().setTitle(ramp.getTitelRamp());

        // create the TabHost that will contain the Tabs
        //TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        Intent intent = new Intent(this, RampActivity.class);
        intent.putExtra("ramp", ramp);
/*
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tijdlijn Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Info Tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Tijdlijn");
        tab1.setContent(intent);

        tab2.setIndicator("Info");
        tab2.setContent(intent);


        /** Add the tabs  to the TabHost to display. */
       // tabHost.addTab(tab1);
       // tabHost.addTab(tab2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_settings:
                Toast.makeText(this.getApplicationContext(), "Instellingen aangeraakt", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}