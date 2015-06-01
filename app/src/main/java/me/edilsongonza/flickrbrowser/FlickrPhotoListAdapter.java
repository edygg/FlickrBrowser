package me.edilsongonza.flickrbrowser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Edilson Gonzalez on 01/06/2015.
 */
public class FlickrPhotoListAdapter extends ArrayAdapter<FlickrPhotoItem> {

    public FlickrPhotoListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public FlickrPhotoListAdapter(Context context, int resource, List<FlickrPhotoItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        //Inflate with new layout instance
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.photo_item_layout, null);
        }
        //Get the item
        FlickrPhotoItem photo = getItem(position);

        if (photo != null) {
            TextView titleTextView = (TextView) v.findViewById(R.id.title);
            TextView authorTextView = (TextView) v.findViewById(R.id.author);

            if (titleTextView != null) {
                titleTextView.setText(photo.getTitle());
            }

            if (authorTextView != null) {
                authorTextView.setText(photo.getAuthor());
            }
        }

        return v;
    }
}
