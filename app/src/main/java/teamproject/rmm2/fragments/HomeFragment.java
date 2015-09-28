package teamproject.rmm2.fragments;

/**
 * Created by Marcin on 2015-04-26.
 * Displays list of Habit objects
 */

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import teamproject.rmm2.HabitDetailActivity;
import teamproject.rmm2.Helpers.SessionManager;
import teamproject.rmm2.R;
import teamproject.rmm2.adapters.HabitAdapter;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.database.asynctask.GetAllHabitsTask;
import teamproject.rmm2.models.HabitRow;

public class HomeFragment extends ListFragment {

    private List<HabitRow> habitList;
    private DbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the items list
        habitList = new ArrayList<>();
        Resources resources = getResources();

        //TODO Add habits to the list, from database ?
            // 1. get data from DB
            // 2. add data to the list
        // EXAMPLE
        /*

        habitList.add(new Habit(resources.getDrawable(R.mipmap.ic_home), getString(R.string.test_title), getString(R.string.test_description)));

         */

        // Get data from database to Habit List
        //dbHelper = new DbHelper(getActivity().getApplicationContext());
        //List<HabitRow> habitRowList = dbHelper.getAllHabits();

        GetAllHabitsTask getAllHabitsTask = new GetAllHabitsTask(getActivity().getApplicationContext());
        List<HabitRow> habitRowList = null;
        try {
            habitRowList = getAllHabitsTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (habitRowList != null)
            for(int i = 0; i < habitRowList.size(); i++)
                habitList.add(new HabitRow(resources.getDrawable(R.mipmap.ic_home), habitRowList.get(i).getTitle(), habitRowList.get(i).getDescription()));

        // Initialize and set the list adapter
        setListAdapter(new HabitAdapter(getActivity(), habitList));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Retrieve the ListView item
        HabitRow item = habitList.get(position);

        SessionManager sessionManager = new SessionManager(getActivity().getApplicationContext());

        sessionManager.setHabitTitle(item.getTitle());

        Intent intent = new Intent(getActivity(), HabitDetailActivity.class);
        startActivity(intent);
    }

}
