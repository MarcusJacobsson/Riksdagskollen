package se.ottomatech.marcusjacobsson.sverigesriksdag.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.activities.CalendarCommitteeMenuListActivity;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.CalendarPojo;
import se.ottomatech.marcusjacobsson.sverigesriksdag.xmlparsers.CalendarXmlParser;

/**
 * Created by Marcus Jacobsson on 2015-03-20.
 */
public class CalendarFragmentMain extends Fragment implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private DownloadCalendarDataTask downloadCalendarDataTask;
    private CalendarFragmentCallback mListener;

    public interface CalendarFragmentCallback {
        public void onDownloadReady(ArrayList<CalendarPojo> list);
    }

    public void setListener(CalendarFragmentCallback listener) {
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_main, container, false);
        TextView tvChamber = (TextView) view.findViewById(R.id.tv_calendar_fragment_chamber);
        TextView tvCommittee = (TextView) view.findViewById(R.id.tv_calendar_fragment_committee);
        TextView tvOther = (TextView) view.findViewById(R.id.tv_calendar_fragment_other);
        tvChamber.setOnClickListener(this);
        tvCommittee.setOnClickListener(this);
        tvOther.setOnClickListener(this);

        //setUpDialog();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDialog();
        Bundle args = getArguments();
        if (args != null) {
            String choice = getArguments().getString("choice");
            if (choice != null) {
                downloadCalendarDataTask = new DownloadCalendarDataTask();
                switch (choice) {
                    case "Alla sammanträden":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_all));
                        break;

                    case "Presskonferens":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_press_conference));
                        break;

                    case "Öppen presskonferens":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_open_press_conference));
                        break;

                    case "Öppen ufrågning":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_open_questioning));
                        break;

                    case "Öppet sammanträde":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_open_meeting));
                        break;

                    case "Öppet samråd":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_open_consultation));
                        break;

                    case "Öppet seminarium":
                        downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_committee_open_seminar));
                        break;
                }
            }
        }
    }

    private void setUpDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.progress_bar_message));
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (downloadCalendarDataTask != null)
                    downloadCalendarDataTask.cancel(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //TODO: Check internet connection
        downloadCalendarDataTask = new DownloadCalendarDataTask();
        switch (v.getId()) {

            case R.id.tv_calendar_fragment_chamber:
                downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_chamber));
                break;

            case R.id.tv_calendar_fragment_committee:
                Intent i = new Intent(getActivity(), CalendarCommitteeMenuListActivity.class);
                startActivityForResult(i, 1);
                break;

            case R.id.tv_calendar_fragment_other:
                downloadCalendarDataTask.execute(getResources().getString(R.string.url_calendar_other));
                break;
        }
    }

    private class DownloadCalendarDataTask extends AsyncTask<String, Integer, ArrayList<CalendarPojo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected ArrayList<CalendarPojo> doInBackground(String... urls) {
            try {
                InputStream stream = null;
                // Instantiate the parser
                CalendarXmlParser parser = new CalendarXmlParser();
                ArrayList<CalendarPojo> entries = null;

                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    stream = conn.getInputStream();
                    entries = parser.parse(stream);

                    // Makes sure that the InputStream is closed after the app is
                    // finished using it.
                } finally {
                    if (stream != null) {
                        stream.close();
                    }
                }
                return entries;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(ArrayList<CalendarPojo> list) {
            if (list != null) {
                super.onPostExecute(list);
                //showCalendar(list);
                mListener.onDownloadReady(list);
            } else {
                //TODO: error
            }
            progressDialog.dismiss();
        }
    }
}
