package smpt.proftaak.ggd;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Geert on 25-6-2015
 */
public class InformatieFragment extends Fragment {

    private TextView txtInfo;
    private TextView txtTitel;
    private TextView txtLaatsteUpdate;
    private ImageView imgWarning;

    private Ramp ramp;
    private Informatie info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.informatie_fragment, container, false);
    }

    public void onStart() {
        super.onStart();
        Bundle bundle = this.getArguments();
        this.ramp = bundle.getParcelable("ramp");
        initControls();
    }

    public void initControls() {
        txtTitel = (TextView) getView().findViewById(R.id.txtTitel);
        txtInfo = (TextView) getView().findViewById(R.id.txtInformatie);
        imgWarning = (ImageView) getView().findViewById(R.id.warningImage);
        txtLaatsteUpdate = (TextView) getView().findViewById(R.id.txtLaatsteUpdate);

        txtTitel.setTypeface(null, Typeface.BOLD_ITALIC);
        txtTitel.setTextColor(getResources().getColor(R.color.ggdBlauw));
        txtTitel.setPaintFlags(txtTitel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        getInformation();
    }

    private void getInformation() {

        if(info != null)
        {
            setInformation();
        }

        SharedPreferences prefs = getActivity().getSharedPreferences("smpt.proftaak.ggd", Context.MODE_PRIVATE);
        String postcode = prefs.getString(getString(R.string.sharedpref_postcode), "5616NH");
        System.out.println("Postcode: " + postcode);

        String path = "http://stanjan.nl/smpt/API/info.php?id=" + ramp.getID() + "&postcode=" + postcode;
        APICallTask apiTest = new APICallTask(this, APICallType.GET_INFORMATIE, path);
        apiTest.execute();
    }

    private void setInformation() {
        txtTitel.setText(info.getInfoTitel());
        txtInfo.setText(info.getBeschrijving());
        imgWarning.setImageBitmap(info.getWarningImg());
        txtLaatsteUpdate.setText(info.getLaatsteUpdate());
    }

    public void setData(String data) {
        JSONParser parser = new JSONParser(data);
        info = parser.getInformatie();

        if(info != null) {
            String url = "http://stanjan.nl/smpt/images/info/" + info.getAfbeeldingPath();
            new DownloadImage(this).execute(url);
        }
        else
        {
            txtTitel.setText("Nog geen informatie beschikbaar");
        }
    }

    public void setImage(Bitmap bitmap)
    {
        info.setWarningImg(bitmap);
        setInformation();
    }
}