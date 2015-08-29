package teamproject.rmm2;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import teamproject.rmm2.adapters.HabitAdapter;
import teamproject.rmm2.fragments.HomeFragment;
import teamproject.rmm2.models.Habit;


/**
 *
 * Class supports list of habits, which is in domeFragment. It have method of adding, removing, changing habits or giving last position on list.
 * It is used also to searching and giving habit on given position.
 */
public class LogicBase {

    private static ArrayAdapter habitItemArrayAdapter;
    private static int position;
    private static HomeFragment homeFragment;

    public static void setHomeFragment(HomeFragment hf) {
        homeFragment = hf;
    }

    public static void setHabitItemArrayAdapter(ArrayAdapter hiaa) {
        habitItemArrayAdapter = hiaa;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int pos) {
        position = pos;
    }

    //method return habit on given in arguments position
    public static Habit getHabitAt(int position) {
        int count = habitItemArrayAdapter.getCount();

        if (position < count) {
            return (Habit) habitItemArrayAdapter.getItem(position);
        } else {
            return null;
        }

    }

    // method only updating the list view, may be useful later
    public static void refreshList() {
        homeFragment.setListAdapter(habitItemArrayAdapter);
    }

    // exchange old habit on new with change in this habit
    public static void setHabitAt(int position, Habit habit) {
        int count = habitItemArrayAdapter.getCount();

        // loop which create new list but avoid delete element from old list
        List<Habit> modifiedList = new ArrayList<Habit>(); //count -1
        for (int i = 0; i < count; i++) {
            if (i == position) {
                //add modified habit
                modifiedList.add(habit);
                continue;
            }
            // add Habit from list (habitItemArrayAdapter keep all list habits)
            modifiedList.add((Habit) habitItemArrayAdapter.getItem(i));
        }

        //exchange on new HabitAdapter with new List
        habitItemArrayAdapter = new HabitAdapter(habitItemArrayAdapter.getContext(), modifiedList);
        homeFragment.setListAdapter(habitItemArrayAdapter);
    }

    //method to add habit - create new list, rewrites old habits and add  at the and new habit
    public static void addHabit(Habit newHabit) {
        int count = habitItemArrayAdapter.getCount();
        List<Habit> newList = new ArrayList<Habit>(); //count -1
        for (int i = 0; i < count; i++) {
            newList.add((Habit) habitItemArrayAdapter.getItem(i));
        }
        newList.add(newHabit);

        habitItemArrayAdapter = new HabitAdapter(habitItemArrayAdapter.getContext(), newList);
        homeFragment.setListAdapter(habitItemArrayAdapter);
    }

    //method to delete habit
    public static void removeHabitItemAt(int position) {


        int count = habitItemArrayAdapter.getCount();


        // loop which create new list but avoid delete element from old list
        List<Habit> listWithoutRemovedElement = new ArrayList<Habit>(); //count -1
        for (int i = 0; i < count; i++) {
            if (i == position)
                continue;
            listWithoutRemovedElement.add((Habit) habitItemArrayAdapter.getItem(i));
        }

        habitItemArrayAdapter = new HabitAdapter(habitItemArrayAdapter.getContext(), listWithoutRemovedElement); //habitItemArrayAdapter.getContext()
        homeFragment.setListAdapter(habitItemArrayAdapter);
        //habitItemArrayAdapter.remove(habitItemArrayAdapter.getItem(position));

    }


    public static int getLastPosition() {
        int count = habitItemArrayAdapter.getCount();
        int position = count - 1;
        return position;
    }


}
