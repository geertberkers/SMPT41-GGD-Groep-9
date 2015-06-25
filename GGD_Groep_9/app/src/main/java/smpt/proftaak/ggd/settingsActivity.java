package smpt.proftaak.ggd;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by BartKneepkens on 25/06/15.
 */
public class settingsActivity extends ActionBarActivity {
    boolean postcodeHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.oceaanblauw)));

        Switch gpsSwitch = (Switch) this.findViewById(R.id.gpsSwitch);
        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    postcodeHidden = true;
                } else {
                    postcodeHidden = false;
                }
                locatieInputChanged();
            }
        });

        locatieInputChanged();


    }

    private void locatieInputChanged()
    {
        TextView postcode = (TextView) this.findViewById(R.id.tvPostcode);
        View cv = this.findViewById(R.id.cvPostcode);
        EditText ed = (EditText) this.findViewById(R.id.etPostcode);
        TextView noGps = (TextView) this.findViewById(R.id.tvNoGps);

        if(postcodeHidden)
        {
            postcode.setVisibility(View.GONE);
            cv.setVisibility(View.GONE);
            ed.setVisibility(View.GONE);
            noGps.setVisibility(View.VISIBLE);
        }
        else
        {
            postcode.setVisibility(View.VISIBLE);
            cv.setVisibility(View.VISIBLE);
            ed.setVisibility(View.VISIBLE);
            noGps.setVisibility(View.GONE);
        }
    }
}
