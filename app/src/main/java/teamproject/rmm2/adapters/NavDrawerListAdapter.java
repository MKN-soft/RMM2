package teamproject.rmm2.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import teamproject.rmm2.R;
import teamproject.rmm2.models.NavDrawerItem;

/**
 * Created by MSI on 2015-08-05.
 *
 * Navigation drawer list adapter for holding one object from main menu navigation.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItem;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItem) {
        this.context = context;
        this.navDrawerItem = navDrawerItem;
    }

    @Override
    public int getCount() {
        return navDrawerItem.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imageIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView textTitle = (TextView) convertView.findViewById(R.id.title);
        TextView textCount = (TextView) convertView.findViewById(R.id.counter);

        imageIcon.setImageResource(navDrawerItem.get(position).getIcon());
        textTitle.setText(navDrawerItem.get(position).getTitle());

        // displaying count
        // check whether it set visible or not
        if (navDrawerItem.get(position).getCounterVisibility()) {
            textCount.setText(navDrawerItem.get(position).getCount());
        } else {
            // hide the counter view
            textCount.setVisibility(View.GONE);
        }

        return convertView;
    }
}
