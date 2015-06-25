package smtp.proftaak.ggd;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by BartKneepkens on 25/06/15.
 */
public class settingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.oceaanblauw)));
    }
}
