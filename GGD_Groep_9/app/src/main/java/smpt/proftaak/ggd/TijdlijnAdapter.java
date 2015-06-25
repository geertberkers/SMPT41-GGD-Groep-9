package smpt.proftaak.ggd;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Joep on 25-6-2015.
 */
public class TijdlijnAdapter extends ArrayAdapter<TijdlijnItem>
{
    private final Context context;
    private final ArrayList<TijdlijnItem> items;

    public TijdlijnAdapter(Context context, ArrayList<TijdlijnItem> items)
    {
        super(context, R.layout.tijdlijnrow, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.tijdlijnrow, parent, false);

        TextView titleView = (TextView)rowView.findViewById(R.id.tijdlijnItemTitle);
        TextView timestampView = (TextView)rowView.findViewById(R.id.tijdlijnItemTimestamp);
        TextView descriptionView = (TextView)rowView.findViewById(R.id.tijdlijnItemDetailsDescription);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.tijdlijnItemDetailsImage);

        //Get current item from item list
        final TijdlijnItem currentItem = items.get(position);

        //prepare resource identifier variable for sideline image, and root RelativeLayout for background image set
        int tijdlijnSidelineImage;
        RelativeLayout root = (RelativeLayout)rowView.findViewById(R.id.tijdlijnItemRoot);

        //if this is the first item in the timeline
        if (position == 0)
        {
            tijdlijnSidelineImage = context.getResources().getIdentifier("tijdlijnbegin", "mipmap", context.getPackageName());
        }
        //or the last item in the timeline
        else if (position == items.size()-1)
        {
            tijdlijnSidelineImage = context.getResources().getIdentifier("tijdlijneind", "mipmap", context.getPackageName());
        }
        //else default is tijdlijnmidden
        else
        {
            tijdlijnSidelineImage = context.getResources().getIdentifier("tijdlijnmidden", "mipmap", context.getPackageName());
        }

        //set tijdlijn item background image
        root.setBackgroundResource(tijdlijnSidelineImage);

        //set details text
        titleView.setText(currentItem.getTitle());
        timestampView.setText(currentItem.getTimestamp());
        descriptionView.setText(currentItem.getDescription());

        //set tijdlijnt item image, or hide imageview if image is not available
        int imageResourceIdentifier = context.getResources().getIdentifier(currentItem.getImgSrc(), "mipmap", context.getPackageName());
        if (imageResourceIdentifier == 0)
        {
            imageView.setVisibility(View.GONE);
        }
        else
        {
            imageView.setImageResource(imageResourceIdentifier);
        }

        switch(currentItem.getAnimationPermission())
        {
            case ANIMATE:
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.newtimelineitem);
                rowView.startAnimation(animation);
                currentItem.revokeAnimationPermission();
                break;
            case NO_ANIMATION:
                break;
        }

        //return prepared row
        return rowView;
    }
}
