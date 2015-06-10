package me.edilsongonza.flickrbrowser;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Edilson Gonzalez on 01/06/2015.
 */
public class FlickrPhotoListAdapter extends RecyclerView.Adapter<FlickrPhotoListAdapter.FlickrPhotoViewHolder> {

    public class FlickrPhotoViewHolder extends RecyclerView.ViewHolder {
        private NetworkImageView photo;
        private TextView title;
        private TextView author;

        public FlickrPhotoViewHolder(View itemView) {
            super(itemView);
            photo = (NetworkImageView) itemView.findViewById(R.id.photo);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);

        }

        public NetworkImageView getPhoto() {
            return photo;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getAuthor() {
            return author;
        }
    }

    private List<FlickrPhotoItem> photos;

    public FlickrPhotoListAdapter(List<FlickrPhotoItem> photos) {
        this.photos = photos;
    }

    @Override
    public FlickrPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_layout, parent, false);
        //View onClickListener
        FlickrPhotoViewHolder viewHolder = new FlickrPhotoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FlickrPhotoViewHolder holder, int position) {
        FlickrPhotoItem currentPhoto = photos.get(position);

        if (holder != null) {
            holder.getPhoto().setImageUrl(currentPhoto.getMedia().getLinkToMedia(), AppController.getIntance().getImageLoader());
            holder.getTitle().setText(currentPhoto.getTitle());
            holder.getAuthor().setText(currentPhoto.getAuthor());
        }
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }
}
