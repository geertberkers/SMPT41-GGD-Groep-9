package smpt.proftaak.ggd;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Geert on 18-6-2015
 */
public class RampActivity extends BaseActivity {

    private Ramp ramp;

    private View buttonBackground;
    private Button btnTijdlijn;
    private Button btnInformatie;
    private Fragment tijdlijnFragment;
    private Fragment informatieFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ramp_activity);

        btnTijdlijn = (Button) findViewById(R.id.btnTijdlijn);
        btnInformatie = (Button) findViewById(R.id.btnInformatie);
        buttonBackground = findViewById(R.id.buttonBackground);

        buttonBackground.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blue_button_background_timeline));

        Bundle b = getIntent().getExtras();
        ramp = b.getParcelable("ramp");

        getSupportActionBar().setTitle(ramp.getTitelRamp());

        Intent intent = new Intent(this, RampActivity.class);
        intent.putExtra("ramp", ramp);

        Bundle bundle = new Bundle();
        bundle.putParcelable("ramp", ramp);


        tijdlijnFragment = new TijdlijnFragment();

        informatieFragment = new InformatieFragment();

        fragmentManager = getFragmentManager();

        informatieFragment.setArguments(bundle);
        tijdlijnFragment.setArguments(bundle);

        //AANZETTEN OM TIJDLIJN FRAGMENT TE TESTEN
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frameLayout, tijdlijnFragment);
            fragmentTransaction.commit();
        }
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
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnTijdlijn:
                buttonBackground.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blue_button_background_timeline));
                btnTijdlijn.setTextColor(getResources().getColor(R.color.zwart));
                btnInformatie.setTextColor(getResources().getColor(R.color.lichteGrijs));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, tijdlijnFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.btnInformatie:
                buttonBackground.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.blue_button_background_information));
                btnInformatie.setTextColor(getResources().getColor(R.color.zwart));
                btnTijdlijn.setTextColor(getResources().getColor(R.color.lichteGrijs));
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, informatieFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
}