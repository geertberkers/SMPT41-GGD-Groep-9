package smpt.proftaak.ggd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.ListFragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class TijdlijnFragment extends ListFragment {

    private ListView list;
    private final TijdlijnAdapter adapter;
    private final Context context;
    private final ArrayList<TijdlijnItem> items;

    public TijdlijnFragment(){context = null; items = new ArrayList<TijdlijnItem>(); adapter = null;};

    @SuppressLint("ValidFragment")
    // ^ Hier vooral niet op letten
    public TijdlijnFragment(Context c, ArrayList<TijdlijnItem> items)
    {
        this.context = c;
        this.items = items;

        for (TijdlijnItem i: items)
        {
            i.revokeAnimationPermission();
        }

        adapter = new TijdlijnAdapter(context, items);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(adapter);

        setUpNewsUpdate();
    }

    private void setUpNewsUpdate()
    {
        Timer timer = new Timer();
        TimerTask addNewItemTask;
        final Handler handler = new Handler();

        addNewItemTask = new TimerTask()
        {
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        items.add(0, new TijdlijnItem("Gebouw B ontruimd", "brandweer", "23:36", "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken. Het heeft niet alleen vijf eeuwen overleefd maar is ook, vrijwel onveranderd, overgenomen in elektronische letterzetting. Het is in de jaren '60 populair geworden met de introductie van Letraset vellen met Lorem Ipsum passages en meer recentelijk door desktop publishing software zoals Aldus PageMaker die versies van Lorem Ipsum bevatten."));

                        list.post(new Runnable() {
                            @Override
                            public void run() {
                                list.smoothScrollToPositionFromTop(0, 0, 1000);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        };

        timer.schedule(addNewItemTask, 1500, 4000);
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
