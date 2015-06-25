package smpt.proftaak.ggd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;


import java.util.ArrayList;

public class TijdlijnFragment extends ListFragment {

    private ListView list;
    private final Context context;
    private final ArrayList<TijdlijnItem> items;

    public TijdlijnFragment(){context = null; items = new ArrayList<TijdlijnItem>();};

    @SuppressLint("ValidFragment")
    // ^ Hier vooral niet op letten
    public TijdlijnFragment(Context c, ArrayList<TijdlijnItem> items)
    {
        this.context = c;
        this.items = items;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TijdlijnAdapter adapter = new TijdlijnAdapter(context, items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "Item clicked: " + id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tijdlijn_fragment, container, false);
        LinearLayout linear = (LinearLayout)rootView.findViewById(R.id.tijdlijnFragment);
        list = (ListView) rootView.findViewById(android.R.id.list);

        return rootView;
    }
}
