package smpt.proftaak.ggd;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

/**
 * Created by Geert on 30-6-2015
 */
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private Fragment parent;

    public DownloadImage(Fragment parent)
    {
        this.parent = parent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {

        String imageURL = URL[0];

        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        InformatieFragment i = (InformatieFragment)parent;
        i.setImage(result);
    }
}