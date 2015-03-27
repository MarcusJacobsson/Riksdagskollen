package se.ottomatech.marcusjacobsson.sverigesriksdag.adapters;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class MemberPojoArrayAdapter extends ArrayAdapter<MemberPojo> {

    private ArrayList<MemberPojo> members;
    private ViewHolder holder;

    public MemberPojoArrayAdapter(Context c, ArrayList<MemberPojo> members) {
        super(c, R.layout.member_list_item, members);
        this.members = members;
    }

    private class ViewHolder{
        private ImageView image;
        private TextView name;
        private TextView party;
        private TextView district;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        if (convertView == null) { // Create new row view object
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.member_list_item, null);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.iv_member_list_item_image);
            holder.name = (TextView) row.findViewById(R.id.tv_member_list_item_name);
            holder.party = (TextView) row.findViewById(R.id.tv_member_list_item_party);
            holder.district = (TextView) row.findViewById(R.id.tv_member_list_item_district);
            row.setTag(holder);
        } else {
            row = convertView;
            holder = (ViewHolder) row.getTag();
        }

        MemberPojo member = members.get(position);

        holder.name.setText(member.getFirstName() + " " + member.getLastName());
        holder.district.setText(member.getDistrict());
        holder.party.setText(member.getParty());

        Picasso.with(getContext())
                .load(member.getImageUrl())
                .centerCrop()
                .fit()
                .placeholder(R.drawable.transp_placeholder)
                .error(R.drawable.error)
                .into(holder.image);

        return row;
    }
}
