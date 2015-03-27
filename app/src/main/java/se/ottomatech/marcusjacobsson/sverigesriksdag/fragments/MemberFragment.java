package se.ottomatech.marcusjacobsson.sverigesriksdag.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.activities.MemberListActivity;
import se.ottomatech.marcusjacobsson.sverigesriksdag.parsers.JSONParser;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.MemberPojo;

/**
 * Created by Marcus Jacobsson on 2015-03-25.
 */
public class MemberFragment extends Fragment implements View.OnClickListener {

    private Button btnSearch;
    private EditText etFirstName, etLastName;
    private Spinner sParty, sDistrict;
    private ProgressDialog progressDialog;
    private DownloadMemberDataTask downloadMemberDataTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);

        btnSearch = (Button) view.findViewById(R.id.btn_member_fragment_search);
        etFirstName = (EditText) view.findViewById(R.id.et_member_fragment_first_name);
        etLastName = (EditText) view.findViewById(R.id.et_member_fragment_last_name);
        sParty = (Spinner) view.findViewById(R.id.sp_member_fragment_party);
        sDistrict = (Spinner) view.findViewById(R.id.sp_member_fragment_district);

        btnSearch.setOnClickListener(this);

        setUpDialog();

        return view;
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
                if (downloadMemberDataTask != null)
                    downloadMemberDataTask.cancel(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_member_fragment_search:
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String party = sParty.getSelectedItem().toString();
                String district = sDistrict.getSelectedItem().toString();
                int partyPos = sParty.getSelectedItemPosition();
                int districtPos = sDistrict.getSelectedItemPosition();

                if (partyPos == 0) {
                    party = "";
                } else {
                    switch (party) {
                        case "Socialdemokraterna":
                            party = "S";
                            break;

                        case "Moderata samlingspartiet":
                            party = "M";
                            break;

                        case "Miljöpartiet":
                            party = "MP";
                            break;

                        case "Folkpartiet":
                            party = "FP";
                            break;

                        case "Centerpartiet":
                            party = "C";
                            break;

                        case "Sverigedemokraterna":
                            party = "SD";
                            break;

                        case "Vänsterpartiet":
                            party = "V";
                            break;

                        case "Kristdemokraterna":
                            party = "KD";
                            break;
                    }
                }

                if (districtPos == 0) {
                    district = "";
                }

                downloadMemberInfo(firstName, lastName, party, district);
                break;
        }
    }

    private void downloadMemberInfo(String firstName, String lastName, String party, String district) {
        String url = "http://data.riksdagen.se/personlista/?iid=&fnamn=" + firstName + "&enamn=" + lastName + "&f_ar=&kn=&parti=" + party + "&valkrets=" + district.replace(" ", "%20") + "&rdlstatus=&org=&utformat=json&termlista=";
        System.out.println(url);
        downloadMemberDataTask = new DownloadMemberDataTask();
        downloadMemberDataTask.execute(url);
    }

    private class DownloadMemberDataTask extends AsyncTask<String, Integer, ArrayList<MemberPojo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected ArrayList<MemberPojo> doInBackground(String... urls) {
            JSONObject json = downloadMemberData(urls[0]);
            return JSONParser.parseJSONObject(json);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<MemberPojo> memberPojoArrayList) {
            super.onPostExecute(memberPojoArrayList);
            showMemberInformation(memberPojoArrayList);
            progressDialog.dismiss();
        }
    }

    private JSONObject downloadMemberData(String url) {
        URL u = null;
        JSONObject json = new JSONObject();
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (u != null) {
            InputStream is = null;
            try {
                is = u.openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                json = new JSONObject(jsonText);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException jsonex) {
                jsonex.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private void showMemberInformation(ArrayList<MemberPojo> members) {
        int size = members.size();
        if (size > 0) {
/*            for(MemberPojo member : members){
                int tasks = member.getAssignmentPojos().size();
                System.out.println(member.getFirstName() + " " + member.getLastName() + " har " + tasks + " uppdrag.");
            }*/
            Intent i = new Intent(getActivity(), MemberListActivity.class);
            i.putExtra("members", members);
            startActivity(i);
        } else {
            System.out.println("inga sökresultat");
        }
    }
}
