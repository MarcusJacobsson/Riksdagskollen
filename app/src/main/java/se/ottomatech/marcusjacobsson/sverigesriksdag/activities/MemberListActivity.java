package se.ottomatech.marcusjacobsson.sverigesriksdag.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.adapters.MemberPojoArrayAdapter;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class MemberListActivity extends ListActivity implements ListView.OnItemClickListener {

    private ArrayList<MemberPojo> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.members = getIntent().getExtras().getParcelableArrayList("members");
        getListView().setOnItemClickListener(this);
        MemberPojoArrayAdapter adapter = new MemberPojoArrayAdapter(this, members);
        getListView().setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, MemberDetailsActivity.class);
        i.putExtra("member", members.get(position));
        startActivity(i);
    }
}
