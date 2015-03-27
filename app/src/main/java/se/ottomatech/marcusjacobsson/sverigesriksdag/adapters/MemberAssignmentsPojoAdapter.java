package se.ottomatech.marcusjacobsson.sverigesriksdag.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberAssignmentPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-26.
 */
public class MemberAssignmentsPojoAdapter extends ArrayAdapter<MemberAssignmentPojo> {

    private ViewHolder holder;
    private ArrayList<MemberAssignmentPojo> assignmentPojos;

    public MemberAssignmentsPojoAdapter(Context c, ArrayList<MemberAssignmentPojo> assignmentPojos){
        super(c, R.layout.member_assignment_list_item, assignmentPojos);
        this.assignmentPojos = assignmentPojos;
    }

    private class ViewHolder{
        private TextView role;
        private TextView dtStart;
        private TextView dtEnd;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        if (convertView == null) { // Create new row view object
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.member_assignment_list_item, null);
            holder = new ViewHolder();
            holder.role = (TextView) row.findViewById(R.id.tv_member_assignment_list_item_role);
            holder.dtEnd = (TextView) row.findViewById(R.id.tv_member_assignment_list_item_dt_end);
            holder.dtStart = (TextView) row.findViewById(R.id.tv_member_assignment_list_item_dt_start);
            row.setTag(holder);
        } else {
            row = convertView;
            holder = (ViewHolder) row.getTag();
        }
        MemberAssignmentPojo assignmentPojo = assignmentPojos.get(position);

        holder.role.setText(assignmentPojo.getRole());
        holder.dtEnd.setText(assignmentPojo.getDtEnd());
        holder.dtStart.setText(assignmentPojo.getDtStart());

        return row;
    }
}
