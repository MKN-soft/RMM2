package teamproject.rmm2.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import teamproject.rmm2.R;
import teamproject.rmm2.models.HabitRow;

/**
 * Created by MSI on 2015-09-03.
 *
 * Adapter object for dynamically generated listView holding one habit informations from database.
 */
public class HabitAdapter extends ArrayAdapter<HabitRow> {

    public HabitAdapter(Context context, List<HabitRow> habitList) {
        super(context, R.layout.listview_item, habitList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // Inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // Initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        }
        else {
            // Recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Resources resources = getContext().getResources();

        // Update the item view
        HabitRow item = getItem(position);
        viewHolder.ivIcon.setImageDrawable(resources.getDrawable(R.mipmap.ic_home));
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvDescription.setText(item.getDescription());

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvDescription;
    }

}
