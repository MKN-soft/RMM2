package teamproject.rmm2.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import teamproject.rmm2.R;
import teamproject.rmm2.services.SynchronizationService;

/**
 * Created by ASUS on 2015-04-27.
 * Fragment that displays and makes it possible to edit settings (not implemented)
 */
public class SettingsFragment extends Fragment {

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);


        Button buttonStatistics;
        buttonStatistics = (Button) rootView.findViewById(R.id.buttonStatistics);

        buttonStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SynchronizationService.class);
                getActivity().startService(intent);
            }
        });

        return rootView;
    }

}
