package smtp.proftaak.ggd;

import android.os.Bundle;

/**
 * Created by Geert on 19-6-2015
 */
public class InfoActivity extends BaseActivity{

    private Ramp ramp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        Bundle b = getIntent().getExtras();
        ramp = b.getParcelable("ramp");

        getSupportActionBar().setTitle(ramp.getTitelRamp());
    }
}
