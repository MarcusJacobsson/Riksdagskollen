package se.ottomatech.marcusjacobsson.sverigesriksdag.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.CalendarPojo;
import se.ottomatech.marcusjacobsson.sverigesriksdag.util.RiksdagskollenTimeUtil;

/**
 * Created by Marcus Jacobsson on 2015-03-22.
 */
public class CalendarEventListAdapter extends ArrayAdapter<CalendarPojo> {

    private ArrayList<CalendarPojo> events;

    public CalendarEventListAdapter(Context c, ArrayList<CalendarPojo> events) {
        super(c, R.layout.calendar_event_list_item, events);
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.calendar_event_list_item, null);
        } else {
            row = convertView;
        }

        TextView tvSummary = (TextView) row.findViewById(R.id.calendar_event_list_summary);
        TextView tvTime = (TextView) row.findViewById(R.id.calendar_event_list_time);
        tvSummary.setText(events.get(position).getSummary());
        tvTime.setText(RiksdagskollenTimeUtil.getTimeFromDateString(events.get(position).getDtStart()) + "-" + RiksdagskollenTimeUtil.getTimeFromDateString(events.get(position).getDtEnd()));

        return row;
    }
}
