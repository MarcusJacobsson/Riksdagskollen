package se.ottomatech.marcusjacobsson.sverigesriksdag.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.CalendarPojo;
import se.ottomatech.marcusjacobsson.sverigesriksdag.util.RiksdagskollenTimeUtil;

/**
 * Created by Marcus Jacobsson on 2015-03-24.
 */
public class CalendarEventDetails extends Activity {

    private EditText etDesc;
    private TextView tvDtStart, tvDtEnd, tvCreated, tvLastModified, tvLocation, tvComment, tvSummary, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setUpComponents();
        Bundle extras = getIntent().getExtras();
        CalendarPojo calendarPojo = extras.getParcelable("calendarPojo");
        setUpEvent(calendarPojo);
    }

    private void setUpComponents(){
        etDesc = (EditText)findViewById(R.id.et_event_details_desc);
        tvDtStart = (TextView)findViewById(R.id.tv_event_details_dt_start);
        tvDtEnd = (TextView)findViewById(R.id.tv_event_details_dt_end);
        tvCreated = (TextView)findViewById(R.id.tv_event_details_created);
        tvLastModified = (TextView)findViewById(R.id.tv_event_details_last_modified);
        tvLocation = (TextView)findViewById(R.id.tv_event_details_location);
        tvComment = (TextView)findViewById(R.id.tv_event_details_comment);
        tvSummary = (TextView)findViewById(R.id.tv_event_details_summary);
        tvDate = (TextView)findViewById(R.id.tv_event_details_date);

    }

    private void setUpEvent(CalendarPojo calendarPojo){
        etDesc.setText(calendarPojo.getDescription().replace("\\n", "\n"));
        tvDtStart.setText(RiksdagskollenTimeUtil.getTimeFromDateString(calendarPojo.getDtStart()));
        tvDtEnd.setText(RiksdagskollenTimeUtil.getTimeFromDateString(calendarPojo.getDtEnd()));
        tvCreated.setText(RiksdagskollenTimeUtil.formatDateString(calendarPojo.getCreated()));
        tvLastModified.setText(RiksdagskollenTimeUtil.formatDateString(calendarPojo.getLastModified()));
        tvLocation.setText(calendarPojo.getLocation());
        tvComment.setText(calendarPojo.getComment());
        tvSummary.setText(calendarPojo.getSummary());
        tvDate.setText(RiksdagskollenTimeUtil.getDateFromDateString(calendarPojo.getDtStart()));
    }
}
