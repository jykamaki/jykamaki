package fi.jykamaki.mvp_demo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by YR on 14.11.2015.
 */
public class VenueDataAdapter extends ArrayAdapter<VenueData> {
    Context context;
    int layoutResourceId;
    List<VenueData> data = null;

    public VenueDataAdapter(Context context, int layoutResourceId, List<VenueData> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    public void setData(List<VenueData> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        VenueHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new VenueHolder();
            holder.venueName = (TextView)row.findViewById(R.id.textVenueName);
            holder.venueAddress = (TextView)row.findViewById(R.id.textVenueAddress);
            holder.venueDistance = (TextView)row.findViewById(R.id.textVenueDistance);

            row.setTag(holder);
        }
        else
        {
            holder = (VenueHolder)row.getTag();
        }

        VenueData venue = data.get(position);
        holder.venueName.setText(venue.getVenueName());
        holder.venueAddress.setText(venue.getVenueAddress());
        holder.venueDistance.setText(venue.getVenueDistance());
        return row;
    }

    static class VenueHolder
    {
        TextView venueName;
        TextView venueAddress;
        TextView venueDistance;
    }
}
