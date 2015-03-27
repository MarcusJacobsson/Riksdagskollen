package se.ottomatech.marcusjacobsson.sverigesriksdag.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.adapters.MemberAssignmentsPojoAdapter;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberAssignmentPojo;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-26.
 */
public class MemberDetailsActivity extends Activity implements View.OnClickListener {

    private ImageView ivImage, ivTwitter, ivFacebook, ivLinkedIn, ivMail, ivWebsite;
    private TextView tvName, tvBorn, tvSex, tvParty, tvStatus, tvDistrict;
    private ListView lwAssignments;
    private MemberPojo member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        member = getIntent().getExtras().getParcelable("member");
        setUpComponents();
        displayMemberDetails(member);
    }

    private void setUpComponents() {
        ivImage = (ImageView) findViewById(R.id.iv_activity_member_details_image);
        ivTwitter = (ImageView) findViewById(R.id.iv_activity_member_details_twitter);
        ivFacebook = (ImageView) findViewById(R.id.iv_activity_member_details_facebook);
        ivLinkedIn = (ImageView) findViewById(R.id.iv_activity_member_details_linkedin);
        ivMail = (ImageView) findViewById(R.id.iv_activity_member_details_mail);
        ivWebsite = (ImageView) findViewById(R.id.iv_activity_member_details_website);
        tvName = (TextView) findViewById(R.id.tv_activity_member_details_name);
        tvBorn = (TextView) findViewById(R.id.tv_activity_member_details_born);
        tvSex = (TextView) findViewById(R.id.tv_activity_member_details_sex);
        tvParty = (TextView) findViewById(R.id.tv_activity_member_details_party);
        tvStatus = (TextView) findViewById(R.id.tv_activity_member_details_status);
        tvDistrict = (TextView) findViewById(R.id.tv_activity_member_details_district);
        lwAssignments = (ListView) findViewById(R.id.lw_activity_member_details_assignments);

        ivTwitter.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivLinkedIn.setOnClickListener(this);
        ivMail.setOnClickListener(this);
        ivWebsite.setOnClickListener(this);

        if (member.getWebsiteUrl() == null) {
            ivWebsite.setOnClickListener(null);
            ivWebsite.setImageResource(R.mipmap.no_website_icon);
        }

        if (member.getEmailAddress() == null) {
            ivMail.setOnClickListener(null);
            ivMail.setImageResource(R.mipmap.no_mail_icon);
        }
    }

    private void displayMemberDetails(MemberPojo member) {

        Picasso.with(this)
                .load(member.getImageUrl())
                .fit()
                .placeholder(R.drawable.transp_placeholder)
                .error(R.drawable.error)
                .into(ivImage);

        tvName.setText(member.getFirstName() + " " + member.getLastName());
        tvBorn.setText(member.getYearBorn());
        tvSex.setText(member.getSex());
        tvParty.setText(member.getParty());
        tvStatus.setText(member.getStatus());
        tvDistrict.setText(member.getDistrict());

        ArrayList<MemberAssignmentPojo> assignmentPojos = member.getAssignmentPojos();

        Collections.sort(assignmentPojos, new Comparator<MemberAssignmentPojo>() {
            public int compare(MemberAssignmentPojo s1, MemberAssignmentPojo s2) {
                return s1.getDtStart().compareToIgnoreCase(s2.getDtStart());
            }
        });

        MemberAssignmentsPojoAdapter adapter = new MemberAssignmentsPojoAdapter(this, assignmentPojos);
        lwAssignments.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Animation animFadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        String url;
        Intent browserIntent;
        switch (v.getId()) {
            case R.id.iv_activity_member_details_twitter:
                ivTwitter.startAnimation(animFadein);
                url = "https://twitter.com/search?q=" + member.getFirstName() + "%20" + member.getLastName() + "&src=typd&mode=users";
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                break;

            case R.id.iv_activity_member_details_facebook:
                url = "https://www.facebook.com/search.php?o=2048&init=dir&q=" + member.getFirstName() + "+" + member.getLastName();
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                ivFacebook.startAnimation(animFadein);
                break;

            case R.id.iv_activity_member_details_linkedin:
                url = "https://www.linkedin.com/pub/dir/?first=" + member.getFirstName() + "&last=" + member.getLastName() + "&search=Search&searchType=fps";
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                ivLinkedIn.startAnimation(animFadein);
                break;

            case R.id.iv_activity_member_details_mail:
                ivMail.startAnimation(animFadein);
                String email = member.getEmailAddress().replace("[på]", "@");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                try {
                    startActivity(Intent.createChooser(i, getResources().getString(R.string.activity_member_details_send_email)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iv_activity_member_details_website:
                ivWebsite.startAnimation(animFadein);
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(member.getWebsiteUrl()));
                startActivity(browserIntent);
                break;
        }
    }
}
