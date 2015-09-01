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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teamproject.rmm2.activity.AddHabitActivity;
import teamproject.rmm2.adapters.NavDrawerListAdapter;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.fragments.GoProFragment;
import teamproject.rmm2.fragments.HomeFragment;
import teamproject.rmm2.fragments.SettingsFragment;
import teamproject.rmm2.models.CalendarRow;
import teamproject.rmm2.models.Habit;
import teamproject.rmm2.models.HabitRow;
import teamproject.rmm2.models.NavDrawerItem;

//TODO Main Activity - menu and stuff

public class MainActivity extends MyActivityTemplate {

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    protected DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.db = new DbHelper(this);

//        adding some habits for testing purposes
        db.insertHabit("a","a",1,0,System.currentTimeMillis());
        db.insertHabit("b","a",2,0,System.currentTimeMillis());
        db.insertHabit("c","a",3,0,System.currentTimeMillis());

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
        //if (id == R.id.action_settings) {
       //     return true;
       // }

        switch (item.getItemId()) {
            case R.id.action_addHabit:
                //click addhabit
                Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_addHabit).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
                HomeFragment hF = new HomeFragment();
                List<Habit> habits = new ArrayList<Habit>();
                //habits = db.getAllHabits2();

                hF.setHabits(db.getAllHabits2());
                fragment = hF;
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

    /*//TODO delete testers
    *//**
     * testing purposes, delete when not needed
     * @param v
     *//*
    public void dbputhabit(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("Processing...");
        //ok until here

        //TODO: think about throwing and handling exception so it's convenient for others using this method
        try {
            dbHelper.insertHabit("test_habit", "desc", 1);
        } catch (SQLiteConstraintException e) {
            textView.setText("Duplicate record!");
            return;
        }
        catch(Exception e){
            textView.setText("Unexpected exception!");
            return;
        }

       textView.setText("Processing... finished");

    }

    *//**
     * testing purposes, delete when not needed
     * @param v
     *//*
    public void dbgethabit(View v){
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

    *//**
     * testing purposes, delete when not needed
     * @param v
     *//*
    public void dbputstate(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("Inserting state...");
        //ok until here

        dbHelper.insertState("test_state");

        textView.setText("Processing... finished");

    }

    *//**
     * testing purposes, delete when not needed
     * @param v
     *//*

    *//**
     * testing purposes, delete when not needed
     * @param v
     *//*
    public void dbputdate(View v){
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("Processing...");
        //ok until here

        //Creating unix timesstamp for the BEGINING of the day

        //inserting with current time
        dbHelper.insertDate(System.currentTimeMillis()/1000L,"test_habit", 0);   //TODO foreign key not working properly!

        textView.setText("Processing... finished");

    }

    public void dbgetdate(View v){
        //TODO method getDate will return LIST of CalendarRows in future
        TextView textView = (TextView) findViewById(R.id.test_db_textview);
        textView.setText("getting text from db");

        //gets current date and time 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

       long time = calendar.getTimeInMillis();

        CalendarRow calendarRow = dbHelper.getDate(time, "test_habit");

        if (calendarRow != null) {
            textView.setText(String.valueOf(calendarRow.getTime()) + "," + calendarRow.getHabit() + "," + String.valueOf(calendarRow.getState()));
        }
        else {

            //TODO: NO SUCH HABIT when there should be.
            textView.setText("No such habit!");
        }
    }*/
}
