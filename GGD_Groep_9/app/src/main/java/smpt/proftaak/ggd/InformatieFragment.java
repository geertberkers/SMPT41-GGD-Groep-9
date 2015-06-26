package smpt.proftaak.ggd;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Geert on 25-6-2015
 */
public class InformatieFragment extends Fragment {

    private TextView txtInfo;
    private Ramp ramp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.informatie_fragment, container, false);
    }

    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        this.ramp = bundle.getParcelable("ramp");
        initControls();
    }

    public void initControls(){
        txtInfo = (TextView) getView().findViewById(R.id.txtInformatie);
        txtInfo.setText("INFORMATIE FRAGMENT");

        System.out.println(this.ramp.getTitelRamp());
        System.out.println(this.ramp.getLaatsteUpdate());
        System.out.println(this.ramp.getOmschrijving());
    }
}