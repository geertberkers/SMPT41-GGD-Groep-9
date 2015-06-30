package smpt.proftaak.ggd;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.callback.Callback;

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

        //AANZETTEN OM TIJDLIJN FRAGMENT TE TESTEN
        if (getFragmentManager().findFragmentById(android.R.id.content) == null) {

            String lipsum = "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken. Het heeft niet alleen vijf eeuwen overleefd maar is ook, vrijwel onveranderd, overgenomen in elektronische letterzetting. Het is in de jaren \'60 populair geworden met de introductie van Letraset vellen met Lorem Ipsum passages en meer recentelijk door desktop publishing software zoals Aldus PageMaker die versies van Lorem Ipsum bevatten.";

            ArrayList<TijdlijnItem> testNews = new ArrayList<>();
            testNews.add(new TijdlijnItem("title", "src", "11:23", lipsum));
            testNews.add(new TijdlijnItem("title2", "chemiepack", "14:23", lipsum));
            testNews.add(new TijdlijnItem("title3", "src2", "00:23", lipsum));
            testNews.add(new TijdlijnItem("title4", "src2", "11:12", lipsum));
            testNews.add(new TijdlijnItem("title5", "src2", "12:34", lipsum));
            testNews.add(new TijdlijnItem("title6", "chemiepack", "23:58", lipsum));

            bundle.putParcelableArrayList("tijdlijnItems", testNews);
            tijdlijnFragment.setArguments(bundle);

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
                Intent intent = new Intent(this, settingsActivity.class);
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