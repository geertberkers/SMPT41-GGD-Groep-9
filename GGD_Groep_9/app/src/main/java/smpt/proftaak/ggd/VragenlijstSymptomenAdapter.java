package smpt.proftaak.ggd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Joep on 26-6-2015.
 */
public class VragenlijstSymptomenAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final ArrayList<String> entries;

    public VragenlijstSymptomenAdapter(Context context, ArrayList<String> entries)
    {
        super(context, R.layout.vragenlijstsymptoomrow, entries);
        this.context = context;
        this.entries = entries;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent)
    {
        String currentEntry = entries.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.vragenlijstsymptoomrow, parent, false);

        TextView symptoomTitle = (TextView)rowView.findViewById(R.id.symptoomTitle);

        symptoomTitle.setText(currentEntry);

        return rowView;
    }
    }
