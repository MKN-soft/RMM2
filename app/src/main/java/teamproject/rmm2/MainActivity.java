package teamproject.rmm2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import teamproject.rmm2.Helpers.RepeatForService;
import teamproject.rmm2.Helpers.Statistics;
import teamproject.rmm2.adapters.NavDrawerListAdapter;
import teamproject.rmm2.fragments.GoProFragment;
import teamproject.rmm2.fragments.HomeFragment;
import teamproject.rmm2.fragments.SettingsFragment;
import teamproject.rmm2.models.CalendarRow;
import teamproject.rmm2.models.HabitRow;
import teamproject.rmm2.models.NavDrawerItem;
import teamproject.rmm2.services.SynchronizationService;


public class MainActivity extends MyActivityTemplate {

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<>();

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(5, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // Notification handler - set time etc.
        RepeatForService repeatForService = new RepeatForService(getContext());
        repeatForService.NotificationsHandler(SynchronizationService.class);

        /********************************************************
        //DATABASE TESTING, insert habits, get statistics etc.
        ********************************************************/

        dbHelper.insertState(-1, "FAIL");
        dbHelper.insertState(0, "NEUTRAL");
        dbHelper.insertState(1, "DONE");

//        inserting habits
        dbHelper.insertHabit("a","a",1,1, Calendar.getInstance());
        dbHelper.insertHabit("b","b",1,7, Calendar.getInstance());
        dbHelper.insertHabit("c","c",2,1, Calendar.getInstance());

//        inserting dates (CALENDAR table)
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dbHelper.insertDate(calendar, "a", 1);


        //new
        calendar.set(2015, 9, 16);
        dbHelper.insertDate(calendar,"b",1);

        dbHelper.setTodosFor30Days();

        /********************************************************
         //END OF DATABASE TESTING
         ********************************************************/


        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_addHabit) {
            // Add Habit

            Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }


    @Override
    protected Context getContext() {
        return getApplicationContext();
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new SettingsFragment();
                break;
            case 2:
                fragment = new GoProFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    //TODO delete testers
    /**
     * testing purposes, delete when not needed
     * @param v
     */
    public void dbputhabit(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("Processing...");
        //ok until here

        try {
            dbHelper.insertHabit("test_habit", "desc", 1,1, Calendar.getInstance());
        } catch (SQLiteConstraintException e) {
            textView.setText("Duplicate record!");
            return;
        }
        catch(Exception e){
            textView.setText("Unexpected exception!");
            return;
        }

       textView.setText("Habit inserted");

    }

    /**
     * testing purposes, delete when not needed
     * @param v
     */
    public void dbgethabit(View v) {
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("getting text from db");

        HabitRow habitRow = dbHelper.getHabit("test_habit");

        if (habitRow != null) {
            textView.setText(habitRow.getTitle() + "," + habitRow.getDescription() + "," + String.valueOf(habitRow.getFrequency()));
        }
        else {
            textView.setText("No such habit!");
        }
    }

    /**
     * testing purposes, delete when not needed
     * @param v
     */
    /*public void dbputstate(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("Inserting state...");
        //ok until here

        try {
            dbHelper.insertState(0);
        }
        catch(SQLiteConstraintException e){
            textView.setText("Duplicate record!");
            return;
        }
        catch(Exception e){
            textView.setText("Unexpected exception!");
            return;
        }

        textView.setText("State inserted");

    }*/

    /**
     * testing purposes, delete when not needed
     * @param v
     */
    public void dbgetstate(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("getting text from db");

        int aux = 0;

        try{
             aux = dbHelper.getState(0);
        }
        catch(NoSuchFieldException e){
            textView.setText("No such state!");
            return;
        }

        textView.setText(Integer.toString(aux));


    }

    /**
     * testing purposes, delete when not needed
     * @param v
     */
    public void dbputdate(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("Processing...");
        //ok until here

        //getting current time in Calendar object
        Calendar calendar = Calendar.getInstance();

        long time = calendar.getTimeInMillis()/1000L;

        //inserting with current time
        try {
            dbHelper.insertDate(calendar, "test_habit", 0);
        }
        catch(SQLiteConstraintException e){
            textView.setText("Constraint exception!");
            return;
        }
        catch(Exception e){
            textView.setText("Unexpected exception!");
            return;
        }

        textView.setText("Date inserted");

    }

    public void dbgetdate(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("getting text from db");

        //gets current date
        Calendar calendar = Calendar.getInstance();

        long time = calendar.getTimeInMillis();

        CalendarRow calendarRow = dbHelper.getDate(calendar, "test_habit");

        if (calendarRow != null) {
            textView.setText(String.valueOf(calendarRow.getTime()) + "," + calendarRow.getHabit() + "," + String.valueOf(calendarRow.getState()));
        }
        else {
            textView.setText("No such habit!");
        }
    }
}
