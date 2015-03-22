package se.ottomatech.marcusjacobsson.sverigesriksdag.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.adapters.CalendarEventListAdapter;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.CalendarPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-22.
 */
public class CalendarEventListActivity extends ListActivity implements ListView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        ArrayList<CalendarPojo> events = extras.getParcelableArrayList("events");
        CalendarEventListAdapter adapter = new CalendarEventListAdapter(this, events);
        getListView().setAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
