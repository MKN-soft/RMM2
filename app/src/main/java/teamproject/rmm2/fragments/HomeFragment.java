package teamproject.rmm2.fragments;

/**
 * Created by Marcin on 2015-04-26.
 * Displays list of Habit objects
 */

import android.app.ListFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teamproject.rmm2.LogicBase;
import teamproject.rmm2.R;
import teamproject.rmm2.activity.HabitDetailActivity;
import teamproject.rmm2.adapters.HabitAdapter;
import teamproject.rmm2.database.DbHelper;
import teamproject.rmm2.models.Habit;

public class HomeFragment extends ListFragment {

    private ListView habitListView;
    private ArrayAdapter habitItemArrayAdapter;
    public List<Habit> habits = new ArrayList();
    private View view;
    //protected  DbHelper dbHelper;


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 



        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        habits = randomHabits();


        //create array adapter for homeFragment with given random habits
        habitItemArrayAdapter = new HabitAdapter(rootView.getContext(), habits);

        //Place which connect with components logicBase
        setListAdapter(habitItemArrayAdapter);
        LogicBase.setHabitItemArrayAdapter(habitItemArrayAdapter); // remember reference to list of items
        LogicBase.setHomeFragment(this);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //NIE RUSZAC - moze sie przydac :)
//        Fragment mFragment = new HabitDetailFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.frame_container, mFragment);
//        transaction.addToBackStack(null);
//
//        transaction.commit();

        LogicBase.setPosition(position); // remember selected item position
        Intent intent = new Intent(getActivity(), HabitDetailActivity.class);
        getActivity().startActivity(intent);
    }

    //image.draw(R.mipmap.ic_pages);
    private List<Habit> randomHabits() {
        List<Habit> habits = new ArrayList<Habit>();
        Drawable image = getResources().getDrawable(R.mipmap.ic_pages);

        Date date = new Date();
        String date1 = new SimpleDateFormat("dd MMMM yyyy").format(date);

        for (int i = 0; i < 5; i++) {//zle to na dole
            Habit habit = new Habit("Tytuł nawyku #" + i, "Trochę tekstu dla body #" + i, "" + 1 + i, image, "Tu sa notatki pisz co chcesz by pomoc sobie dazyc do wyrobienia nawyku " + i, date1, 0);
            habits.add(habit);
        }
        return habits;
    }


    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }
}

