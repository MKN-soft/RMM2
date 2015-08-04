package teamproject.rmm2.fragments;

/**
 * Created by Marcin on 2015-04-26.
 * Displays list of Habit objects
 */

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teamproject.rmm2.R;

public class HomeFragment extends ListFragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        return rootView;
    }

}
