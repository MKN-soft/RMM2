package teamproject.rmm2.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import teamproject.rmm2.R;

/**
 * Fragment displaying "Go premium" aka "GoPro" TextView and ImageButton which call in-app billing on click
 */
public class GoProFragment extends Fragment {

    public GoProFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_go_pro, container, false);


        return rootView;
    }



}
