package smpt.proftaak.ggd;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
        txtTitel = (TextView) getView().findViewById(R.id.txtTitel);
        txtInfo = (TextView) getView().findViewById(R.id.txtInformatie);
        imgWarning = (ImageView) getView().findViewById(R.id.warningImage);
        txtLaatsteUpdate = (TextView) getView().findViewById(R.id.txtLaatsteUpdate);

        // Zorgen dat deze uit de database word opgehaald via:
        // "http://stanjan.nl/smpt/API/info.php?id=" + ramp.getId() + "&postcode=" + postcode
        // Postcode nog ophalen van user!!
        info = new Informatie("Titel","beschrijving","sluitramenendeuren.jpg","2015-06-26 11:04:09");

        txtTitel.setText(info.getInfoTitel());
        txtInfo.setText(info.getBeschrijving());
        txtLaatsteUpdate.setText(info.getLaatsteUpdate());

        // Zorgen dat dit een aparte thread word om image op te halen!
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            String url = "http://stanjan.nl/smpt/images/info/" + info.getAfbeeldingPath();
            System.out.println(url);
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            imgWarning.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}