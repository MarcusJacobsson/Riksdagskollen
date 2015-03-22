package se.ottomatech.marcusjacobsson.sverigesriksdag.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;

/**
 * Created by Marcus Jacobsson on 2015-03-21.
 */
public class CalendarCommitteeMenuListActivity extends ListActivity implements ListView.OnItemClickListener {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String[] values = getResources().getStringArray(R.array.calendar_list_activity_content);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        setResult(RESULT_CANCELED);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent();
        i.putExtra("choice", getListView().getItemAtPosition(position).toString());
        setResult(RESULT_OK, i);
        finish();
    }
}
