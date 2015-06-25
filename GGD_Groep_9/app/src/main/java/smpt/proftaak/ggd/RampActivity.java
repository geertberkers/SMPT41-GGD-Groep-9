package smpt.proftaak.ggd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

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

        //AANZETTEN OM TIJDLIJN FRAGMENT TE TESTEN
//        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
//            ArrayList<TijdlijnItem> TESTITEMS = new ArrayList<TijdlijnItem>();
//            String lipsum = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken. Het heeft niet alleen vijf eeuwen overleefd maar is ook, vrijwel onveranderd, overgenomen in elektronische letterzetting. Het is in de jaren \'60 populair geworden met de introductie van Letraset vellen met Lorem Ipsum passages en meer recentelijk door desktop publishing software zoals Aldus PageMaker die versies van Lorem Ipsum bevatten.";
//            TESTITEMS.add(new TijdlijnItem("title", "src", "11:23", lipsum));
//            TESTITEMS.add(new TijdlijnItem("title2", "chemiepack", "14:23", lipsum));
//            TESTITEMS.add(new TijdlijnItem("title3", "src2", "00:23", lipsum));
//            TESTITEMS.add(new TijdlijnItem("title4", "src2", "11:12", lipsum));
//            TESTITEMS.add(new TijdlijnItem("title5", "src2", "12:34", lipsum));
//            TESTITEMS.add(new TijdlijnItem("title6", "chemiepack", "23:58", lipsum));
//
//            TijdlijnFragment list = new TijdlijnFragment(this, TESTITEMS);
//            getFragmentManager().beginTransaction().add(android.R.id.content, list).commit();
//        }
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