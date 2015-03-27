package se.ottomatech.marcusjacobsson.sverigesriksdag.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import se.ottomatech.marcusjacobsson.sverigesriksdag.R;
import se.ottomatech.marcusjacobsson.sverigesriksdag.dialogfragments.AboutDialogFragment;
import se.ottomatech.marcusjacobsson.sverigesriksdag.fragments.CalendarFragmentMain;
import se.ottomatech.marcusjacobsson.sverigesriksdag.fragments.HelpFragment;
import se.ottomatech.marcusjacobsson.sverigesriksdag.fragments.MemberFragment;
import se.ottomatech.marcusjacobsson.sverigesriksdag.pojo.CalendarPojo;


public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener, CalendarFragmentMain.CalendarFragmentCallback {

    private String[] drawerItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpComponents();
        showHelpFragment();
    }

    private void showHelpFragment(){
        HelpFragment helpFragment = new HelpFragment();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, helpFragment);
        t.commit();
    }

    private void setUpComponents() {
        drawerItems = getResources().getStringArray(R.array.navigation_drawer_content);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, drawerItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(this);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                setActionBarArrowDependingOnFragmentsBackStack();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mDrawerToggle.setDrawerIndicatorEnabled(true);
            }
        };


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
        super.onDestroy();
    }

    private void setActionBarArrowDependingOnFragmentsBackStack() {
        int backStackEntryCount =
                getSupportFragmentManager().getBackStackEntryCount();
        mDrawerToggle.setDrawerIndicatorEnabled(backStackEntryCount == 0);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {

        String[] navDrawerItems = getResources().getStringArray(R.array.navigation_drawer_content);
        String selection = navDrawerItems[position];
        switch (selection) {
            case "Kalender":
                CalendarFragmentMain calendarFragmentMain = new CalendarFragmentMain();
                calendarFragmentMain.setListener(this);
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.content_frame, calendarFragmentMain);
                t.commit();
                break;

            case "Ledamöter":
                MemberFragment memberFragment = new MemberFragment();
                FragmentTransaction tt = getSupportFragmentManager().beginTransaction();
                tt.replace(R.id.content_frame, memberFragment);
                tt.commit();
                break;
        }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(drawerItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void showCalendar(final ArrayList<CalendarPojo> allEvents) {
        //this.allEvents = allEvents;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.getDefault());
        HashMap<Date, Integer> activeDates = new HashMap<>(allEvents.size());
        for (CalendarPojo cal : allEvents) {
            System.out.println(cal.toString());
            String dtStart = cal.getDtStart();
            try {
                Date date = format.parse(dtStart);
                activeDates.put(date, R.color.red);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        caldroidFragment.setTextColorForDates(activeDates);

        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                ArrayList<CalendarPojo> matchingDates = getMatchingDates(date, allEvents);
                int size = matchingDates.size();
                if(size != 0){
                    if(size == 1){
                        Intent i = new Intent(view.getContext(), CalendarEventDetails.class);
                        i.putExtra("calendarPojo",matchingDates.get(0));
                        startActivity(i);
                        System.out.println("Skapa dialog direkt");
                    }else if(size > 1){
                        Intent i = new Intent(getApplicationContext(), CalendarEventListActivity.class);
                        i.putParcelableArrayListExtra("events", matchingDates);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "onChangeMonth: month: " + month + " year: " + year;
                System.out.println(text);
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                System.out.println("onLongClickDate: " + date.getTime());
            }

            @Override
            public void onCaldroidViewCreated() {
                System.out.println("onCaldroidViewCreated");
            }

        };

        caldroidFragment.setCaldroidListener(listener);

        caldroidFragment.show(getSupportFragmentManager(), "caldroid");
        // Insert the fragment by replacing any existing fragment
/*        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.addToBackStack(null);
        t.replace(R.id.content_frame, caldroidFragment);
        t.commit();*/
    }

    private ArrayList<CalendarPojo> getMatchingDates(Date date, ArrayList<CalendarPojo> allEvents) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        ArrayList<CalendarPojo> matchingDates = new ArrayList<>();
        String datetime = dateformat.format(date);
        for (CalendarPojo cal : allEvents) {
            if (datetime.equals(cal.getDtStart().substring(0, 8))){
                matchingDates.add(cal);
            }
        }
        return matchingDates;
    }

    @Override
    public void onDownloadReady(ArrayList<CalendarPojo> list) {
        showCalendar(list);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                CalendarFragmentMain calendarFragmentMain = new CalendarFragmentMain();
                calendarFragmentMain.setListener(this);
                calendarFragmentMain.setArguments(extras);
                android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.content_frame, calendarFragmentMain);
                t.commit();
            } else {
                Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.isDrawerIndicatorEnabled() &&
                mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home &&
                getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        } else {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            switch (item.getItemId()) {
                case R.id.action_about:
                    AboutDialogFragment aboutDialogFragment = new AboutDialogFragment();
                    aboutDialogFragment.show(getFragmentManager(), "about");
                    break;

                case R.id.action_settings:

                    break;

                case R.id.action_help:
                    showHelpFragment();
                    break;
            }

            return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        menu.findItem(R.id.action_about).setVisible(!drawerOpen);
        menu.findItem(R.id.action_help).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private FragmentManager.OnBackStackChangedListener
            mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            setActionBarArrowDependingOnFragmentsBackStack();
        }
    };

}
