package smpt.proftaak.ggd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Joep on 30-6-2015.
 */
public class VragenlijstOpenVragenAdapter extends ArrayAdapter<Vraag>
{
    private final Context context;
    private final ArrayList<Vraag> entries;

    public VragenlijstOpenVragenAdapter(Context context, ArrayList<Vraag> entries)
    {
        super(context, R.layout.openvraagrow, entries);
        this.context = context;
        this.entries = entries;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent)
    {
        Vraag currentEntry = entries.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.openvraagrow, parent, false);

        TextView rowTitle = (TextView)rowView.findViewById(R.id.openvraagTitle);
        EditText rowInput = (EditText)rowView.findViewById(R.id.openvraagInput);

        rowTitle.setText(currentEntry.getVraag());

        return rowView;
    }
}
