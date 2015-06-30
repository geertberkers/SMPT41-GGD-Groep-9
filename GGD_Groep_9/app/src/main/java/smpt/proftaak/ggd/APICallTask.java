package smpt.proftaak.ggd;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Joep on 30-6-2015.
 */
public class APICallTask extends AsyncTask<String, Void, String>
{

    private Fragment parent;
    private APICallType callType;
    private String apiUrl;

    public APICallTask(Fragment parent, APICallType callType, String apiUrl)
    {
        this.parent = parent;
        this.callType = callType;
        this.apiUrl = apiUrl;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        try
        {
            url = new URL(apiUrl);
        }
        catch (MalformedURLException ex)
        {
            throw new RuntimeException("URL " + apiUrl + " is malformed");
        }

        String result = "";

        URLConnection con;
        try
        {
            con = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
            {
                result += inputLine;
            }
            in.close();
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Connection failed");
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        switch(callType) {
            case GET_TIJDLIJN:
                TijdlijnFragment t = (TijdlijnFragment)parent;
                t.setData(result);
                break;
            case GET_VRAGENLIJST:
                break;
            case GET_INFORMATIE:
                InformatieFragment i = (InformatieFragment)parent;
                i.setData(result);
                break;

        }

    }
}
