package teamproject.rmm2.fragments;

/**
 * Created by Marcin on 2015-04-26.
 * Displays list of Habit objects
 */

import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import teamproject.rmm2.R;
import teamproject.rmm2.adapters.HabitAdapter;
import teamproject.rmm2.items.Habit;

public class HomeFragment extends ListFragment {

    private List<Habit> habitList;

    public HomeFragment() {
    }

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

        habitList.add(new Habit(resources.getDrawable(R.mipmap.ic_home), getString(R.string.test_title), getString(R.string.test_description)));
        habitList.add(new Habit(resources.getDrawable(R.mipmap.ic_home), getString(R.string.test_title), getString(R.string.test_description)));
        habitList.add(new Habit(resources.getDrawable(R.mipmap.ic_home), getString(R.string.test_title), getString(R.string.test_description)));

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
        Habit item = habitList.get(position);

        //TODO Do something
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }

}
